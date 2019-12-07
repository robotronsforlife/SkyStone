package org.firstinspires.ftc.teamcode.Robotron;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Odometry.OdometryGlobalCoordinatePosition;

public class Robot {
    final double COUNTS_PER_INCH = 307.699557;

    OdometryGlobalCoordinatePosition globalPositionUpdate;

    public DcMotor Right_Front_Wheel;
    public DcMotor Left_Front_Wheel;
    public DcMotor Right_Rear_Wheel;
    public DcMotor Left_Rear_Wheel;

    //Odometry Wheels
    private DcMotor verticalLeft;
    private DcMotor verticalRight;
    private DcMotor horizontal;

    private DcMotor Elbow1;
    private DcMotor Elbow2;
    private DcMotor Elbow3;
    private DcMotor Elbow4;

    private CRServo Right_Puller;
    private CRServo Left_Puller;

    private CRServo Grip_Servo;
    private CRServo Turn_Servo;

    //IMU Sensor
    private BNO055IMU imu;

    private Telemetry telemetry;

    public void Init(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.telemetry = telemetry;

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
