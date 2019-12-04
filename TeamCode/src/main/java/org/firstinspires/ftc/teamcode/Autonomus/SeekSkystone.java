package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodSkyStone;

@Autonomous(name = "SeekSkystoneA ", group = "")
public class SeekSkystone extends LinearOpMode {

    private DcMotor Left_Front_Motor;
    private DcMotor Left_Rear_Motor;
    private VuforiaSkyStone vuforiaSkyStone;
    private TfodSkyStone tfodSkyStone;
    private DcMotor Right_Rear_Motor;
    private DcMotor Right_Front_Motor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        List<Recognition> recognitions;
        double TargetHeightRatio;
        boolean SkystoneFound;
        double Skystone_Count;
        double ObjectAngle;
        double LeftPower;
        double RightPower;
        float ObjectHeight;
        int ImageHeight;
        double ObjectHeightRatio;

        Left_Rear_Motor = hardwareMap.dcMotor.get("LeftBackMotorAsDcMotor");
        Left_Front_Motor = hardwareMap.dcMotor.get("LeftFrontMotorAsDcMotor");
        vuforiaSkyStone = new VuforiaSkyStone();
        tfodSkyStone = new TfodSkyStone();
        Right_Rear_Motor = hardwareMap.dcMotor.get("RightBackMotorAsDcMotor");
        Right_Front_Motor = hardwareMap.dcMotor.get("RightFrontMotorAsDcMotor");

        Right_Rear_Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front_Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Sample TFOD Op Mode
        // Initialize Vuforia.
        vuforiaSkyStone.initialize(
                "", // vuforiaLicenseKey
                hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
                "Webcam 1", // webcamCalibrationFilename
                true, // useExtendedTracking
                true, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                0, // xAngle
                0, // yAngle
                0, // zAngle
                true); // useCompetitionFieldTargetLocations
        // Set min confidence threshold to 0.7
        tfodSkyStone.initialize(vuforiaSkyStone, 0.7F, false, true);
        // Initialize TFOD before waitForStart.
        // Init TFOD here so the object detection labels are visible
        // in the Camera Stream preview window on the Driver Station.
        tfodSkyStone.activate();
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        // Wait for start command from Driver Station.
        TargetHeightRatio = 0.8;
        waitForStart();
        tfodSkyStone.activate();
        SkystoneFound = false;
        while (opModeIsActive() && !SkystoneFound) {
            recognitions = tfodSkyStone.getRecognitions();
            telemetry.addData("Objects Recognized", recognitions.size());
            if (recognitions.size() > 0) {
                Skystone_Count = 0;
                for (Recognition recognition : recognitions) {
                    if (recognition.getLabel().equals("Skystone")) {
                        Skystone_Count = Skystone_Count + 1;
                        ObjectAngle = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                        telemetry.addData("Estimated Angle", ObjectAngle);
                        if (ObjectAngle > 0) {
                            telemetry.addData("Direction", "Right");
                        } else {
                            telemetry.addData("Direction", "Left");
                        }
                        LeftPower = 0.25 * (ObjectAngle / 45);
                        RightPower = -0.25 * (ObjectAngle / 45);
                        ImageHeight = recognition.getImageHeight();
                        ObjectHeight = recognition.getHeight();
                        ObjectHeightRatio = ObjectHeight / ImageHeight;
                        telemetry.addData("Height Ratio", ObjectHeightRatio);
                        if (ObjectHeightRatio < TargetHeightRatio - 0.05) {
                            telemetry.addData("Distance", "Not Close Enough");
                            if (Math.abs(LeftPower) + Math.abs(RightPower) < 0.2) {
                                telemetry.addData("Action", "Forward");
                                LeftPower = (0.035 + 0.5 * ((TargetHeightRatio - 0.05) - ObjectHeightRatio)) * -1;
                                RightPower = LeftPower;
                            } else {
                                telemetry.addData("Action", "Turn");
                            }
                        } else if (ObjectHeightRatio > TargetHeightRatio + 0.05) {
                            telemetry.addData("Distance ", "Too Close");
                            if (Math.abs(LeftPower) + Math.abs(RightPower) < 0.12) {
                                telemetry.addData("Action", "Back up");
                                LeftPower = -0.05 + -0.5 * ((TargetHeightRatio + 0.05) - ObjectHeightRatio);
                                RightPower = LeftPower;
                            } else {
                                telemetry.addData("Action", "Turn");
                            }
                        } else {
                            telemetry.addData("Distance", "Correct");
                            if (Math.abs(LeftPower) + Math.abs(RightPower) < 0.12) {
                                telemetry.addData("Action", "Motors off, hit Skystone");
                                LeftPower = 0;
                                RightPower = LeftPower;
                                SkystoneFound = true;
                            } else {
                                telemetry.addData("Action", "Turn");
                            }
                        }
                        telemetry.addData("Left Power", LeftPower);
                        telemetry.addData("Right Power", RightPower);
                        Left_Rear_Motor.setPower(LeftPower);
                        Left_Front_Motor.setPower(LeftPower);
                        Right_Rear_Motor.setPower(RightPower);
                        Right_Front_Motor.setPower(RightPower);
                        break;
                    }
                }
                 if (Skystone_Count == 0) {
                    telemetry.addData("Status", "No Skystone");
                    telemetry.addData("Action", "Slide right ");
                    RightFrontMotorBackward();
                    LeftFrontMotorBackward();
                    RightBackMotorBackward();
                    LeftBackMotorBackward();

                }
            } else {
                telemetry.addData("Status", "No Object Detected");
                telemetry.addData("Action", "Backup");
                LeftBackMotorForward();
                LeftFrontMotorForward();
                RightFrontMotorStop();
                RightBackMotorForward();
            }
            telemetry.update();
        }
        // Deactivate TFOD.
        tfodSkyStone.deactivate();
        LeftBackMotorStop();
        LeftFrontMotorStop();
        RightBackMotorStop();
        RightFrontMotorStop();
        sleep(2000);

        vuforiaSkyStone.close();
        tfodSkyStone.close();
    }
    private void LeftFrontMotorStop() {
        Left_Front_Motor.setPower(0);
    }

    private void LeftBackMotorStop() {

        Right_Front_Motor.setPower(0);
    }

    private void RightFrontMotorStop() {
        Right_Front_Motor.setPower(0);
    }

    private void RightBackMotorStop() {
        Right_Rear_Motor.setPower(0);
    }
    private void LeftFrontMotorForward() {
        Left_Front_Motor.setPower(0.1);
    }

    private void LeftBackMotorForward() {

        Right_Front_Motor.setPower(0.1);
    }

    private void RightFrontMotorForward() {
        Right_Front_Motor.setPower(0.1);
    }

    private void RightBackMotorForward() {
        Right_Rear_Motor.setPower(0.1);
    }

    private void LeftFrontMotorBackward() {
        Left_Front_Motor.setPower(-0.1);
    }

    private void LeftBackMotorBackward() {

        Right_Front_Motor.setPower(-0.1);
    }

    private void RightFrontMotorBackward() {
        Right_Front_Motor.setPower(-0.1);
    }

    private void RightBackMotorBackward() {
        Right_Rear_Motor.setPower(-0.1);
    }




}