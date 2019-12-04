package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TestJoySticks (Blocks to Java)", group = "")
public class TestJoySticks extends LinearOpMode {

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

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                if (Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x)) {
                    if (gamepad1.left_stick_y < 0) {
                        forward();
                    } else if (gamepad1.left_stick_y > 0) {
                        backward();
                    }
                } else if (Math.abs(gamepad1.left_stick_y) < Math.abs(gamepad1.left_stick_x)) {
                    if (gamepad1.left_stick_x > 0) {
                        right();
                    } else if (gamepad1.left_stick_x < 0) {
                        left();
                    }
                } else {
                    if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_x == 0) {
                        stop2();
                    }
                }
                if (gamepad1.right_stick_x < 0) {
                    slide_left();
                } else if (gamepad1.right_stick_x > 0) {
                    slide_right();
                } else {
                    if (gamepad1.right_stick_x == 0 && gamepad1.right_stick_x == 0) {
                        stop2();
                    }
                }
                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void backward() {
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setPower(-1);
        RightBackMotor.setPower(-1);
        RightFrontMotor.setPower(-1);
        LeftFrontMotor.setPower(-1);
    }

    /**
     * Describe this function...
     */
    private void forward() {
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setPower(1);
        RightBackMotor.setPower(1);
        RightFrontMotor.setPower(1);
        LeftFrontMotor.setPower(1);
    }

    /**
     * Describe this function...
     */
    private void right() {
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setPower(1);
        RightBackMotor.setPower(-1);
        RightFrontMotor.setPower(-1);
        LeftFrontMotor.setPower(1);
    }

    /**
     * Describe this function...
     */
    private void left() {
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setPower(-1);
        RightBackMotor.setPower(1);
        RightFrontMotor.setPower(1);
        LeftFrontMotor.setPower(-1);
    }

    /**
     * Describe this function...
     */
    private void stop2() {
        LeftBackMotor.setPower(0);
        RightBackMotor.setPower(0);
        RightFrontMotor.setPower(0);
        LeftFrontMotor.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void slide_left() {
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setPower(1);
        RightBackMotor.setPower(-1);
        RightFrontMotor.setPower(1);
        LeftFrontMotor.setPower(-1);
    }

    /**
     * Describe this function...
     */
    private void slide_right() {
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setPower(-1);
        RightBackMotor.setPower(1);
        RightFrontMotor.setPower(-1);
        LeftFrontMotor.setPower(1);
    }
}