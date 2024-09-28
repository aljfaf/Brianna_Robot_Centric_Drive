package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp //shows class in driver station
public class RobotCentricRun extends LinearOpMode { //Adds LinearOpMode class into RobotCentricRun

    RobotCentricDriveTrain drive;

    @Override //Inheritance, modifying method (overriding method) in Linear Opmode
    public void runOpMode() {
        drive = new RobotCentricDriveTrain(hardwareMap, gamepad1, telemetry);
        waitForStart();
        while (opModeIsActive()) { //makes sure code is running for whole duration
            drive.loop();
        }
    }
}