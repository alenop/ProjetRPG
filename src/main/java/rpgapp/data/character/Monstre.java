package rpgapp.data.character;

public class Monstre extends Character{
	private Monstres typeMonster=Monstres.Rat;
	private int give_experience=100;
	private boolean Unique=false;
	
	public Monstre(String nom, int atk, int def, int pv,boolean unique) {
		super(nom,atk,def,pv);
		this.Unique=unique;
		this.setWeaknesses(new String[5]);
		this.getWeaknesses()[0]="balai de ménagère";
	}
	public Monstre(String nom, int atk, int def, int pv) {
		super(nom,atk,def,pv);
		this.setWeaknesses(new String[5]);
		this.getWeaknesses()[0]="balai de ménagère";
	}
	public Monstre(String nom, int atk, int def, int pv,Monstres type) {
		super(nom,atk,def,pv);
		this.setTypeMonster(type);
		this.setWeaknesses(new String[5]);
		if (type.equals(Monstres.Rat)) {
		this.getWeaknesses()[0]="balai de ménagère";
		}
	}


	public int getGive_experience() {
		return give_experience;
	}


	public void setGive_experience(int give_experience) {
		this.give_experience = give_experience;
	}


	public Monstres getTypeMonstre() {
		return typeMonster;
	}


	public void setTypeMonster(Monstres typeMonstre) {
		this.typeMonster = typeMonstre;
	}
	public boolean isUnique() {
		return this.Unique;
	}


}
