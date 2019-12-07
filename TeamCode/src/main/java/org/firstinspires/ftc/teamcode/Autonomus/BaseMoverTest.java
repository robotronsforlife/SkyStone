package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robotron.Robot;

@Autonomous(name = "Base Mover Test 1", group = "Test Test")
public class BaseMoverTest extends LinearOpMode {
    private Robot robot;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        double Left_Power;
        double Right_Power;

        robot = new Robot();
        robot.Init(hardwareMap, telemetry);

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Create a timer object with millisecond
            // resolution and save in ElapsedTime variable.
            ElapsedTime ElapsedTime2 = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

            // Move robot forward for 2 seconds or until stop
            // is pressed on Driver Station.
            while (!(ElapsedTime2.milliseconds() >= 1900 || isStopRequested())) {
                MoveRobot(true);

                robot.RightPuller(1);
                robot.LeftPuller(1);

                // Wait 1/5 second before checking again.
                sleep(200);
            }

            robot.RightPuller(0);
            robot.LeftPuller(0);

            ElapsedTime2.reset();
            while (!(ElapsedTime2.milliseconds() >= 250 || isStopRequested())) {
                robot.Slide_Right((float) 0.5);
            }

            robot.Stop();

            ElapsedTime2.reset();
            while (!(ElapsedTime2.milliseconds() >= 600 || isStopRequested())) {
                robot.Forward((float) 0.2);
            }

            robot.Stop();

            ElapsedTime2.reset();
            while (!(ElapsedTime2.milliseconds() >= 1000 || isStopRequested())) {
                robot.RightPuller(-1);
                robot.LeftPuller(-1);

                // Wait 1/5 second before checking again.
                sleep(200);
            }

            robot.Stop();

            ElapsedTime2.reset();
            while (!(ElapsedTime2.milliseconds() >= 2500 || isStopRequested())) {
                MoveRobot(false);
                // Wait 1/5 second before checking again.
                sleep(200);
            }

            robot.Stop();
        }
    }

    private void MoveRobot(boolean forward){

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
            // Update the motors to the new power levels.
            robot.Left_Front_Wheel.setPower(Left_Power*-1);
            robot.Left_Rear_Wheel.setPower(Left_Power*-1);
            robot.Right_Front_Wheel.setPower(Right_Power*-1);
            robot.Right_Rear_Wheel.setPower(Right_Power*-1);
        }
        telemetry.update();
    }
}
