package frc.robot.util.math;

import java.util.List;

public abstract class GZGeometry<T> {

    public static <T> void printArray(List<T> list) {
        StringBuilder out = new StringBuilder();
        for (T val : list) {
            out.append(val);
        }
        System.out.println(out);
    }

    public T print() {
        return print("");
    }

    public T print(String message) {
        String out = "";

        if (!message.equals("")) {
            out += "[" + message + "]\t";
        }

        out += toString();
        System.out.println(out);
        return (T) this;
    }

}