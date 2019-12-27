package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "MoveRight (Blocks to Java)", group = "")
public class SlideRight extends LinearOpMode {

    private CRServo Right_Puller;
    private DcMotor Elbow4;
    private DcMotor Right_Front_Wheel;
    private DcMotor Right_Rear_Wheel;
    private DcMotor Left_Front_Wheel;
    private DcMotor Left_Rear_Wheel;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        Right_Puller = hardwareMap.crservo.get("Right_Puller");
        Elbow4 = hardwareMap.dcMotor.get("Elbow4");
        Right_Front_Wheel = hardwareMap.dcMotor.get("Right_Front_Wheel");
        Right_Rear_Wheel = hardwareMap.dcMotor.get("Right_Rear_Wheel");
        Left_Front_Wheel = hardwareMap.dcMotor.get("Left_Front_Wheel");
        Left_Rear_Wheel = hardwareMap.dcMotor.get("Left_Rear_Wheel");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                Elbow4.setPower(0.5);
                sleep(2600);
                Elbow4.setPower(0);
                sleep(100);
                slideRight();
                telemetry.update();
                break;
            }
        }
    }

    /**
     * Describe this function...
     */
    private void slideRight() {
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Rear_Wheel.setPower(0.5);
        Left_Front_Wheel.setPower(0.5);
        Right_Front_Wheel.setPower(-0.5);
        Left_Rear_Wheel.setPower(-0.5);
        sleep(400);
    }

    /**
     * Describe this function...
     */
    private void slideLeft() {
        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Rear_Wheel.setPower(-0.5);
        Left_Front_Wheel.setPower(-0.5);
        Right_Front_Wheel.setPower(0.5);
        Left_Rear_Wheel.setPower(0.5);
        sleep(400);
    }
}

