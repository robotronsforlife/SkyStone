package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@Autonomous(name = "SlideLeft")

public class SlideLeft extends LinearOpMode {
    private Robot robot;
    int normalEncoderTicks = 0;

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
            while (!isStopRequested()) {

                robot.Right_Front_Wheel.setPower(0.5);
                robot.Left_Rear_Wheel.setPower(0.5);
                robot.Left_Front_Wheel.setPower(-0.5);
                robot.Right_Rear_Wheel.setPower(-0.5);


                telemetry.addData("Move Sideways Left Ticks", robot.GetLeftVerticalEncoderTicks());
                telemetry.addData("Move Sideways Right Ticks", robot.GetRightVerticalEncoderTicks());
                telemetry.addData("Move Sideways Normal Ticks", robot.GetNormalEncoderTicks());
                telemetry.update();
            }
            robot.Right_Front_Wheel.setPower(0);
            robot.Left_Rear_Wheel.setPower(0);
            robot.Left_Front_Wheel.setPower(0);
            robot.Right_Rear_Wheel.setPower(0);


        }
    }
}