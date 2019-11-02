package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "AutonomousMode (Blocks to Java)", group = "")
public class AutonomousMode extends LinearOpMode {

    private DcMotor LeftBackMotor;
    private DcMotor LeftFrontMotor;
    private DcMotor RightBackMotor;
    private DcMotor RightFrontMotor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        LeftBackMotor = hardwareMap.dcMotor.get("Left Back Motor");
        LeftFrontMotor = hardwareMap.dcMotor.get("Left Front Motor");
        RightBackMotor = hardwareMap.dcMotor.get("Right Back Motor");
        RightFrontMotor = hardwareMap.dcMotor.get("Right Front Motor");

        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            LeftBackMotor.setPower(1);
            LeftFrontMotor.setPower(1);
            RightBackMotor.setPower(1);
            RightFrontMotor.setPower(1);
            sleep(1000);
            LeftBackMotor.setPower(0);
            LeftFrontMotor.setPower(0);
            RightBackMotor.setPower(0);
            RightFrontMotor.setPower(0);
            // Put loop blocks here.
            telemetry.update();
        }
    }
}