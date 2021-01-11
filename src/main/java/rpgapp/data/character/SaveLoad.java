package rpgapp.data.character;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javafx.geometry.Point2D;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.system.SaveMap;
import rpgapp.view.DisplayMap;

public abstract class SaveLoad {
	public static Hero load(String menu) {
		ObjectInputStream ois = null;
		try {
		      final FileInputStream fichier = new FileInputStream("mon_objet.ser");
		      ois = new ObjectInputStream(fichier);
		      final Hero hero = (Hero) ois.readObject();
		      hero.setInventory((Item[]) ois.readObject());
		      hero.setEquipment((HashMap<String, Item>) ois.readObject());
		      hero.setPosition(new Point2D(ois.readDouble(),ois.readDouble()));
		      SaveMap.load(ois);
		      if (menu.equals("GameMenu")) {
		      DisplayMap.chargeMap(hero.getCurrentMap(),hero.getPosition());
		      }
		      
		      return hero;
		    } catch (final java.io.IOException e) {
		      e.printStackTrace();
		    } catch (final ClassNotFoundException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        if (ois != null) {
		          ois.close();
		        }
		      } catch (final IOException ex) {
		        ex.printStackTrace();
		      }
		    }
		return null;
		  }
		
	
	
	
	public static void save(Hero hero) {
		ObjectOutputStream oos = null;

	    try {
	      final FileOutputStream fichier = new FileOutputStream("mon_objet.ser");
	      oos = new ObjectOutputStream(fichier);
	     
	      oos.writeObject(hero);
	      oos.writeObject(hero.getInventory());
	      oos.writeObject(hero.getEquipement());
	      oos.writeDouble(RPGApp.player.getX());
		  oos.writeDouble(RPGApp.player.getY());
		  SaveMap.save(oos);
	      
	      
	      
	      // ...
	    } catch (final java.io.IOException e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (oos != null) {
	          oos.flush();
	          oos.close();
	        }
	      } catch (final IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	}
}
