package character;

import javafx.geometry.Point2D;
import java.util.HashMap;

public abstract class MonsterList {
 public static HashMap<Point2D, Monstre> MonsterList;
 
 public static void init() {
	 MonsterList=new HashMap<Point2D, Monstre>();
 }

 
}
