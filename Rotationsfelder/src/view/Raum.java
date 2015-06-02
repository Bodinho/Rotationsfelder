package view;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Raum extends Frame {

	TransformGroup transroot = new TransformGroup(new Transform3D());
	Canvas3D canvas3d;
	SimpleUniverse universe;

	public Raum(String title, ArrayList<Partikel> li) {
		super(title);

		// AnzeigeInfos holen (Farbtiefe, usw...
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		// ein neues Canvas mit den AnzeigeInfos erzeugen und dem Frame
		// hinzufügen
		canvas3d = new Canvas3D(config);
		add(canvas3d);

		paint(li);
	}

	public void paint(ArrayList<Partikel> li) {

		// zu Testzwecken Partikel bei (0|0|0) der Liste hinzufügen
		// Partikel pAdd = new Partikel();
		// pAdd.setX(0);
		// pAdd.setY(0);
		// pAdd.setZ(0);
		// li.add(pAdd);

		// ein neues Universum im Canvas erzeugen und eine Betrachtungsebene
		// erzeugen
		if (universe != null) {
			universe.cleanup();
		}
		universe = new SimpleUniverse(canvas3d);
		universe.getViewingPlatform().setNominalViewingTransform();
		// neue Transform Informationen
		Transform3D transform3d = new Transform3D();

		// Ausgangsblickwinkel
		// transform3d.setTranslation(new Vector3d(0.0, 0.0, -10.0));
		transform3d.lookAt(new Point3d(0, 0, -50.0), new Point3d(0, 0, 0),
				new Vector3d(0, 1.0, 0));

		// neue Transformgruppe
		transroot = new TransformGroup(transform3d);
		transroot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

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

		addMouse();

		// neue Branchgruppe
		BranchGroup branchgroup = new BranchGroup();

		RotationInterpolator ri;
		// Partikel hinzufügen
		for (Partikel p : li) {
			transroot.addChild(addSphere(p));
			// Rotation
			// ri = new RotationInterpolator(new Alpha(-1, 4000), addSphere(p));
			// ri.setSchedulingBounds(new BoundingSphere(
			// new Point3d(0.0, 0.0, 0.0), Float.MAX_VALUE));
			// transroot.addChild(ri);
		}

		// Transformgruppe an Branchgruppe hängen
		branchgroup.addChild(transroot);

		// ... an das Universum hängen
		universe.addBranchGraph(branchgroup);
	}

	/**
	 * x-, y- und z- Achse anzeigen derzeit sind die Achsen noch statisch und
	 * bewegen sich nicht bei der Mausaction
	 * 
	 * @return BranchGroup
	 */
	public BranchGroup createSceneGraph() {
		BranchGroup objRoot = new BranchGroup();

		// Create X axis
		LineArray axisXLines = new LineArray(2, GeometryArray.COORDINATES);
		objRoot.addChild(new Shape3D(axisXLines));

		axisXLines.setCoordinate(0, new Point3f(-1.0f, 0.0f, 0.0f));
		axisXLines.setCoordinate(1, new Point3f(1.0f, 0.0f, 0.0f));

		// Create Y axis
		LineArray axisYLines = new LineArray(2, GeometryArray.COORDINATES
				| GeometryArray.COLOR_3);
		objRoot.addChild(new Shape3D(axisYLines));

		axisYLines.setCoordinate(0, new Point3f(0.0f, -1.0f, 0.0f));
		axisYLines.setCoordinate(1, new Point3f(0.0f, 1.0f, 0.0f));

		// Create Z axis with arrow
		Point3f z1 = new Point3f(0.0f, 0.0f, -1.0f);
		Point3f z2 = new Point3f(0.0f, 0.0f, 1.0f);

		LineArray axisZLines = new LineArray(10, GeometryArray.COORDINATES
				| GeometryArray.COLOR_3);
		objRoot.addChild(new Shape3D(axisZLines));

		axisZLines.setCoordinate(0, z1);
		axisZLines.setCoordinate(1, z2);
		axisZLines.setCoordinate(2, z2);
		axisZLines.setCoordinate(3, new Point3f(0.1f, 0.1f, 0.9f));
		axisZLines.setCoordinate(4, z2);
		axisZLines.setCoordinate(5, new Point3f(-0.1f, 0.1f, 0.9f));
		axisZLines.setCoordinate(6, z2);
		axisZLines.setCoordinate(7, new Point3f(0.1f, -0.1f, 0.9f));
		axisZLines.setCoordinate(8, z2);
		axisZLines.setCoordinate(9, new Point3f(-0.1f, -0.1f, 0.9f));

		return objRoot;
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
			TransformGroup t;
			try {
				t = (TransformGroup) obj;
			} catch (Exception e) {
				continue;
			}
			p = li.get(counter);
			Transform3D t3d = new Transform3D();
			t3d.setTranslation(new Vector3d(p.x, p.y, p.z));
			counter++;
		}
	}

	private void addMouse() {
		// Bounds inherhalb derer das Behaviour gelten soll
		BoundingBox boundBox = new BoundingBox(new Point3d(-10, -10, -10),
				new Point3d(10, 10, 10));

		// Muss gesetzt werden um das Betrachten mit der Maus zu erlauben
		transroot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transroot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// rotieren mit der linken Maustaste
		MouseRotate behaviour = new MouseRotate(transroot);
		behaviour.setSchedulingBounds(boundBox);
		transroot.addChild(behaviour);

		// zoomen mit der mitleren Maustaste
		MouseWheelZoom mouseBeh2 = new MouseWheelZoom(transroot);
		mouseBeh2.setSchedulingBounds(boundBox);
		transroot.addChild(mouseBeh2);

		// verschieben mit der rechten Maustaste
		MouseTranslate mouseBeh3 = new MouseTranslate(transroot);
		mouseBeh3.setSchedulingBounds(boundBox);
		transroot.addChild(mouseBeh3);
	}
}
