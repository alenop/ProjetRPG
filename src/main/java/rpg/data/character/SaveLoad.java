package rpg.data.character;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javafx.geometry.Point2D;
import rpg.RPGApp;
import rpg.data.elementInteractifs.Equipment;
import rpg.data.elementInteractifs.Item;
import rpg.system.SaveMap;

public abstract class SaveLoad {
	public static Hero load(String menu) {
		ObjectInputStream ois = null;
		try {
		      final FileInputStream file = new FileInputStream("mon_objet.ser");
		      ois = new ObjectInputStream(file);
		      final Hero hero = (Hero) ois.readObject();
		      hero.setInventory((Item[]) ois.readObject());
			  /*HashMap<String, Equipment> equiptest=new HashMap<>();
			  Class clas = equiptest.getClass();
			  Object b = ois.readObject();
			  clas.cast(b);
			HashMap<String, Equipment> equip=null;
			  if(clas.isInstance(b)){
				 equip = (HashMap<String, Equipment>) b;
			  }*/
			  HashMap<String, Equipment> equip = (HashMap<String, Equipment>) ois.readObject();
		      hero.setEquipment(equip);
		      hero.setPosition(new Point2D(ois.readDouble(),ois.readDouble()));
		      SaveMap.load(ois);
		      
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
	      final FileOutputStream file = new FileOutputStream("mon_objet.ser");
	      oos = new ObjectOutputStream(file);
	     
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
