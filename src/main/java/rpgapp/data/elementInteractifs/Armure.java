package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Armure extends Equipment implements Serializable {
	public Armure(int b,String a, String c){
		super(b,a,c);
		this.type="Armure";
		this.details=a+" def : "+b;
	}
	
}
