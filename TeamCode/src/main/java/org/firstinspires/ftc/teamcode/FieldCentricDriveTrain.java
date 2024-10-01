package org.firstinspires.ftc.teamcode; //folder

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FieldCentricDriveTrain {
    private final BNO055IMU imu;

    private final DcMotor frontRightMotor;
    private final DcMotor frontLeftMotor;
    private final DcMotor backRightMotor;
    private final DcMotor backLeftMotor;
    private final double SPEED_MULTIPLIER = 0.8;


            Gamepad gamepad; //objects
    Telemetry telemetry;


    //constructor of objects
    public FieldCentricDriveTrain(HardwareMap hardwareMap, Gamepad gamepad, Telemetry telemetry) {
        this.gamepad = gamepad; //retrieves from class
        this.telemetry = telemetry;
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR"); //assigns names to the motors from the class
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        backRightMotor = hardwareMap.get(DcMotor.class, "BR");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); //because they are reversed on robot
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); // setdirection+method

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

     /*   if (gamepad.right_bumper) {
            imu.resetYaw();
    }*/

}

    public void loop() {
        double verticalMovement = -gamepad.left_stick_y; //motors originally spin counterclockwise thus negative
        double strafe = gamepad.left_stick_x * 1.1; //make sure it is set to 1 in case of error
        double turning = gamepad.right_stick_x;
        double heading = imu.getAngularOrientation().firstAngle; //"front" of robot
        double rotatedStrafe = (strafe * Math.cos(-heading)) - (verticalMovement * Math.sin(-heading)); //x2 = x cos(theta) - y(sin(theta)
        double rotatedVerticalMovement = (strafe * Math.sin(-heading)) + (verticalMovement * Math.cos(-heading)); //y2 = x cos(theta) + y sin(theta)


        double maintainRatio = Math.max(Math.abs(rotatedVerticalMovement) + Math.abs(rotatedStrafe) + Math.abs(turning), 1); //to maintain correct ratio if it exceeds 1

        frontRightMotor.setPower(((rotatedVerticalMovement - rotatedStrafe - turning) / (maintainRatio))*SPEED_MULTIPLIER); //relative to wheels
        backRightMotor.setPower(((rotatedVerticalMovement + rotatedStrafe - turning) / (maintainRatio))*SPEED_MULTIPLIER);
        frontLeftMotor.setPower(((rotatedVerticalMovement + rotatedStrafe + turning) / (maintainRatio))*SPEED_MULTIPLIER);
        backLeftMotor.setPower(((rotatedVerticalMovement - rotatedStrafe + turning) / (maintainRatio))*SPEED_MULTIPLIER);


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
