package controller;

import java.util.ArrayList;

import view.Partikel;

public class RotationCalculator {

	public ArrayList<Partikel> partikelListe = new ArrayList<Partikel>();
	public static int alpha = 1;
	public static int varT = 3;

	public RotationCalculator(int partikelAnzahl) {
		for (int i = 0; i < partikelAnzahl; ++i) {

			Partikel p = new Partikel();
			p.setX(Math.random() * 4 - 2);
			p.setY(Math.random() * 4 - 2);
			p.setZ(Math.random() * 4 - 2);

			partikelListe.add(p);
		}
	}

	public ArrayList<Partikel> run() {

		alpha += Math.PI / 5;
		ArrayList<Partikel> neuePartikelListe = new ArrayList<Partikel>();

		for (int i = 0; i < partikelListe.size(); ++i) {

			Partikel p = partikelListe.get(i);
			p.addRotationsKraft(MathFunctions.rotation(p));
			p.ausgabe();
			neuePartikelListe.add(p);
		}

		System.out.println("ALPHA=:" + alpha);
		return neuePartikelListe;
	}
}
