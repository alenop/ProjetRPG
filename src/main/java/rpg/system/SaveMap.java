package rpg.system;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map.Entry;

import javafx.geometry.Point2D;
import rpg.RPGApp;
import rpg.data.elementInteractifs.Chest;
import rpg.data.map.ModeleMap;

public abstract class SaveMap {
	
	public static void save(ObjectOutputStream oos) {
		for (Entry<String, ModeleMap> map:RPGApp.ListeMaps.entrySet()) {
			try {
				saveChest(oos,map.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void saveChest(ObjectOutputStream oos,ModeleMap map) throws IOException {
		for (Entry<Point2D, Chest> chest:map.getCoffreList().entrySet()) {
			oos.writeDouble(chest.getKey().getX());
			oos.writeDouble(chest.getKey().getY());
			oos.writeObject(chest.getValue());
		}
	}
	public static void loadChest(ObjectInputStream ois,ModeleMap map) throws ClassNotFoundException, IOException {
		for (Entry<Point2D, Chest> chest:map.getCoffreList().entrySet()) {
				map.getCoffreList().put(new Point2D(ois.readDouble()
				, ois.readDouble())
				,(Chest) ois.readObject());
		}
	}
	public static void load(ObjectInputStream ois) {
		for (Entry<String, ModeleMap> map:RPGApp.ListeMaps.entrySet()) {
			try {
				loadChest(ois,map.getValue());
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
