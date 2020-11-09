package rpgapp.data.character;
import com.almasb.fxgl.entity.GameWorld;

import javafx.geometry.Point2D;
public class Monstre extends Character{
	private Monstres typeMonstre;
	private int give_experience=100;
	
	
	public Monstre(String nom, int atk, int def, int pv) {
		super(nom,atk,def,pv);
		this.setAtk(atk);
		this.setDef(def);
		this.setPv(pv);
		this.setTypeMonstre(Monstres.Souris);
		this.setFaiblesses(new String[5]);
		this.getFaiblesses()[0]="balai de ménagère";
	}


	public int getGive_experience() {
		return give_experience;
	}


	public void setGive_experience(int give_experience) {
		this.give_experience = give_experience;
	}


	public Monstres getTypeMonstre() {
		return typeMonstre;
	}


	public void setTypeMonstre(Monstres typeMonstre) {
		this.typeMonstre = typeMonstre;
	}


}
