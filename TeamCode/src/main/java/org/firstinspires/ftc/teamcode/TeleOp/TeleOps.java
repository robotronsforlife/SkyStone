package org.firstinspires.ftc.teamcode.TeleOp;

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

@TeleOp(name = "TEST", group = "")
public class TeleOps extends LinearOpMode {


    public DcMotor Right_Front_Wheel;
    public DcMotor Left_Front_Wheel;
    public DcMotor Right_Rear_Wheel;
    public DcMotor Left_Rear_Wheel;
    public DcMotor Elbow1;
    public DcMotor Elbow2;
    public DcMotor Elbow3;
    public DcMotor Elbow4;
    public CRServo Right_Puller;
    public CRServo Left_Puller;
    public CRServo Grip_Servo;
    public CRServo Turn_Servo;



    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

        Right_Front_Wheel = hardwareMap.dcMotor.get("Right_Front_Wheel");
        Left_Front_Wheel = hardwareMap.dcMotor.get("Left_Front_Wheel");
        Right_Rear_Wheel = hardwareMap.dcMotor.get("Right_Rear_Wheel");
        Left_Rear_Wheel = hardwareMap.dcMotor.get("Left_Rear_Wheel");
        Elbow1=hardwareMap.dcMotor.get("Elbow1");
        Elbow2=hardwareMap.dcMotor.get("Elbow2");
        Elbow3=hardwareMap.dcMotor.get("Elbow3");
        Elbow4=hardwareMap.dcMotor.get("Elbow4");
        Right_Puller = hardwareMap.crservo.get("Right_Puller");
        Left_Puller = hardwareMap.crservo.get("Left_Puller");
        Turn_Servo = hardwareMap.crservo.get("Turn_Servo");
        Grip_Servo = hardwareMap.crservo.get("Grip_Servo");



        double elbowOneTickCount = 5264;

        double elbowOneCurrentTickCount = 0;

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //This one takes care of Forward , Reverse, Right and Left
                if (Math.abs(gamepad1.left_stick_y) > Math.abs((gamepad1.left_stick_x))) {
                    if (gamepad1.left_stick_y < 0) {
                        Forward(Math.abs(gamepad1.left_stick_y));
                    } else if (gamepad1.left_stick_y > 0) {
                        Reverse(Math.abs(gamepad1.left_stick_y));
                    }
                }
                else if(Math.abs(gamepad1.left_stick_y) < Math.abs((gamepad1.left_stick_x))){
                    if(gamepad1.left_stick_x > 0){
                        Right(Math.abs(gamepad1.left_stick_x));
                    }
                    else if (gamepad1.left_stick_x < 0){
                        Left(Math.abs(gamepad1.left_stick_x));
                    }
                }
                else {
                    if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0){
                        Stop();
                    }
                }

                //This one takes care of Diagonal Right Up , Diagonal Right Down, Diagonal Left Up, Diagonal Left Down
                if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y < 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        Diagonal_Left_Up(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        Diagonal_Left_Up(Math.abs(gamepad1.right_stick_y));
                    }

                }
                else if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y > 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        Diagonal_Left_Down(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        Diagonal_Left_Down(Math.abs(gamepad1.right_stick_y));
                    }

                }
                else if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y < 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        Diagonal_Right_Up(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        Diagonal_Right_Up(Math.abs(gamepad1.right_stick_y));
                    }

                }
                else if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y > 0){
                    if(Math.abs(gamepad1.right_stick_x) >= Math.abs(gamepad1.right_stick_y)){
                        Diagonal_Right_Down(Math.abs(gamepad1.right_stick_x));
                    }
                    else{
                        Diagonal_Right_Down(Math.abs(gamepad1.right_stick_y));
                    }

                }
                else{
                    if(gamepad1.right_stick_x == 0 && gamepad1.right_stick_y == 0){
                        Stop();
                    }
                }

                //Slide Code is Here
                if(gamepad1.right_trigger <= 1 && gamepad1.right_trigger >0){
                    Slide_Right(gamepad1.right_trigger);
                }
                else if(gamepad1.left_trigger <= 1 && gamepad1.left_trigger >0){
                    Slide_Left(gamepad1.left_trigger);
                }
                else{
                    Stop();
                }


                //servo puller - left and right

                if(gamepad1.a && gamepad1.x){
                    ServoMotion(Right_Puller,-1);
                    ServoMotion(Left_Puller,-1);
                }
                else if(gamepad1.b && gamepad1.y){
                    ServoMotion(Right_Puller, 1);
                    ServoMotion(Left_Puller,1);
                }
                else if (gamepad1.a){
                    ServoMotion(Right_Puller,-1);
                }
                else if (gamepad1.b){
                    ServoMotion(Right_Puller, 1);
                }
                else if (gamepad1.x){
                    ServoMotion(Left_Puller,-1);
                }
                else if(gamepad1.y){
                    ServoMotion(Left_Puller,1);
                }
                else{
                    ServoMotion(Left_Puller,0);
                    ServoMotion(Right_Puller,0);

                }
                // Arm Code is Here
                if(gamepad2.dpad_up){
                    MoveArmMotors(Elbow1,1);
                }
                else if(gamepad2.dpad_down){
                    MoveArmMotors(Elbow1, -1);
                }
                else if(gamepad2.left_stick_y<0){
                    Float f = new Float(Math.abs(gamepad2.left_stick_y));
                    MoveArmMotors(Elbow2,f.doubleValue());
                }
                else if(gamepad2.left_stick_y>0){
                    Float f = new Float(Math.abs(gamepad2.left_stick_y));
                    MoveArmMotors(Elbow2,f.doubleValue() * -1);
                }
                else if(gamepad2.right_stick_y <0){
                    Float f = new Float(Math.abs(gamepad2.right_stick_y));
                    MoveArmMotors(Elbow3,f.doubleValue());
                }
                else if(gamepad2.right_stick_y > 0){
                    Float f = new Float(Math.abs(gamepad2.right_stick_y));
                    MoveArmMotors(Elbow3,f.doubleValue() * -1);
                }
                else if(gamepad2.y){
                    MoveArmMotors(Elbow4, -0.5);
                }
                else if(gamepad2.a){
                    MoveArmMotors(Elbow4, 0.5);
                }
                else{
                    MoveArmMotors(Elbow1, 0);
                    MoveArmMotors(Elbow2, 0);
                    MoveArmMotors(Elbow3, 0);
                    MoveArmMotors(Elbow4, 0);
                }
                if(gamepad2.left_bumper){
                    ServoMotion(Turn_Servo,0.75);
                }
                else if(gamepad2.right_bumper){
                    ServoMotion(Turn_Servo,-0.75);
                }
                else if(gamepad2.right_trigger > 0 && gamepad2.right_trigger <= 1){
                    Float f = new Float(Math.abs(gamepad2.right_trigger));
                    ServoMotion(Grip_Servo,f.doubleValue() * -1);
                }
                else if(gamepad2.left_trigger > 0 && gamepad2.left_trigger <= 1){
                    Float f = new Float(Math.abs(gamepad2.left_trigger));
                    ServoMotion(Grip_Servo,f.doubleValue());
                }
                else{
                    ServoMotion(Grip_Servo,0);
                    ServoMotion(Turn_Servo,0);
                }

                /*//Resetting arm Enconder
                ResetArmEncoder(Elbow1);
                ResetArmEncoder(Elbow2);
                ResetArmEncoder(Elbow3);
                ResetArmEncoder(Elbow4);
                if (gamepad2.dpad_left){
                    int newTarget = Elbow1.getTargetPosition() + (int)elbowOneTickCount/4;
                    Elbow1.setTargetPosition(newTarget);
                    Elbow1.setPower(-1);
                    Elbow1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while (Elbow1.isBusy()){
                        telemetry.addData("Status", "Running the motor to a 1/8 turn.");
                        telemetry.addData("Current Tick Count " , Integer.toString(newTarget));
                        telemetry.update();
                    }
                    Elbow1.setPower(0);
                }
                else if (gamepad2.dpad_right){
                    int newTarget =   Elbow1.getTargetPosition() - (int)elbowOneTickCount/4;
                    Elbow1.setTargetPosition((newTarget));
                    Elbow1.setPower(1);
                    Elbow1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while (Elbow1.isBusy()){
                        telemetry.addData("Status", "Running the motor to a 1/8 turn.");
                        telemetry.addData(("Current Tick Count"), Integer.toString(newTarget));
                        telemetry.update();
                    }
                    Elbow1.setPower(0);
                }*/

            }

            telemetry.addData("Path", "Complete");
            telemetry.update();
        }
    }


    public void ResetArmEncoder(DcMotor dcMotor){
        dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void BringArmToPickerPosition(){

    }

    public void MoveArmMotors(DcMotor dcMotor, double power ){
        dcMotor.setPower(power);
    }


    public void ServoMotion (CRServo servo, double power){
        servo.setPower(power);
    }


    public void Stop(){
        Left_Front_Stop();
        Left_Rear_Stop();
        Right_Front_Stop();
        Right_Rear_Stop();

    }

    public void Forward(float power){
        Left_Front_Forward(power);
        Left_Rear_Forward(power);
        Right_Front_Forward(power);
        Right_Rear_Forward(power);
    }

    public void Reverse(float power){
        Left_Front_Reverse(power);
        Left_Rear_Reverse(power);
        Right_Front_Reverse(power);
        Right_Rear_Reverse(power);
    }

    public void Right(float power){
        Left_Front_Forward(power);
        Right_Front_Reverse(power);
        Left_Rear_Forward(power);
        Right_Rear_Reverse(power);
    }

    public void Left(float power){
        Left_Front_Reverse(power);
        Right_Front_Forward(power);
        Left_Rear_Reverse(power);
        Right_Rear_Forward(power);
    }

    public void Diagonal_Right_Up(float power){
        Left_Front_Forward(power);
        Right_Front_Stop();
        Left_Rear_Stop();
        Right_Rear_Forward(power);
    }

    public void Diagonal_Left_Down(float power){
        Left_Front_Reverse(power);
        Right_Front_Stop();
        Left_Rear_Stop();
        Right_Rear_Reverse(power);
    }

    public void Diagonal_Right_Down(float power){
        Left_Front_Stop();
        Right_Front_Reverse(power);
        Left_Rear_Reverse(power);
        Right_Rear_Stop();
    }
    public void Diagonal_Left_Up(float power){
        Left_Front_Stop();
        Right_Front_Forward(power);
        Left_Rear_Forward(power);
        Right_Rear_Stop();
    }
    public void Slide_Right(float power){
        Left_Front_Forward(power);
        Right_Front_Reverse(power);
        Left_Rear_Reverse(power);
        Right_Rear_Forward(power);
    }
    public void Slide_Left(float power){
        Left_Front_Reverse(power);
        Right_Front_Forward(power);
        Left_Rear_Forward(power);
        Right_Rear_Reverse(power);
    }

    public void Left_Front_Forward(float power){
        Float f = new Float(power);
        Left_Front_Wheel.setPower(f.doubleValue());
    }

    public void Left_Front_Reverse(float power){
        Float f = new Float(power);
        Left_Front_Wheel.setPower(f.doubleValue() * -1);
    }

    public void Left_Rear_Forward(float power){
        Float f = new Float(power);
        Left_Rear_Wheel.setPower(f.doubleValue());
    }

    public void Left_Rear_Reverse(float power){
        Float f = new Float(power);
        Left_Rear_Wheel.setPower(f.doubleValue() * -1);
    }

    public void Right_Front_Forward(float power){
        Float f = new Float(power);
        Right_Front_Wheel.setPower(f.doubleValue() * -1);
    }

    public void Right_Front_Reverse(float power){
        Float f = new Float(power);
        Right_Front_Wheel.setPower(f.doubleValue());
    }

    public void Right_Rear_Forward(float power){
        Float f = new Float(power);
        Right_Rear_Wheel.setPower(f.doubleValue() * -1);
    }
    public void Right_Rear_Reverse(float power){
        Float f = new Float(power);
        Right_Rear_Wheel.setPower(f.doubleValue());
    }

    public void Left_Front_Stop(){
        Left_Front_Wheel.setPower(0);
    }

    public void Left_Rear_Stop(){
        Left_Rear_Wheel.setPower(0);
    }

    public void Right_Front_Stop(){
        Right_Front_Wheel.setPower(0);
    }

    public void Right_Rear_Stop(){
        Right_Rear_Wheel.setPower(0);
    }

}
