package rpgapp.data.elementInteractifs;

import java.util.HashMap;

import javafx.geometry.Point2D;
public abstract class PNJList {
	
	public static HashMap<Point2D, PNJ> pnjList;

	public static void init() {
		pnjList = new HashMap<Point2D, PNJ>();
	}

}
