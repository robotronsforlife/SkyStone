package org.firstinspires.ftc.teamcode.Robotron;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    private DcMotor Right_Front_Wheel;
    private DcMotor Left_Front_Wheel;
    private DcMotor Right_Rear_Wheel;
    private DcMotor Left_Rear_Wheel;
    private DcMotor Elbow1;
    private DcMotor Elbow2;
    private DcMotor Elbow3;
    private DcMotor Elbow4;
    private CRServo Right_Puller;
    private CRServo Left_Puller;
    private CRServo Grip_Servo;
    private CRServo Turn_Servo;

    public void Init(HardwareMap hardwareMap)
    {
        Right_Front_Wheel = hardwareMap.dcMotor.get("Right_Front_Wheel");
        Left_Front_Wheel = hardwareMap.dcMotor.get("Left_Front_Wheel");
        Right_Rear_Wheel = hardwareMap.dcMotor.get("Right_Rear_Wheel");
        Left_Rear_Wheel = hardwareMap.dcMotor.get("Left_Rear_Wheel");

        Right_Front_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Rear_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);

        Elbow1=hardwareMap.dcMotor.get("Elbow1");
        Elbow2=hardwareMap.dcMotor.get("Elbow2");
        Elbow3=hardwareMap.dcMotor.get("Elbow3");
        Elbow4=hardwareMap.dcMotor.get("Elbow4");

        Right_Puller = hardwareMap.crservo.get("Right_Puller");
        Left_Puller = hardwareMap.crservo.get("Left_Puller");
        Turn_Servo = hardwareMap.crservo.get("Turn_Servo");
        Grip_Servo = hardwareMap.crservo.get("Grip_Servo");
    }

    public void Stop(){
        Left_Front_Wheel.setPower(0);
        Left_Rear_Wheel.setPower(0);
        Right_Front_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(0);
    }

    public void Forward(float power){
        Left_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power);
        Right_Front_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(power);
    }

    public void Reverse(float power){
        Left_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(power * -1);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void Right(float power){
        Left_Front_Wheel.setPower(power);
        Right_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void Left(float power){
        Left_Front_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power * -1);
        Right_Rear_Wheel.setPower(power);
    }

    public void Diagonal_Right_Up(float power){
        Left_Front_Wheel.setPower(power);
        Right_Front_Wheel.setPower(0);
        Left_Rear_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(power);
    }

    public void Diagonal_Left_Down(float power){
        Left_Front_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(0);
        Left_Rear_Wheel.setPower(0);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void Diagonal_Right_Down(float power){
        Left_Front_Wheel.setPower(0);
        Right_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(0);
    }

    public void Diagonal_Left_Up(float power){
        Left_Front_Wheel.setPower(0);
        Right_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(0);
    }

    public void Slide_Right(float power){
        Left_Front_Wheel.setPower(power);
        Right_Front_Wheel.setPower(power * -1);
        Left_Rear_Wheel.setPower(power * -1);
        Right_Rear_Wheel.setPower(power);
    }

    public void Slide_Left(float power){
        Left_Front_Wheel.setPower(power * -1);
        Right_Front_Wheel.setPower(power);
        Left_Rear_Wheel.setPower(power);
        Right_Rear_Wheel.setPower(power * -1);
    }

    public void LeftPuller(double power)
    {
        Left_Puller.setPower(power);
    }

    public void RightPuller(double power)
    {
        Right_Puller.setPower(power);
    }

    public void Gripper(double power)
    {
        Grip_Servo.setPower(power);
    }

    public void TurnGripper(double power)
    {
        Turn_Servo.setPower(power);
    }

    public void MoveElbow1(double power)
    {
        Elbow1.setPower(power);
    }

    public void MoveElbow2(double power){
        Elbow2.setPower(power);
    }

    public void MoveElbow3(double power){
        Elbow3.setPower(power);
    }

    public void MoveElbow4(double power){
        Elbow4.setPower(power);
    }
}
