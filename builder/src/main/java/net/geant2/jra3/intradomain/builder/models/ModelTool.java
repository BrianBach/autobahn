package net.geant2.jra3.intradomain.builder.models;

public class ModelTool {

	/**
	 * Helps in parsing String  value to int value
	 * @param previousValue i
	 */
	public static int parseInt(int previousValue, String value) {
		int val = 0;
		try {
			val = Integer.parseInt(value);
			return val;
		} catch (NumberFormatException e) {
			return previousValue;
		}
	}

	public static long parseLong(long previousValue, String value) {
		long val = 0;
		try {
			val = Long.parseLong(value);
			return val;
		} catch (NumberFormatException e) {
			return previousValue;
		}
	}

	public static double parseDouble(double previousValue, String value) {
		double val = 0;
		try {
			val = Double.parseDouble(value);
			return val;
		} catch (NumberFormatException e) {
			return previousValue;
		}
	}

	public static boolean parseBoolean(boolean previousValue, String value) {
		if (value == null)
			return previousValue;
		if (value.toLowerCase().equals("true"))
			return true;
		if (value.toLowerCase().equals("false"))
			return false;
		return previousValue;
	}
}
