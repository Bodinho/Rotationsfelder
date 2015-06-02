package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Beispiel extends Frame {

	TransformGroup transroot = new TransformGroup(new Transform3D());

	public Beispiel(String title, ArrayList<Partikel> li) {
		super(title);

		// AnzeigeInfos holen (Farbtiefe, usw...
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		// ein neues Canvas mit den AnzeigeInfos erzeugen und dem Frame
		// hinzufügen
		Canvas3D canvas3d = new Canvas3D(config);
		add(BorderLayout.CENTER, canvas3d);
		// canvas3d.addMouseWheelListener(new MouseWheelZoom());

		// zu Testzwecken Partikel bei (0|0|0) der Liste hinzufügen
		Partikel pAdd = new Partikel();
		pAdd.setX(0);
		pAdd.setY(0);
		pAdd.setZ(0);
		li.add(pAdd);

		// ein neues Universum im Canvas erzeugen und eine Betrachtungsebene
		// erzeugen
		SimpleUniverse universe = new SimpleUniverse(canvas3d);
		universe.getViewingPlatform().setNominalViewingTransform();

		// neue Transform Informationen
		Transform3D transform3d = new Transform3D();

		// Ausgangsblickwinkel
		// transform3d.setTranslation(new Vector3d(0.0, 0.0, -10.0));
		transform3d.lookAt(new Point3d(0, 0, -10.0), new Point3d(0, 0, 0),
				new Vector3d(0, 1.0, 0));

		// neue Transformgruppe
		transroot = new TransformGroup(transform3d);
		// transroot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// // ein kleiner Würfel
		// ColorCube colorcube = new ColorCube(0.33);
		// // neue Transform Informationen
		// transform3d = new Transform3D();
		// // Rotation um Y- Achse
		// transform3d.rotY(Math.toRadians(30));
		// // Verschiebung um Vector
		// transform3d.setTranslation(new Vector3f(1, 1, 1));
		// // neue Transformgruppe
		// TransformGroup transcube1 = new TransformGroup(transform3d);
		// // colorcube an Transformgruppe hängen
		// transcube1.addChild(colorcube);
		//
		// // colorcube an Transformgruppe hängen
		// transroot.addChild(transcube1);
		//
		// // ein kleiner Würfel
		// ColorCube colorcube2 = new ColorCube(0.33);
		// // neue Transform Informationen
		// transform3d = new Transform3D();
		// // Rotation um Y- Achse
		// transform3d.rotY(Math.toRadians(30));
		// // Verschiebung um Vector
		// transform3d.setTranslation(new Vector3f(0, 0, 0));
		// // neue Transformgruppe
		// TransformGroup transcube2 = new TransformGroup(transform3d);
		// // colorcube an Transformgruppe hängen
		// transcube2.addChild(colorcube2);
		//
		// // colorcube an Transformgruppe hängen
		// transroot.addChild(transcube2);

		// Boundingbereich
		BoundingSphere boundsph = new BoundingSphere(
				new Point3d(0.0, 0.0, 0.0), Float.MAX_VALUE);

		// Manipulation des Sichtfeldes mit der Maus
		addMouse(boundsph);

		// neue Branchgruppe
		BranchGroup branchgroup = new BranchGroup();

		// Rotation hinzufügen
		// RotationInterpolator ri = new RotationInterpolator(new Alpha(-1,
		// 4000),
		// transroot);
		// ri.setSchedulingBounds(boundsph);
		// Transform3D ri3d = new Transform3D();
		// ri3d.lookAt(new Point3d(0, 0, -10.0), new Point3d(0, 0, 0),
		// new Vector3d(0, 1.0, 0));
		// ri3d.setTranslation(new Vector3d(0, 0, 0));
		// ri.setTransformAxis(ri3d);
		// transroot.addChild(ri);

		// PositionInterpolator pi = new PositionInterpolator(new Alpha(-1,
		// 4000),
		// transroot);

		// Partikel hinzufügen
		for (Partikel p : li) {
			transroot.addChild(addSphere(p));
		}

		// Transformgruppe an Branchgruppe hängen
		branchgroup.addChild(transroot);

		// ... an das Universum hängen
		universe.addBranchGraph(branchgroup);
	}

	public void paint(ArrayList<Partikel> li) {
		if (transroot != null)
			changeLocation(li);
	}

	private TransformGroup addSphere(Partikel p) {
		// neuen Partikel
		Sphere s = new Sphere(0.1f, new Appearance());
		// neue Transform Informationen
		Transform3D transform3d = new Transform3D();
		// Verschiebung um Vector
		transform3d.setTranslation(new Vector3d(p.x, p.y, p.z));
		// neue Transformgruppe
		TransformGroup transcube1 = new TransformGroup(transform3d);
		// colorcube an Transformgruppe hängen
		transcube1.addChild(s);

		// colorcube an Transformgruppe hängen
		return transcube1;
	}

	private void changeLocation(ArrayList<Partikel> li) {
		Enumeration en = transroot.getAllChildren();
		int counter = 0;
		Partikel p;
		while (en.hasMoreElements()) {
			Object obj = en.nextElement();
			if (!(obj instanceof BranchGroup))
				continue;
			BranchGroup t = (BranchGroup) obj;
			int pos = transroot.indexOfChild(t);
			p = li.get(counter);
			Transform3D t3d = new Transform3D();
			t3d.setTranslation(new Vector3d(p.x, p.y, p.z));
			counter++;
			transroot.setChild(t, pos);
		}
	}

	private void addMouse(BoundingSphere boundsph) {

		// Muss gesetzt werden um das Betrachten mit der Maus zu erlauben
		transroot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transroot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// rotieren mit der linken Maustaste
		MouseRotate behaviour = new MouseRotate(transroot);
		behaviour.setSchedulingBounds(boundsph);
		transroot.addChild(behaviour);

		// zoomen mit der mitleren Maustaste
		MouseWheelZoom mouseBeh2 = new MouseWheelZoom(transroot);
		mouseBeh2.setSchedulingBounds(boundsph);
		transroot.addChild(mouseBeh2);

		// verschieben mit der rechten Maustaste
		MouseTranslate mouseBeh3 = new MouseTranslate(transroot);
		mouseBeh3.setSchedulingBounds(boundsph);
		transroot.addChild(mouseBeh3);
	}
}
