package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.Math;
//import java.lang.Float;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.Robotron.Robot;

@TeleOp(name = "TeleOps Test", group = "")
public class TeleOps extends LinearOpMode {


    private Robot robot;

    double Elbow1TickCount = 5264 * 2 ;
    double Elbow2TickCount = (5264 * 2) / 360 ;
    double Elbow3TickCount = 5264 * 2 ;
    double Elbow4TickCount = (2786 * 2) / 360 ;
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        robot = new Robot();
        robot.Init(hardwareMap, telemetry, false);

        waitForStart();

        int currentElbow2TargetPosition = robot.Elbow2.getCurrentPosition();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //This one takes care of Forward , Reverse, Right and Left
                if (Math.abs(gamepad1.left_stick_y) > Math.abs((gamepad1.left_stick_x))) {
                    if (gamepad1.left_stick_y < 0) {
                       robot.Forward(Math.abs(gamepad1.left_stick_y));
                    } else if (gamepad1.left_stick_y > 0) {
                        robot.Reverse(Math.abs(gamepad1.left_stick_y));
                    }
                }
                else if(Math.abs(gamepad1.left_stick_y) < Math.abs((gamepad1.left_stick_x))){
                    if(gamepad1.left_stick_x > 0){

                        robot.Right(Math.abs(gamepad1.left_stick_x));
               } else if (gamepad1.left_stick_x < 0){
                        robot.Left(Math.abs(gamepad1.left_stick_x));
                    }
                }
                else {
                    if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0){
                        robot.Stop();
                    }
                }

                //This one takes care of Diagonal Right Up , Diagonal Right Down, Diagonal Left Up, Diagonal Left Down
                if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y < 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        robot.Diagonal_Left_Up(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        robot.Diagonal_Left_Up(Math.abs(gamepad1.right_stick_y));
                    }
                }
                else if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y > 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        robot.Diagonal_Left_Down(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        robot.Diagonal_Left_Down(Math.abs(gamepad1.right_stick_y));
                    }

                }
                else if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y < 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        robot.Diagonal_Right_Up(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        robot.Diagonal_Right_Up(Math.abs(gamepad1.right_stick_y));
                    }
                }
                else if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y > 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        robot.Diagonal_Right_Down(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        robot.Diagonal_Right_Down(Math.abs(gamepad1.right_stick_y));
                    }
                }
                else{
                    if(gamepad1.right_stick_x == 0 && gamepad1.right_stick_y == 0){
                        robot.Stop();
                    }
                }

                //Slide Code is Here
                if(gamepad1.right_trigger <= 1 && gamepad1.right_trigger >0){
                    robot.Slide_Right(gamepad1.right_trigger);
                }
                else if(gamepad1.left_trigger <= 1 && gamepad1.left_trigger >0){
                    robot.Slide_Left(gamepad1.left_trigger);
                }
                else{
                    robot.Stop();
                }

                //servo puller - left and right
                if(gamepad1.a && gamepad1.x){
                    robot.RightPuller(-1);
                    robot.LeftPuller(-1);
                }
                else if(gamepad1.b && gamepad1.y){
                    robot.RightPuller(1);
                    robot.LeftPuller(1);
                }
                else if (gamepad1.a){
                    robot.RightPuller(-1);
                }
                else if (gamepad1.b){
                    robot.RightPuller(1);
                }
                else if (gamepad1.x){
                    robot.LeftPuller(-1);
                }
                else if(gamepad1.y){
                    robot.LeftPuller(1);
                }
                else{
                    robot.LeftPuller(0);
                    robot.RightPuller(0);
                }

                //Arm Position Code is Here


                // Arm Code is Here
                if(gamepad2.dpad_up){
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.MoveElbow1(1);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                }
                else if(gamepad2.dpad_down) {
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.MoveElbow1(-1);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                }
                else if(gamepad2.dpad_left){
                    //NUMBER GOES DOWN
                    robot.State--;
                    telemetry.addData("State:", robot.State);
                    telemetry.update();
                }
                else if(gamepad2.dpad_right){
                    //NUMBER GOES UP
                    robot.State++;
                    telemetry.addData("State:", robot.State);
                    telemetry.update();
                }
                else if(gamepad2.left_stick_y<0){
                    robot.MoveElbow2(Math.abs(gamepad2.left_stick_y));
                }
                else if(gamepad2.left_stick_y>0){
                    robot.MoveElbow2(Math.abs(gamepad2.left_stick_y)* -1);
                }
                else if(gamepad2.right_stick_y <0){
                    robot.MoveElbow3(Math.abs(gamepad2.right_stick_y));
                }
                else if(gamepad2.right_stick_y > 0){
                    robot.MoveElbow3(Math.abs(gamepad2.right_stick_y) * -1);
                }
                else if(gamepad2.y){
                    robot.MoveElbow4(-0.5);
                }
                else if(gamepad2.a){
                    robot.MoveElbow4(0.5);
                }

                else if (gamepad2.x){
                    //Elbow2 Operation Below

                    double Elbow2Turn = Elbow2TickCount  * 130;
                    int newTargetElbow2 = (int)Elbow2Turn;
                    robot.Elbow2.setTargetPosition(newTargetElbow2);
                    robot.Elbow2.setPower(1);
                    robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (opModeIsActive() && robot.Elbow2.isBusy()){
                        telemetry.addData("In side While Arm Position", robot.Elbow2.getTargetPosition());
                        telemetry.update();
                        idle();
                    }

                    robot.Elbow2.setPower(0);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    //Griper Opener
                    robot.Gripper(1);
                    sleep(900);
                    robot.Gripper(0);

                    // Elbow 4
                    double Elbow4Turn = Elbow4TickCount * 130;
                    robot.Elbow4.setTargetPosition((int)Elbow4Turn);
                    robot.Elbow4.setPower(-1);
                    robot.Elbow4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.Elbow4.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while (robot.Elbow4.isBusy()){
                        telemetry.addData("Arm Position", robot.Elbow4.getTargetPosition());
                        telemetry.update();
                        idle();
                    }
                    robot.Elbow4.setPower(0);
                    robot.Elbow4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    double Elbow2Turn_1 = Elbow2TickCount  * 40;
                    int newTargetElbow2_1 = (int)Elbow2Turn_1;
                    robot.Elbow2.setTargetPosition(newTargetElbow2_1);
                    robot.Elbow2.setPower(1);
                    robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (opModeIsActive() && robot.Elbow2.isBusy()){
                        telemetry.addData("In side While Arm Position", robot.Elbow2.getTargetPosition());
                        telemetry.update();
                        idle();
                    }

                    robot.Elbow2.setPower(0);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                }

                else if (gamepad2.b) {
                    //Elbow2 Operation Below

                    double Elbow2Turn = Elbow2TickCount * 170;
                    int newTargetElbow2 = (int) Elbow2Turn;
                    robot.Elbow2.setTargetPosition(-newTargetElbow2);
                    robot.Elbow2.setPower(-1);
                    robot.Elbow2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (opModeIsActive() && robot.Elbow2.isBusy()) {
                        telemetry.addData("In side While Arm Position", robot.Elbow2.getTargetPosition());
                        telemetry.update();
                        idle();
                    }

                    robot.Elbow2.setPower(0);
                    robot.Elbow2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }

                else{
                    robot.MoveElbow1(0);
                    robot.MoveElbow2(0);
                    robot.MoveElbow3(0);
                    robot.MoveElbow4(0);
                }

                if(gamepad2.left_bumper){
                    robot.TurnGripper(0.75);
                }
                else if(gamepad2.right_bumper){
                    robot.TurnGripper(-0.75);
                }
                else if(gamepad2.right_trigger > 0 && gamepad2.right_trigger <= 1){
                    robot.Gripper(Math.abs(gamepad2.right_trigger) * -1);
                }
                else if(gamepad2.left_trigger > 0 && gamepad2.left_trigger <= 1){
                    robot.Gripper(gamepad2.left_trigger);
                }
                else{
                    robot.Gripper(0);
                    robot.TurnGripper(0);
                }

            }
            telemetry.addData("Path", "Complete");
            telemetry.update();
        }
    }

    public void BringArmToPickerPosition(){

    }

    public void MoveArmMotors(DcMotor dcMotor, double power ){
        dcMotor.setPower(power);
    }
}
