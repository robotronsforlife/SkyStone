package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "DriverMode (Blocks to Java)", group = "")
public class DriverMode extends LinearOpMode {

    private DcMotor LeftBackMotor;
    private DcMotor LeftFrontMotor;
    private DcMotor RightFrontMotor;
    private DcMotor RightBackMotor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        LeftBackMotor = hardwareMap.dcMotor.get("Left Back Motor");
        LeftFrontMotor = hardwareMap.dcMotor.get("Left Front Motor");
        RightFrontMotor = hardwareMap.dcMotor.get("Right Front Motor");
        RightBackMotor = hardwareMap.dcMotor.get("Right Back Motor");

        waitForStart();
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // Use left stick to drive and right stick to turn
                LeftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                LeftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                RightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                RightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                if (gamepad1.x) {
                    RightFrontMotor.setPower(1);
                    LeftBackMotor.setPower(1);
                    LeftFrontMotor.setPower(0);
                    RightBackMotor.setPower(0);
                } else if (gamepad1.y) {
                    RightBackMotor.setPower(1);
                    LeftFrontMotor.setPower(1);
                    LeftBackMotor.setPower(0);
                    RightFrontMotor.setPower(0);
                } else if (gamepad1.b) {
                    RightFrontMotor.setPower(-1);
                    LeftBackMotor.setPower(-1);
                    RightBackMotor.setPower(0);
                    LeftFrontMotor.setPower(0);
                } else if (gamepad1.a) {
                    RightBackMotor.setPower(-1);
                    LeftFrontMotor.setPower(-1);
                    RightFrontMotor.setPower(0);
                    LeftBackMotor.setPower(0);
                } else if (gamepad1.left_bumper) {
                    RightFrontMotor.setPower(1);
                    LeftFrontMotor.setPower(-1);
                    LeftBackMotor.setPower(1);
                    RightBackMotor.setPower(-1);
                } else if (gamepad1.right_bumper) {
                    RightFrontMotor.setPower(-1);
                    LeftFrontMotor.setPower(1);
                    LeftBackMotor.setPower(-1);
                    RightBackMotor.setPower(1);
                } else if (gamepad1.dpad_up) {
                    LeftBackMotor.setPower(1);
                    LeftFrontMotor.setPower(1);
                    RightFrontMotor.setPower(1);
                    RightBackMotor.setPower(1);
                } else if (gamepad1.dpad_down) {
                    LeftBackMotor.setPower(-1);
                    LeftFrontMotor.setPower(-1);
                    RightFrontMotor.setPower(-1);
                    RightBackMotor.setPower(-1);
                } else if (gamepad1.dpad_right) {
                    LeftBackMotor.setPower(1);
                    LeftFrontMotor.setPower(1);
                    RightFrontMotor.setPower(-1);
                    RightBackMotor.setPower(-1);
                } else if (gamepad1.dpad_left) {
                    LeftBackMotor.setPower(-1);
                    LeftFrontMotor.setPower(-1);
                    RightBackMotor.setPower(1);
                    RightFrontMotor.setPower(1);
                } else if (gamepad1.atRest()) {
                    LeftBackMotor.setPower(0);
                    LeftFrontMotor.setPower(0);
                    RightFrontMotor.setPower(0);
                    RightBackMotor.setPower(0);
                }
                telemetry.addData("Left Front Pow", LeftFrontMotor.getPower());
                telemetry.addData("Right Front Pow", RightFrontMotor.getPower());
                telemetry.addData("Left Back Pow", LeftBackMotor.getPower());
                telemetry.addData("Right Back Pow", RightBackMotor.getPower());
                telemetry.update();
            }
        }
        // Reverse one of the drive motors.
    }
}



