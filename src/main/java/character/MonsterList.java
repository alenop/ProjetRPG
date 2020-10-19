package character;

import java.util.HashMap;

public abstract class MonsterList {
 public static HashMap<String, Monstre> MonsterList;
 
 public static void init() {
	 MonsterList=new HashMap<String, Monstre>();
 }

 
}
