package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Robotron.Robot;

@Autonomous(name = "Avanthi Test Arm Encoders", group = "")
public class ArmEncoderTest extends LinearOpMode {

    private Robot robot;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        robot = new Robot();
        robot.Init(hardwareMap, telemetry, false);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        waitForStart();
        double Elbow1TickCount = 5264 ;
        double Elbow2TickCount = 5264;
        double Elbow3TickCount = 5264 ;
        double Elbow4TickCount = 1993.6  ;
        double Elbow2QuarterTurn = 5264 / 2;


        int newTarget = robot.Elbow2.getTargetPosition() + (int)Elbow2QuarterTurn;
        robot.Elbow2.setTargetPosition(newTarget);
        robot.Elbow2.setPower(1);
        robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow2.isBusy()){
            telemetry.addData("Arm Position", robot.Elbow2.getTargetPosition());
            telemetry.update();
        }
        robot.Elbow2.setPower(0);
        sleep(3000);

        newTarget = robot.Elbow2.getTargetPosition() - (int)Elbow2QuarterTurn;
        robot.Elbow2.setTargetPosition(newTarget);
        robot.Elbow2.setPower(-1);
        robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow2.isBusy()){
            telemetry.addData("Arm Position", robot.Elbow2.getTargetPosition());
            telemetry.update();
        }
        robot.Elbow2.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();

    }
}

