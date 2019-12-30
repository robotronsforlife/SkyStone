package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name = "ImuSample", group = "")
public class ImuSample extends LinearOpMode {

    private DcMotor leftmotor;
    private DcMotor rightmotor;
    private BNO055IMU imu;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        BNO055IMU.Parameters IMU_Parameters;
        ElapsedTime ElapsedTime2;
        double Left_Power;
        double Right_Power;
        float Yaw_Angle = 0;

        leftmotor = hardwareMap.dcMotor.get("leftmotor");
        rightmotor = hardwareMap.dcMotor.get("rightmotor");
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        // This op mode uses the REV Hub's built-in gyro to
        // to allow a robot to move straight forward and then
        // make a right turn.
        // The op mode assume you have
        // (1) Connected two motors to the expansion
        //       hub.
        // (2) Created a config file that
        // (a) names the motors "left-motor" and
        //       "right-motor"
        // (b) configures the imu on I2C bus 0 port 0
        //      as a REV Expansion Hub IMU
        //      with the name "imu".
        // Setup so motors will brake the wheels
        // when motor power is set to zero.
        leftmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Reverse direction of one motor so robot moves
        // forward rather than spinning in place.
        leftmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Create an IMU parameters object.
        IMU_Parameters = new BNO055IMU.Parameters();
        // Set the IMU sensor mode to IMU. This mode uses
        // the IMU gyroscope and accelerometer to
        // calculate the relative orientation of hub and
        // therefore the robot.
        IMU_Parameters.mode = BNO055IMU.SensorMode.IMU;
        // Intialize the IMU using parameters object.
        imu.initialize(IMU_Parameters);
        // Report the initialization to the Driver Station.
        telemetry.addData("Status", "IMU initialized, calibration started.");
        telemetry.update();
        // Wait one second to ensure the IMU is ready.
        sleep(1000);
        // Loop until IMU has been calibrated.
        while (!IMU_Calibrated()) {
            telemetry.addData("If calibration ", "doesn't complete after 3 seconds, move through 90 degree pitch, roll and yaw motions until calibration complete ");
            telemetry.update();
            // Wait one second before checking calibration
            // status again.
            sleep(1000);
        }
        // Report calibration complete to Driver Station.
        telemetry.addData("Status", "Calibration Complete");
        telemetry.addData("Action needed:", "Please press the start triangle");
        telemetry.update();
        // Wait for Start to be pressed on Driver Station.
        waitForStart();
        // Create a timer object with millisecond
        // resolution and save in ElapsedTime variable.
        ElapsedTime2 = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        // Initialize motor power variables to 30%.
        Left_Power = 0.3;
        Right_Power = 0.3;
        // Set motor powers to the variable values.
        leftmotor.setPower(Left_Power);
        rightmotor.setPower(Right_Power);
        // Move robot forward for 2 seconds or until stop
        // is pressed on Driver Station.
        while (!(ElapsedTime2.milliseconds() >= 2000 || isStopRequested())) {
            // Save gyro's yaw angle
            Yaw_Angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
            // Report yaw orientation to Driver Station.
            telemetry.addData("Yaw angle", Yaw_Angle);
            // If the robot is moving straight ahead the
            // yaw value will be close to zero. If it's not, we
            // need to adjust the motor powers to adjust heading.
            // If robot yaws right or left by 5 or more,
            // adjust motor power variables to compensation.
            if (Yaw_Angle < -5) {
                // Turn left
                Left_Power = 0.25;
                Right_Power = 0.35;
            } else if (Yaw_Angle > 5) {
                // Turn right.
                Left_Power = 0.35;
                Right_Power = 0.25;
            } else {
                // Continue straight
                Left_Power = 0.3;
                Right_Power = 0.3;
            }
            // Report the new power levels to the Driver Station.
            telemetry.addData("Left Motor Power", Left_Power);
            telemetry.addData("Right Motor Power", Right_Power);
            // Update the motors to the new power levels.
            leftmotor.setPower(Left_Power);
            rightmotor.setPower(Right_Power);
            telemetry.update();
            // Wait 1/5 second before checking again.
            sleep(200);
        }
        // Now let's execute a right turn using power
        // levels that will cause a turn in place.
        leftmotor.setPower(0.2);
        rightmotor.setPower(-0.2);
        // Continue until robot yaws right by 90 degrees
        // or stop is pressed on Driver Station.
        while (!(Yaw_Angle <= -90 || isStopRequested())) {
            // Update Yaw-Angle variable with current yaw.
            Yaw_Angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
            // Report yaw orientation to Driver Station.
            telemetry.addData("Yaw value", Yaw_Angle);
            telemetry.update();
        }
        // We're done. Turn off motors
        leftmotor.setPower(0);
        rightmotor.setPower(0);
        // Pause so final telemetry is displayed.
        sleep(1000);
    }

    /**
     * Function that becomes true when gyro is calibrated and
     * reports calibration status to Driver Station in the meantime.
     */
    private boolean IMU_Calibrated() {
        telemetry.addData("IMU Calibration Status", imu.getCalibrationStatus());
        telemetry.addData("Gyro Calibrated", imu.isGyroCalibrated() ? "True" : "False");
        telemetry.addData("System Status", imu.getSystemStatus().toString());
        return imu.isGyroCalibrated();
    }
}
