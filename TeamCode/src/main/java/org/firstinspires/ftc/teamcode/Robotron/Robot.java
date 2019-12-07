package org.firstinspires.ftc.teamcode.Robotron;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class Robot {
    final double COUNTS_PER_INCH = 307.699557;

    OdometryGlobalCoordinatePosition globalPositionUpdate;

    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "  AUZcNNv/////AAABmYMCzj8UbECuscFRufHBOhQu2A8/CywUJ1W56jEzQmo+nm0Y10JmIbQidFyREEAKPmRTOBUMC4vhXV8sAeP6Y3SvNVF9RXCxRKVpGyN/YqgpipV0JgS0A4LZchrRjS2Pr//G8LEVBVgPagcmsU2N6R2wo2U6sS75KDZsnhwKIFOpgENYH/VezSh14aJL2cEifl2c76d5f4bfKDSBM3qTMqBAL/3Yz/fSK7Dt817SvtDUcvzS9mNTLYrbmV3sCVJqm+HIGxcYN+5RfKWe5iKB9tCPPfpDzmMIoBzBX9KKkAK9IKMkVms5FkomsuGbkodkui3RcJDJa80ZIkU1dsT6OktT3Hgj1PtcuUsjKt7AjE4D   ";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private VuforiaLocalizer vuforia = null;

    /**
     * This is the webcam we are to use. As with other hardware devices such as motors and
     * servos, this device is identified using the robot configuration tool in the FTC application.
     */
    WebcamName webcamName = null;
    public VuforiaTrackables targetsSkyStone;

    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;


    public DcMotor Right_Front_Wheel;
    public DcMotor Left_Front_Wheel;
    public DcMotor Right_Rear_Wheel;
    public DcMotor Left_Rear_Wheel;

    //Odometry Wheels
    private DcMotor verticalLeft;
    private DcMotor verticalRight;
    private DcMotor horizontal;

    public DcMotor Elbow1;
    public DcMotor Elbow2;
    public DcMotor Elbow3;
    public DcMotor Elbow4;

    private CRServo Right_Puller;
    private CRServo Left_Puller;

    private CRServo Grip_Servo;
    private CRServo Turn_Servo;

    //IMU Sensor
    private BNO055IMU imu;

    private Telemetry telemetry;

    private HardwareMap hardwareMap;

    public void Init(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        //Initialize IMU hardware map value.
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        // Create an IMU parameters object.
        BNO055IMU.Parameters IMU_Parameters = new BNO055IMU.Parameters();
        // Set the IMU sensor mode to IMU. This mode uses
        // the IMU gyroscope and accelerometer to
        // calculate the relative orientation of hub and
        // therefore the robot.
        IMU_Parameters.mode = BNO055IMU.SensorMode.IMU;
        // Initialize the IMU using parameters object.
        imu.initialize(IMU_Parameters);

        Right_Front_Wheel = hardwareMap.dcMotor.get("Right_Front_Wheel");
        Left_Front_Wheel = hardwareMap.dcMotor.get("Left_Front_Wheel");
        Right_Rear_Wheel = hardwareMap.dcMotor.get("Right_Rear_Wheel");
        Left_Rear_Wheel = hardwareMap.dcMotor.get("Left_Rear_Wheel");

        verticalLeft = hardwareMap.dcMotor.get("Left_Rear_Wheel");
        verticalRight = hardwareMap.dcMotor.get("Left_Front_Wheel");
        horizontal = hardwareMap.dcMotor.get("Right_Rear_Wheel");

        Right_Front_Wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right_Rear_Wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Front_Wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Rear_Wheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Right_Front_Wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Rear_Wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Front_Wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Rear_Wheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        Right_Front_Wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        Right_Rear_Wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        Left_Front_Wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        Left_Rear_Wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        Elbow1=hardwareMap.dcMotor.get("Elbow1");
        Elbow2=hardwareMap.dcMotor.get("Elbow2");
        Elbow3=hardwareMap.dcMotor.get("Elbow3");
        Elbow4=hardwareMap.dcMotor.get("Elbow4");

        Right_Puller = hardwareMap.crservo.get("Right_Puller");
        Left_Puller = hardwareMap.crservo.get("Left_Puller");
        Turn_Servo = hardwareMap.crservo.get("Turn_Servo");
        Grip_Servo = hardwareMap.crservo.get("Grip_Servo");

        Elbow1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Elbow3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Elbow4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Create and start GlobalCoordinatePosition thread to constantly update the global coordinate positions
        globalPositionUpdate = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(globalPositionUpdate);
        positionThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            telemetry.addData("Thread Sleep Interrupted", "Thread Sleep Interrupted");
            telemetry.update();
        }

        globalPositionUpdate.reverseRightEncoder();
        globalPositionUpdate.reverseLeftEncoder();

        InitCamera();
    }

    private void InitCamera()
    {
        /*
         * Retrieve the camera we are to use.
         */
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        targetsSkyStone.activate();
    }

    public float GetRobotYaw_Angle()
    {
        // get gyro's yaw angle
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
    }

    public BNO055IMU.CalibrationStatus GetRobotGyroCalibrationStatus()
    {
        return imu.getCalibrationStatus();
    }

    public BNO055IMU.SystemStatus GetRobotGyroSystemStatus()
    {
        return imu.getSystemStatus();
    }

    public boolean IsRobotGyroCalibrated()
    {
        telemetry.addData("IMU Calibration Status", imu.getCalibrationStatus());
        telemetry.addData("Gyro Calibrated", imu.isGyroCalibrated() ? "True" : "False");
        telemetry.addData("System Status", imu.getSystemStatus().toString());
        return imu.isGyroCalibrated();
    }

    public void StopRobot()
    {
        //Stop the thread
        globalPositionUpdate.stop();
    }

    public void Stop(){
        Left_Front_Wheel.setPower(0);
        Left_Rear_Wheel.setPower(0);
        Right_Front_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(0);
    }

    public void Forward(float power){
        Left_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power);
        Right_Front_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(power);
    }

    public void Reverse(float power){
        Left_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(power * -1);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void Right(float power){
        Left_Front_Wheel.setPower(power);
        Right_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void Left(float power){
        Left_Front_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power * -1);
        Right_Rear_Wheel.setPower(power);
    }

    public void Diagonal_Right_Up(float power){
        Left_Front_Wheel.setPower(power);
        Right_Front_Wheel.setPower(0);
        Left_Rear_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(power);
    }

    public void Diagonal_Left_Down(float power){
        Left_Front_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(0);
        Left_Rear_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void Diagonal_Right_Down(float power){
        Left_Front_Wheel.setPower(0);
        Right_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(0);
    }

    public void Diagonal_Left_Up(float power){
        Left_Front_Wheel.setPower(0);
        Right_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(0);
    }

    public void Slide_Right(float power){
        Left_Front_Wheel.setPower(power);
        Right_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power * -1);
        Right_Rear_Wheel.setPower(power);
    }

    public void Slide_Left(float power){
        Left_Front_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void LeftPuller(double power)
    {
        Left_Puller.setPower(power);
    }

    public void RightPuller(double power)
    {
        Right_Puller.setPower(power);
    }

    public void Gripper(double power)
    {
        Grip_Servo.setPower(power);
    }

    public void TurnGripper(double power)
    {
        Turn_Servo.setPower(power);
    }

    public void MoveElbow1(double power)
    {
        Elbow1.setPower(power);
    }

    public void MoveElbow2(double power){
        Elbow2.setPower(power);
    }

    public void MoveElbow3(double power){
        Elbow3.setPower(power);
    }

    public void MoveElbow4(double power){
        Elbow4.setPower(power);
    }
}
