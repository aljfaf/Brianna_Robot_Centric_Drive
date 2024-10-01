package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp //shows class in driver station
public class FieldCentricRun extends LinearOpMode { //Adds LinearOpMode class into this new class

    FieldCentricDriveTrain drive;

    @Override //Inheritance, modifying method (overriding method) in Linear Opmode
    public void runOpMode() {
        try {
            drive = new FieldCentricDriveTrain(hardwareMap, gamepad1, telemetry);
        }
        catch (Exception message) {
            telemetry.addData("Message:",message.getMessage());
            telemetry.update();
        }
        waitForStart();
        while (opModeIsActive()) { //makes sure code is running for whole duration
            drive.loop();
        }
    }
}