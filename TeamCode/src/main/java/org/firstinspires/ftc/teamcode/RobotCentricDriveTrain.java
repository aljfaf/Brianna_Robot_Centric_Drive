package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotCentricDriveTrain {

    private final DcMotor frontRightMotor;
    private final DcMotor frontLeftMotor;
    private final DcMotor backRightMotor;
    private final DcMotor backLeftMotor;


    Gamepad gamepad; //objects
    Telemetry telemetry;

    //constructor of objects
    public RobotCentricDriveTrain(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        this.gamepad = gamepad; //retrieves from class
        this.telemetry = telemetry;
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR"); //assigns names to the motors from the class
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        backRightMotor = hardwareMap.get(DcMotor.class, "BR");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); //because they are reversed on robot
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop() {
        double verticalMovement = -gamepad.left_stick_y; //motors originally spin counterclockwise thus negative
        double strafe = gamepad.left_stick_x * 1.1; //make sure it is set to 1 in case of error
        double turning = gamepad.right_stick_x;
        double maintainRatio = Math.max(Math.abs(verticalMovement) + Math.abs(strafe) + Math.abs(turning), 1); //to maintain correct ratio if it exceeds 1
        frontRightMotor.setPower((verticalMovement - strafe - turning) / (maintainRatio)); //relative to wheels
        backRightMotor.setPower((verticalMovement + strafe - turning) / (maintainRatio));
        frontLeftMotor.setPower((verticalMovement + strafe + turning) / (maintainRatio));
        backLeftMotor.setPower((verticalMovement - strafe + turning) / (maintainRatio));

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //if there is no movement on gamepad, stops the motors
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("frontRightMotor: ", frontRightMotor.getPower()); //prints in drivehub specific powers for funsies
        telemetry.addData("frontLeftMotor: ", frontLeftMotor.getPower());
        telemetry.addData("backRightMotor: ", backRightMotor.getPower());
        telemetry.addData("backLeftMotor: ", backLeftMotor.getPower());
        telemetry.update();

    }

 }
