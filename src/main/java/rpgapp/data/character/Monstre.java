package rpgapp.data.character;

public class Monstre extends Character{
	private Monstres typeMonstre=Monstres.Rat;
	private int give_experience=100;
	private boolean Unique=false;
	
	public Monstre(String nom, int atk, int def, int pv,boolean unique) {
		super(nom,atk,def,pv);
		this.Unique=unique;
		this.setFaiblesses(new String[5]);
		this.getFaiblesses()[0]="balai de ménagère";
	}
	public Monstre(String nom, int atk, int def, int pv) {
		super(nom,atk,def,pv);
		this.setFaiblesses(new String[5]);
		this.getFaiblesses()[0]="balai de ménagère";
	}
	public Monstre(String nom, int atk, int def, int pv,Monstres type) {
		super(nom,atk,def,pv);
		this.setTypeMonstre(type);
		this.setFaiblesses(new String[5]);
		if (type.equals(Monstres.Rat)) {
		this.getFaiblesses()[0]="balai de ménagère";
		}
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
	public boolean isUnique() {
		return this.Unique;
	}


}
