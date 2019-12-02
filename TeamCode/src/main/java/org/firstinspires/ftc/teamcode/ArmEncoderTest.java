package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Test Arm Encoders", group = "")
public class ArmEncoderTest extends LinearOpMode {

    private DcMotor Elbow3;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        Elbow3 = hardwareMap.dcMotor.get("Elbow3");
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        waitForStart();
        double quarterTurn = 1993.6 ;
        Elbow3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int newTarget = Elbow3.getTargetPosition() + (int)quarterTurn;
        Elbow3.setTargetPosition(newTarget);
        Elbow3.setPower(1);
        Elbow3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Elbow3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (Elbow3.isBusy()){
            telemetry.addData("Status", "Running the motor to a quarter turn.");
            telemetry.update();
        }
        Elbow3.setPower(0);
        sleep(3000);
        newTarget = Elbow3.getTargetPosition() - (int)quarterTurn;
        Elbow3.setTargetPosition(newTarget);
        Elbow3.setPower(-0.5);
        Elbow3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Elbow3.isBusy()){
            telemetry.addData("Status", "Running the motor Back a quarter turn.");
            telemetry.update();
        }
        Elbow3.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();

    }
}