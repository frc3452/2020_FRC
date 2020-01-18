package frc.robot.temporary;

import frc.robot.temporary.CSVWritable;
import frc.robot.temporary.Interpolable;

public interface State<S> extends Interpolable<S>, CSVWritable {
    double distance(final S other);

    boolean equals(final Object other);

    String toString();

    String toCSV();
}