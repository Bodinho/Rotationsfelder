package controller;

import view.Partikel;

public class MathFunctions {

	private static final double COSALPHA = Math
			.cos(1 * RotationCalculator.alpha);
	private static final double SINALPHA = Math
			.sin(1 * RotationCalculator.alpha);

	private static double funktion1(double x, double y, double z) {
		// return x+SINALPHA;
		return COSALPHA * x + (-1 * SINALPHA) * y;

	}

	private static double funktion2(double x, double y, double z) {
		// return y+COSALPHA;
		return SINALPHA * x + COSALPHA * y;
	}

	private static double funktion3(double x, double y, double z) {
		// return z+SINALPHA + COSALPHA;
		return z;
	}

	public static double[] rotation(Partikel p) {

		double x = p.getX();
		double y = p.getY();
		double z = p.getZ();

		double[] rotationsKraft = new double[3];

		rotationsKraft[0] = (funktion1(x, y, z));
		rotationsKraft[1] = (funktion2(x, y, z));
		rotationsKraft[2] = (funktion3(x, y, z));

		return rotationsKraft;
	}
}
