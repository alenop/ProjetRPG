package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Arme extends Item implements Serializable{
	public Arme(int b,String a, String c){
		super(b,a,c);
		this.type="Arme";
	}
	
}
