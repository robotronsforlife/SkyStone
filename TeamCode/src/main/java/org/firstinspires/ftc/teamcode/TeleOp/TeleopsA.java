package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleopsA (Blocks to Java)", group = "")
public class TeleopsA extends LinearOpMode {

    private DcMotor Left_Front_Wheel;
    private DcMotor Right_Front_Wheel;
    private DcMotor Left_Rear_Wheel;
    private DcMotor Right_Rear_Wheel;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        Left_Front_Wheel = hardwareMap.dcMotor.get("Left_Front_Wheel");
        Right_Front_Wheel = hardwareMap.dcMotor.get("Right_Front_Wheel");
        Left_Rear_Wheel = hardwareMap.dcMotor.get("Left_Rear_Wheel");
        Right_Rear_Wheel = hardwareMap.dcMotor.get("Right_Rear_Wheel");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void LeftFrontMotorStop() {
        Left_Front_Wheel.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void RightFrontMotorStop() {
        Right_Front_Wheel.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void LeftBackMotorStop() {
        Right_Front_Wheel.setPower(0);
    }

    private void RightBackMotorStop() {
        Right_Front_Wheel.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void LeftFrontMotorBackward(double Power) {
        Left_Front_Wheel.setPower(Power * -1);
    }

    /**
     * Describe this function...
     */
    private void RightFrontMotorBackward(double Power) {
        Left_Front_Wheel.setPower(Power);
    }

    /**
     * Describe this function...
     */
    private void LeftFrontMotorForward(double Power) {
        Left_Front_Wheel.setPower(Power);
    }

    /**
     * Describe this function...
     */
    private void RightFrontMotorForward(double Power) {
        Left_Front_Wheel.setPower(Power * -1);
    }

    /**
     * Describe this function...
     */
    private void LeftRearMotorStop() {
        Left_Rear_Wheel.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void RightRearMotorStop() {
        Right_Rear_Wheel.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void LeftRearMotorBackward(double Power) {
        Left_Front_Wheel.setPower(Power * -1);
    }

    /**
     * Describe this function...
     */
    private void RightRearMotorBackward(double Power) {
        Left_Front_Wheel.setPower(Power);
    }

    /**
     * Describe this function...
     */
    private void LeftRearMotorForward(double Power) {
        Left_Front_Wheel.setPower(Power);
    }


    private void DiagonalLeftUp() {
        LeftFrontMotorStop();
        RightFrontMotorStop();
        LeftBackMotorStop();
        RightBackMotorStop();
        
    }


}


