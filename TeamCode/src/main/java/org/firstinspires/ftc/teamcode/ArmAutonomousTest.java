package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.Math;
//import java.lang.Float;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.Robotron.Robot;


@Autonomous(name = "Test Arm Encoders", group = "")
public class ArmAutonomousTest extends LinearOpMode {
    private Robot robot;



    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        //Elbow3 = hardwareMap.dcMotor.get("Elbow3");
        robot.Init(hardwareMap, telemetry);
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        waitForStart();
        double Elbow1TickCount = 5264 ;
        double Elbow2TickCount = 5264 ;
        double Elbow3TickCount = 1993.6 ;
        double Elbow4TickCount = 5264 ;
        robot.Elbow1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int newTarget = robot.Elbow1.getTargetPosition() + (int)Elbow2TickCount;
        robot.Elbow2.setTargetPosition(newTarget);
        robot.Elbow2.setPower(1);
        robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow2.isBusy()){
            telemetry.addData("Status", "Running the motor to a quarter turn.");
            telemetry.update();
        }
        robot.Elbow2.setPower(0);
        sleep(3000);
        newTarget = robot.Elbow2.getTargetPosition() - (int)Elbow2TickCount;
        robot.Elbow2.setTargetPosition(newTarget);
        robot.Elbow2.setPower(-0.5);
        robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(robot.Elbow2.isBusy()){
            telemetry.addData("Status", "Running the motor Back a quarter turn.");
            telemetry.update();
        }
        robot.Elbow2.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();

    }
}
