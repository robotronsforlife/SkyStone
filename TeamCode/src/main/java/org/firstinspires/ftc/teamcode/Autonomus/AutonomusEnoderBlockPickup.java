package org.firstinspires.ftc.teamcode.Autonomus;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Robotron.Robot;

@Autonomous(name = "Turn Arm For Skystone Pickup A", group = "")
public class AutonomusEnoderBlockPickup extends LinearOpMode {

    private Robot robot;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        robot = new Robot();
        robot.Init(hardwareMap, telemetry, true);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        waitForStart();
        double Elbow1TickCount = 5264 * 2 ;
        double Elbow2TickCount = 5264 * 2 ;
        double Elbow3TickCount = 5264 * 2 ;
        double Elbow4TickCount = 2786 * 2 ;




        //Elbow2 Operation Below
        double Elbow2Turn = (Elbow2TickCount / 360) * 130 ;
        int newTargetElbow2 = robot.Elbow2.getTargetPosition() + (int)Elbow2Turn;
        robot.Elbow2.setTargetPosition(newTargetElbow2);
        robot.Elbow2.setPower(1);
        robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow2.isBusy()){
            telemetry.addData("Arm Position", robot.Elbow2.getTargetPosition());
            telemetry.update();
        }
        robot.Elbow2.setPower(0);

        //Griper Opener
        robot.Gripper(1);
        sleep(650);
        robot.Gripper(0);

        //Elbow4 Down
        double Elbow4Turn = (Elbow4TickCount / 360) * 120 ;

        int newTargetElbow4 = robot.Elbow4.getTargetPosition() + (int)Elbow4Turn;
        robot.Elbow4.setTargetPosition(newTargetElbow4);
        robot.Elbow4.setPower(-1);
        robot.Elbow4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow4.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow4.isBusy()){
            telemetry.addData("Arm Position", robot.Elbow4.getTargetPosition());
            telemetry.update();
        }
        robot.Elbow4.setPower(0);


        //Elbow2 Operation Below
        double Elbow2Turn2 = (Elbow2TickCount / 360) * 130 ;
        int newTargetElbow2_2 = robot.Elbow2.getTargetPosition() + (int)Elbow2Turn2;
        robot.Elbow2.setTargetPosition(newTargetElbow2);
        robot.Elbow2.setPower(1);
        robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow2.isBusy()){
            telemetry.addData("Arm Position", robot.Elbow2.getTargetPosition());
            telemetry.update();
        }
        robot.Elbow2.setPower(0);




        sleep(3000);

        //Elbow 4 reset

        newTargetElbow4 = robot.Elbow4.getTargetPosition() - (int)Elbow4Turn;
        robot.Elbow4.setTargetPosition(newTargetElbow4);
        robot.Elbow4.setPower(-1);
        robot.Elbow4.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (robot.Elbow4.isBusy()){
            telemetry.addData("Arm Position", robot.Elbow4.getTargetPosition());
            telemetry.update();
        }
        robot.Elbow4.setPower(0);

        //Griper Reset
        robot.Gripper(-1);
        sleep(650);
        robot.Gripper(0);

        // Elbow2 Reset
        newTargetElbow2 = robot.Elbow2.getTargetPosition() - (int)Elbow2Turn;
        robot.Elbow2.setTargetPosition(newTargetElbow2);
        robot.Elbow2.setPower(-1);
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



