package frc.robot.util.math;

interface ICurvature<S> extends State<S> {
    double getCurvature();

    double getDCurvatureDs();
}
