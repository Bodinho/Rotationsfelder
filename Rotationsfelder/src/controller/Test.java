package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.Beispiel;
import view.Raum;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Partikel p1 = new Partikel(0, 0, 0);
		// ArrayList<Partikel> li = new ArrayList<Partikel>();
		RotationCalculator rotCal = new RotationCalculator(10);
		rotCal.partikelListe = rotCal.run();
		 Raum beispiel = new Raum("Beispiel", rotCal.partikelListe);
//		Beispiel beispiel = new Beispiel("Test", rotCal.partikelListe);
		beispiel.setSize(600, 400);
		beispiel.setVisible(true);
		beispiel.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < 100; ++i) {
			rotCal.partikelListe = rotCal.run();
			beispiel.paint(rotCal.partikelListe);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// for (int i = 0; i < 4; i++) {
		// try {
		// Thread.sleep(500);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// li.remove(p1);
		// p1.x = p1.x - 0.1;
		// p1.y = p1.y - 0.1;
		// p1.z = p1.z - 0.1;
		// li.add(p1);
		// }
	}

}
