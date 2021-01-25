package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Arme extends Equipment implements Serializable{
	public Arme(int b,String a, String c){
		super(b,a,c);
		this.type="Arme";
		this.details=a+" atk : "+b;
	}

	
}
