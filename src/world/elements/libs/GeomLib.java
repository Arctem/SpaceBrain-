package world.elements.libs;

import java.awt.Polygon;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class GeomLib {

	private static Polygon SHIP_1;
	private static Polygon DEBUG;

	public static void init() throws IOException, ClassNotFoundException {
		// System.out.println("Loading physical geometry...");
		DEBUG = makeDebug();
		// TODO: Since we're going to be importing many hitboxes later, this
		// should be more streamlined.
		InputStream buffer = new BufferedInputStream(new FileInputStream(
				"data/ship.brn"));
		ObjectInput in = new ObjectInputStream(buffer);
		SHIP_1 = (Polygon) in.readObject();
	}

	private static Polygon makeDebug() {
		Polygon poly = new Polygon();
		poly.addPoint(10, 0);
		poly.addPoint(20, 20);
		poly.addPoint(0, 20);
		return poly;
	}

	public static Polygon fetchHitBox(ImageType type){
		Polygon hitBox = null;
		switch(type){
		case SHIP_1: hitBox = new Polygon(SHIP_1.xpoints,SHIP_1.ypoints,SHIP_1.npoints); break;
		case BULLET_1: hitBox = null; break; //HIT BOX NOT YET LOADED. NEED TO TALK TO ROB ABOUT THIS!
		}
		return hitBox;
	}
}
