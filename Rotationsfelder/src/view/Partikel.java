package view;

public class Partikel {

	double x;
	double y;
	double z;

	public Partikel() {

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void ausgabe() {

		System.out.println("Partikel: " + this.toString() + "\n" + "( " + x
				+ " | " + y + " | " + z + " )");
	}

	public void addRotationsKraft(double[] rotationsKraft) {

		this.x = (rotationsKraft[0]);
		this.y = (rotationsKraft[1]);
		this.z = (rotationsKraft[2]);
	}
}
