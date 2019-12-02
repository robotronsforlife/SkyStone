package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Sky stone", group = "")
public class SeekSkystone extends LinearOpMode {

    private DcMotor Elbow4;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        Elbow4 = hardwareMap.dcMotor.get("Elbow4");
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        waitForStart();
        double quarterTurn = 300 ;
        Elbow4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int newTarget = Elbow4.getTargetPosition() + (int)quarterTurn;
        Elbow4.setTargetPosition(newTarget);
        Elbow4.setPower(-0.25);
        Elbow4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Elbow4.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (Elbow4.isBusy()){
            telemetry.addData("Status", "Running the motor to a quarter turn.");
            telemetry.update();
        }
        Elbow4.setPower(0);
        sleep(3000);
        newTarget = Elbow4.getTargetPosition() - (int)quarterTurn;
        Elbow4.setTargetPosition(newTarget);
        Elbow4.setPower(0.25);
        Elbow4.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(Elbow4.isBusy()){
            telemetry.addData("Status", "Running the motor Back a quarter turn.");
            telemetry.update();
        }
        Elbow4.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();

    }
}