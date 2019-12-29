package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.Robotron.Robot;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.mmPerInch;

@TeleOp(name = "Red: Get SkyStones manual measurement", group = "Jan 12 Competition")
@Disabled
public class GetSkystoneManualMeasurement extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();
        robot.Init(hardwareMap, telemetry, true);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Start Left Ticks", robot.GetLeftVerticalEncoderTicks());
            telemetry.addData("Start Right Ticks", robot.GetRightVerticalEncoderTicks());
            telemetry.addData("Start Normal Ticks", robot.GetNormalEncoderTicks());

            float Yaw_Angle = robot.GetRobotYaw_Angle();
            // Report yaw orientation to Driver Station.
            telemetry.addData("Yaw value", Yaw_Angle);

            telemetry.update();
        }

/*        // Create a timer object with millisecond
        // resolution and save in ElapsedTime variable.
        ElapsedTime ElapsedTime2 = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        ElapsedTime2.reset();
        while (!((ElapsedTime2.milliseconds() >= 1000) || isStopRequested())) {
            robot.RightBlockGrabber((float) 0.5);
        }
        robot.RightBlockGrabber((float) 0.0);

        int currentPosition = robot.GetLeftVerticalEncoderTicks();
        // Move backward while holding the block
        while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() > currentPosition - (7.5 * 308)) {
            // MoveRobot(0.1, true, 0);
            MoveRobotByEncoderTicks(-0.5);

            telemetry.addData("Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
            telemetry.addData("Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
            telemetry.update();
        }

        robot.Stop();

        // Turn the base to face the wall
        float Yaw_Angle = robot.GetRobotYaw_Angle();
        while (!(Yaw_Angle <= -84 || isStopRequested())) {
            // Update Yaw-Angle variable with current yaw.
            Yaw_Angle = robot.GetRobotYaw_Angle();
            // Report yaw orientation to Driver Station.
            telemetry.addData("Yaw value on turning", Yaw_Angle);
            telemetry.update();

            robot.Right((float) 0.4);
        }
        robot.Stop();*/

/*
        int currentPosition = robot.GetLeftVerticalEncoderTicks();
        float Yaw_Angle = robot.GetRobotYaw_Angle();
        // Report yaw orientation to Driver Station.
        telemetry.addData("Yaw value on start", Yaw_Angle);
        telemetry.update();

        sleep(1000);

        // Move forward while holding the block
        while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() < currentPosition + (60 * 308)) {
            MoveRobot(0.75, true, 0);
            // MoveRobotByEncoderTicks(0.9, true);

            telemetry.addData("Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
            telemetry.addData("Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
            telemetry.update();
        }

        Yaw_Angle = robot.GetRobotYaw_Angle();
        // Report yaw orientation to Driver Station.
        telemetry.addData("Yaw value on end", Yaw_Angle);
        telemetry.update();
        robot.Stop();

        sleep(10000);

        while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() > 0) {
            MoveRobot(0.75, false, 0);
            // MoveRobotByEncoderTicks(0.9, false);

            telemetry.addData("Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
            telemetry.addData("Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
            telemetry.update();
        }
        robot.Stop();

        sleep(1000);
        Yaw_Angle = robot.GetRobotYaw_Angle();
        // Report yaw orientation to Driver Station.

        telemetry.addData("Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
        telemetry.addData("Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
        telemetry.addData("Yaw value on complete", Yaw_Angle);
        telemetry.update();

        sleep(50000);

 */
    }

    private void MoveRobot(double basePower, boolean forward, float maintainAngle){
        double Left_Power;
        double Right_Power;

        // Save gyro's yaw angle
        float Yaw_Angle = robot.GetRobotYaw_Angle();

        // Report yaw orientation to Driver Station.
        telemetry.addData("Yaw angle", Yaw_Angle);
        // If the robot is moving straight ahead the
        // yaw value will be close to zero. If it's not, we
        // need to adjust the motor powers to adjust heading.
        // If robot yaws right or left by 5 or more,
        // adjust motor power variables to compensation.
        if (Yaw_Angle < -0.05 + maintainAngle) {
            if (forward == true) {
                // Turn left
                Left_Power = basePower - 0.02;
                Right_Power = basePower + 0.02;
            } else {
                Left_Power = basePower + 0.02;
                Right_Power = basePower - 0.02;
            }
        } else if (Yaw_Angle > 0.05 + maintainAngle) {
            if (forward == true) {
                // Turn right.
                Left_Power = basePower + 0.02;
                Right_Power = basePower - 0.04;
            } else {
                Left_Power = basePower - 0.02;
                Right_Power = basePower + 0.04;
            }
        } else {
            // Continue straight
            Left_Power = basePower;
            Right_Power = basePower;
        }

        // Report the new power levels to the Driver Station.
        telemetry.addData("Left Front Motor Power", Left_Power);
        telemetry.addData("Right Front Motor Power", Right_Power);
        telemetry.addData("Left Rear Motor Power", Left_Power);
        telemetry.addData("Right Rear Motor Power", Right_Power);

        if (forward) {
            // Update the motors to the new power levels.
            robot.Left_Front_Wheel.setPower(Left_Power);
            robot.Left_Rear_Wheel.setPower(Left_Power);
            robot.Right_Front_Wheel.setPower(Right_Power);
            robot.Right_Rear_Wheel.setPower(Right_Power);
        }
        else
        {
            // Update the motors to the new power levels.s
            robot.Left_Front_Wheel.setPower(Left_Power*-1);
            robot.Left_Rear_Wheel.setPower(Left_Power*-1);
            robot.Right_Front_Wheel.setPower(Right_Power*-1);
            robot.Right_Rear_Wheel.setPower(Right_Power*-1);
        }
        telemetry.update();
    }

    private void MoveRobotByEncoderTicks(double basePower, boolean direction) {
        double Left_Power;
        double Right_Power;

        double Front_Left_Power;
        double Front_Right_Power;
        double Bsck_Left_Power;
        double Bsck_Right_Power;

        int leftEncoderTicks = robot.GetLeftVerticalEncoderTicks();
        int rightEncoderTicks = robot.GetRightVerticalEncoderTicks();

        int encoderTicksDifference = leftEncoderTicks - rightEncoderTicks;

        if (Math.abs(encoderTicksDifference) > 30) {

            if (encoderTicksDifference < 0) {
                Left_Power = basePower + 0.05;
                Right_Power = basePower - 0.05;
            } else {
                Left_Power = basePower - 0.05;
                Right_Power = basePower + 0.05;
            }
        } else {
            Left_Power = basePower;
            Right_Power = basePower;
        }

        // Report the new power levels to the Driver Station.
        telemetry.addData("Left Front Motor Power", Left_Power);
        telemetry.addData("Right Front Motor Power", Right_Power);
        telemetry.addData("Left Rear Motor Power", Left_Power);
        telemetry.addData("Right Rear Motor Power", Right_Power);

        if (direction) {
            // Update the motors to the new power levels.
            robot.Left_Front_Wheel.setPower(Left_Power);
            robot.Left_Rear_Wheel.setPower(Left_Power);
            robot.Right_Front_Wheel.setPower(Right_Power);
            robot.Right_Rear_Wheel.setPower(Right_Power);
        } else {
            // Update the motors to the new power levels.
            robot.Left_Front_Wheel.setPower(-1 * Left_Power);
            robot.Left_Rear_Wheel.setPower(-1 * Left_Power);
            robot.Right_Front_Wheel.setPower(-1 * Right_Power);
            robot.Right_Rear_Wheel.setPower(-1 * Right_Power);
        }

        telemetry.update();
    }
}
