package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "BaseMoverTestJavaA (Blocks to Java)", group = "")
public class AutonomousMode extends LinearOpMode {

    public DcMotor Right_Front_Wheel;
    public DcMotor Left_Front_Wheel;
    public DcMotor Right_Rear_Wheel;
    public DcMotor Left_Rear_Wheel;
    public CRServo Right_Puller;
    public CRServo Left_Puller;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
@Override
public void runOpMode() {
    Right_Puller = hardwareMap.crservo.get("Right Puller");
    Left_Puller = hardwareMap.crservo.get("Left Puller");
    Right_Rear_Wheel = hardwareMap.dcMotor.get("Right_Rear_Motor");
    Right_Front_Wheel = hardwareMap.dcMotor.get("Right_Front_Motor");
    Left_Rear_Wheel = hardwareMap.dcMotor.get("Left_Rear_Motor");
    Left_Front_Wheel = hardwareMap.dcMotor.get("Left_Front_Motor");

    // Put initialization blocks here.
    Right_Puller.setDirection(DcMotorSimple.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
        // Put run blocks here.
        SlideRight();
        sleep(1775);
        MoveForward();
        sleep(1150);
        Stop();
        sleep(700);
        // servo Here
        Right_Puller.setPower(1);
        Left_Puller.setPower(1);
        sleep(750);
        MoveBackward();
        Left_Puller.setPower(0.1);
        Right_Puller.setPower(0.1);
        sleep(400);
        MoveBackward();
        Left_Puller.setPower(0.1);
        Right_Puller.setPower(0.1);
        sleep(700);
        TurnRight();
        Left_Puller.setPower(0.1);
        Right_Puller.setPower(0.1);
        sleep(1100);
        MoveForward();
        Left_Puller.setPower(0.1);
        Right_Puller.setPower(0.1);
        sleep(300);
        SlideRight();
        Left_Puller.setPower(0.1);
        Right_Puller.setPower(0.1);
        sleep(400);
        Stop();
        Left_Puller.setPower(0.1);
        Right_Puller.setPower(0.1);
        sleep(150);
        // ServoRelease
        Left_Puller.setPower(-0.5);
        Right_Puller.setPower(-0.5);
        sleep(1000);
        // park
        MoveBackward();
        sleep(1750);
    }
}

    /**
     * Describe this function...
     */
    private void MoveForward() {
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Rear_Wheel.setPower(0.5);
        Right_Rear_Wheel.setPower(0.5);
        Left_Front_Wheel.setPower(0.5);
        Right_Front_Wheel.setPower(0.5);
    }

    /**
     * Describe this function...
     */
    private void MoveBackward() {
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Rear_Wheel.setPower(-0.5);
        Right_Rear_Wheel.setPower(-0.5);
        Left_Front_Wheel.setPower(-0.5);
        Right_Front_Wheel.setPower(-0.5);
    }

    /**
     * Describe this function...
     */
    private void Stop() {
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Rear_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(0);
        Left_Front_Wheel.setPower(0);
        Right_Front_Wheel.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void SlideLeft() {
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Rear_Wheel.setPower(0.5);
        Right_Rear_Wheel.setPower(-0.5);
        Left_Front_Wheel.setPower(-0.5);
        Right_Front_Wheel.setPower(0.5);
    }

    /**
     * Describe this function...
     */
    private void SlideRight() {
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Rear_Wheel.setPower(-0.5);
        Right_Rear_Wheel.setPower(0.5);
        Left_Front_Wheel.setPower(0.5);
        Right_Front_Wheel.setPower(-0.5);
    }

    /**
     * Describe this function...
     */
    private void TurnRight() {
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Rear_Wheel.setPower(-0.5);
        Right_Rear_Wheel.setPower(0.5);
        Left_Front_Wheel.setPower(-0.5);
        Right_Front_Wheel.setPower(0.5);
    }
}
