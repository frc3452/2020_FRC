package frc.robot.util;

public class DCMotor {

    private double Kt;
    private double Kv;

    private double kResistanceOhms;

    public DCMotor(final double kFreeSpeedRPM, final double kFreeCurrent, final double kStallTorque_nm, final double kStallCurrent) {
        final double kFreeSpeed_Rad_per_Sec = kFreeSpeedRPM / 60.0 * Math.PI * 2.0;

        kResistanceOhms = 12.0 / kStallCurrent;

        Kt = kStallTorque_nm / kStallCurrent;
        Kv = kFreeSpeed_Rad_per_Sec / (12.0 - kResistanceOhms * kFreeCurrent);
    }

    public static DCMotor scaleKt(final DCMotor motor, int numberOfMotors) {
        motor.Kt *= numberOfMotors;
        return motor;
    }

    public static DCMotor makeGearbox_254(final DCMotor motor, int numberOfMotors, double gearReduction, double efficiency) {
        motor.Kt *= (numberOfMotors * gearReduction * efficiency);
        motor.Kv /= gearReduction;
        motor.kResistanceOhms /= numberOfMotors;
        return motor;
    }

    public static DCMotor makeCIM() {
        return new DCMotor(5330, 2.7, 2.41, 131);
    }

    public static DCMotor makeMiniCIM() {
        return new DCMotor(5840, 3, 1.41, 89);
    }

    public static DCMotor makeBagMotor() {
        return new DCMotor(13180, 1.8, 0.43, 53);
    }

    public static DCMotor make775Pro() {
        return new DCMotor(18730, 0.7, 0.71, 134);
    }

    public static DCMotor makeBaneBotsRs_550() {
        return new DCMotor(19000, 0.4, 0.38, 84);
    }

    public final double getKt() {
        return Kt;
    }

    public final double getKv() {
        return Kv;
    }

    public final double getResistance() {
        return kResistanceOhms;
    }

    public String getConstants() {
        return "var kt = " + getKt() + ";\nvar kv = " + getKv() + ";\nvar R = " + getResistance() + ";";
    }

    public void printConstants() {
        System.out.println(getConstants());
    }

    @Override
    public String toString() {
        return "Kt: [" + getKt() + "]\tKv: [" + getKv() + "]\tOhms:[" + getResistance() + "]";
    }
}
