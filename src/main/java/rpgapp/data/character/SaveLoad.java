package rpgapp.data.character;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Item;

public abstract class SaveLoad {
	

	public static Hero load() {
		ObjectInputStream ois = null;
		try {
		      final FileInputStream fichier = new FileInputStream("mon_objet.ser");
		      ois = new ObjectInputStream(fichier);
		      final Hero hero = (Hero) ois.readObject();
		      hero.setInventaire((Item[]) ois.readObject());
		      hero.setEquipement((HashMap<String, Item>) ois.readObject());
		      
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
	      oos.writeObject(hero.getInventaire());
	      oos.writeObject(hero.getEquipement());
	      
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
