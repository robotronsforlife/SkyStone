package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous(name = "Blue: Get SkyStones", group = "Jan 12 Competition")
public class GetSkystoneBlue extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();
        robot.Init(hardwareMap, telemetry, true);

        waitForStart();

        /*if (opModeIsActive()) {
            robot.TurnElbow4ByDegrees(230, false);}

            Normal encoder reading -13529
*/
        if (opModeIsActive()) {

            robot.TurnElbow4ByDegrees(230, false);
            telemetry.addData("Start Left Ticks", robot.GetLeftVerticalEncoderTicks());
            telemetry.addData("Start Right Ticks", robot.GetRightVerticalEncoderTicks());
            telemetry.update();

            // Create a timer object with millisecond
            // resolution and save in ElapsedTime variable.
            ElapsedTime ElapsedTime2 = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

            // Move robot forward for specified tick counts or until stop
            // is pressed on Driver Station.
            while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() < 6475) {

                if (robot.GetLeftVerticalEncoderTicks() < 4000) {
                    //MoveRobot(0.5, true, 0);
                    robot.Forward((float)0.5);
                } else {
                    //MoveRobot(0.2, true, 0);
                    robot.Forward((float)0.2);

                }

                telemetry.addData("Left Ticks", robot.GetLeftVerticalEncoderTicks());
                telemetry.addData("Right Ticks", robot.GetRightVerticalEncoderTicks());
                telemetry.update();
            }
            robot.Stop();

            boolean targetVisible = false;
            OpenGLMatrix lastLocation = null;
            int normalEncoderTicks = 0;
            // Move left until target becomes visible
            while (!isStopRequested() && !targetVisible) {

                if (((VuforiaTrackableDefaultListener) robot.stoneTarget.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", robot.stoneTarget.getName());
                    targetVisible = true;

                    normalEncoderTicks = robot.GetNormalEncoderTicks();
                    telemetry.addData("Normal Ticks", robot.GetNormalEncoderTicks());
                    telemetry.update();

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) robot.stoneTarget.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                } else {

                    telemetry.addData("Visible Target", "none");
                    float power = 0.3f;

                    /*robot.Right_Front_Wheel.setPower(power * -0.57);
                    robot.Left_Front_Wheel.setPower(power * 1);
                    robot.Right_Rear_Wheel.setPower(power * 0.55);
                    robot.Left_Rear_Wheel.setPower(power * -1); */

                   // robot.Slide_Right((float)0.2);
                    robot.Right_Front_Wheel.setPower(power * -1);
                    robot.Left_Rear_Wheel.setPower(power * -0.70);
                    robot.Right_Rear_Wheel.setPower(power * 1);
                    robot.Left_Front_Wheel.setPower(power * 0.70);


                    sleep(300);
                    robot.Stop();
                    sleep(50);

                    telemetry.addData("Left Ticks", robot.GetLeftVerticalEncoderTicks());
                    telemetry.addData("Right Ticks", robot.GetRightVerticalEncoderTicks());
                    telemetry.addData("Normal Ticks", robot.GetNormalEncoderTicks());
                    telemetry.update();
                }
            }

            robot.Stop();
            // Provide feedback as to where the robot is located (if we know).

            VectorF translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
            telemetry.addData("Found", "skystone");
            // express the rotation of the robot in degrees.
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            telemetry.addData("Location logged", "Logged");
            telemetry.update();

            // Move sideways to adjust the arm above the stone
            while (!isStopRequested() && robot.GetNormalEncoderTicks() < normalEncoderTicks + (4.5 * 308)) {
                /*float power = 0.2f;
                robot.Right_Front_Wheel.setPower(power * -1);
                robot.Left_Front_Wheel.setPower(power * 0.9);
                robot.Right_Rear_Wheel.setPower(power * 1);
                robot.Left_Rear_Wheel.setPower(power * -1);*/
                robot.Slide_Right((float)0.2);

                telemetry.addData("Move Sideways Left Ticks", robot.GetLeftVerticalEncoderTicks());
                telemetry.addData("Move Sideways Right Ticks", robot.GetRightVerticalEncoderTicks());
                telemetry.addData("Move Sideways Normal Ticks", robot.GetNormalEncoderTicks());
                telemetry.update();
            }

            robot.Stop();

            float inchesToMoveForward = (float) ((Math.abs(translation.get(0) / mmPerInch) - 1));
            telemetry.addData("Before Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
            telemetry.addData("Before Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
            telemetry.addData("Inches To Move Forward", inchesToMoveForward);
            telemetry.update();

            // Move forward to get closer to tbe stone
            while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() < 6475 + (inchesToMoveForward * 308)) {
                MoveRobot(0.2, true, 0);

                telemetry.addData("Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
                telemetry.addData("Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
                telemetry.update();
            }

            robot.Stop();

            ElapsedTime2.reset();
            while (!((ElapsedTime2.milliseconds() >= 300) || isStopRequested())) {
                robot.LeftBlockGrabber((float) -0.7);
            }
            sleep(500);
            robot.LeftBlockGrabber(0);

            // Move backward while holding the block
            int currentPosition = robot.GetLeftVerticalEncoderTicks();
            while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() > currentPosition - (1.5 * 308)) {
                MoveRobot(0.75, false, 0);

                telemetry.addData("Move Forward Left Ticks", robot.GetLeftVerticalEncoderTicks());
                telemetry.addData("Move Forward Right Ticks", robot.GetRightVerticalEncoderTicks());
                telemetry.update();
            }

            robot.Stop();

            // Get the distance we have travelled to detect the block
            int normalTravelDistance = Math.abs(robot.GetNormalEncoderTicks());
            // Turn the base to face the wall
            float Yaw_Angle = robot.GetRobotYaw_Angle();
            while (!(Yaw_Angle >= 90 || isStopRequested())) {
                // Update Yaw-Angle variable with current yaw.
                Yaw_Angle = robot.GetRobotYaw_Angle();
                // Report yaw orientation to Driver Station.
                telemetry.addData("Yaw value on turning", Yaw_Angle);
                telemetry.update();

                robot.Left((float) 0.2);
            }
            robot.Stop();

            currentPosition = robot.GetLeftVerticalEncoderTicks();

            ElapsedTime2.reset();
            // Go to building zone while holding the block also start releasing the grabber
            while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() < currentPosition + normalTravelDistance + (27 * 308)) {
                MoveRobot(0.7, true, -90);

                if (!((ElapsedTime2.milliseconds() >= 300) || isStopRequested())) {
                    robot.LeftBlockGrabber((float) 0.7);
                }

            }
            robot.Stop();

            // finish the release
            while (!((ElapsedTime2.milliseconds() >= 300) || isStopRequested())) {
                robot.LeftBlockGrabber((float) 0.7);
            }

            robot.LeftBlockGrabber(0);
            robot.Stop();


            // Come back to parking spots
            currentPosition = robot.GetLeftVerticalEncoderTicks();
            while (!isStopRequested() && robot.GetLeftVerticalEncoderTicks() > currentPosition - (8 * 308)) {
                MoveRobot(0.75, false, 0);
            }
            robot.Stop();
        }

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
            if (forward) {
                // Turn left
                Left_Power = basePower - 0.02;
                Right_Power = basePower + 0.02;
            } else {
                Left_Power = basePower + 0.02;
                Right_Power = basePower - 0.02;
            }
        } else if (Yaw_Angle > 0.05 + maintainAngle) {
            if (forward) {
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
    }
}
