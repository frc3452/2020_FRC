package frc.robot.temporary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GZUtil {

	public static final double kEpsilon = 1e-12;

	private GZUtil() {
	}


	public static String[] letters = { "", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI",
			"AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA",
			"BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS",
			"BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK",
			"CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB", "DC",
			"DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", };

	public static Double getRandDouble(double min, double max) {
		double x = min + (Math.random() * (max - min));
		return x;
	}

	public static Double round(double value) {
		return round(value, 2);
	}

	public static Double round(double value, int place) {
		try {
			double ret = (double) Math.round(value * Math.pow(10, place)) / Math.pow(10, place);
			return ret;
		} catch (Exception e) {
			return Double.NaN; // just incase some dumb divided by 0 on accident kinda thing
		}
	}

	public static Integer getRandInt(int min, int max) {
		int x = min + (int) (Math.random() * ((max - min + 1)));
		return x;
	}

	public static boolean getRandBoolean() {
		return getRandInt(0, 1) == 0;
	}

	public static void bigPrint(String f, int num) {
		for (int i = 0; i < num; i++) {
			System.out.println("ERROR " + f);
		}
	}

	public static void bigPrint(String f) {
		bigPrint(f, 40);
	}

	// public static double autoScale(double inputVal, double outputRange1, double
	// outputRange2, double inputRange1,
	// double inputRange2) {
	// return scaleBetween(inputVal, Math.min(outputRange1, outputRange2),
	// Math.max(outputRange1, outputRange2),
	// Math.min(inputRange1, inputRange2), Math.max(inputRange1, inputRange2));
	// }

	public static double scaleBetween(double unscaledNum, double minAllowed, double maxAllowed, double min,
			double max) {
		return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
	}

	public static double interpolate(double a, double b, double x) {
		x = limit(x, 0.0, 1.0);
		return a + (b - a) * x;
	}

	public static StackTraceElement[] currentThread() {
		return Thread.currentThread().getStackTrace();
	}

	/**
	 * currentThread();
	 */
	public static void trace(StackTraceElement e[]) {

		String retval = "";
		try {
			// for (int i = e.length - 5; i > 1; i--) {
			for (int i = e.length - 1; i > 1; i--) {
				retval += e[i].getMethodName();

				if (i != 2)
					retval += ".";
			}
		} catch (Exception ex) {
			System.out.println(
					"Max was a dummy that tried to write something to make his life easier but he made it much much harder");
			// ex.printStackTrace();
		}

		System.out.println(retval);
	}

	public static double limit1to1(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		if (value < -1.0) {
			return -1.0;
		}
		return value;
	}

	/**
	 * Limits the given input to the given magnitude.
	 */
	public static double limit(double v, double maxMagnitude) {
		return limit(v, -maxMagnitude, maxMagnitude);
	}

	public static double limit(double value, double low, double high) {
		if (value > high)
			value = high;
		else if (value < low)
			value = low;

		return value;
	}

	public static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Double nativeTalonUnitsToRPM(double nativeUnits) {
		return nativeUnits * (1.0 / 4096.0) * (600.0);
	}

	public static boolean between(double value, double low, double high) {
		if (value >= low && value <= high)
			return true;

		return false;
	}

	public static boolean epsilonEquals(double value, double point, double areaAroundPoint) {
		return (value - areaAroundPoint <= point) && (value + areaAroundPoint >= point);
	}

	public static boolean epsilonEquals(double a, double b) {
		return epsilonEquals(a, b, kEpsilon);
	}

	public static boolean allCloseTo(final ArrayList<Double> list, double point, double areaAroundPoint) {
		boolean result = true;
		for (Double value_in : list) {
			result &= epsilonEquals(value_in, point, areaAroundPoint);
		}
		return result;
	}

	public static double hardDeadband(double value, double deadband) {
		return (Math.abs(value) > Math.abs(deadband)) ? value : 0.0;
	}

	public static double applyDeadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}

	/**
	 * toRound = 2.342, wanting to round to nearest .05 1/<b>20</b> is .05
	 * roundToFraction(2.342,20)
	 *
	 * @author max
	 * @param value
	 * @param denominator double
	 * @return double
	 */
}