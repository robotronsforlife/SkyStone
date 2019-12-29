package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robotron.Robot;

@Autonomous(name = "Reset Elbow4 after Autonomous", group = "Jan 12 Competition")
@Disabled
public class ResetElbow4 extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();
        robot.Init(hardwareMap, telemetry, true);

        waitForStart();

        if (opModeIsActive()) {
            robot.TurnElbow4ByDegrees(-267, false);
        }
    }
}
