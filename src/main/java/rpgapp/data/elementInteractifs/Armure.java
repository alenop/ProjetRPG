package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Armure extends Item implements Serializable {
	public Armure(int b,String a, String c){
		super(b,a,c);
		this.type="Armure";
	}
	
}
