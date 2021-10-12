package rpg.data.character;

public class Monstre extends Character{
	private Monstres typeMonster=Monstres.Rat;
	private int give_experience=100;
	private boolean Unique=false;
	private String quest;
	
	
	public Monstre(String nom, int atk, int def, int pv,boolean unique, String quest,Monstres type) {
		super(nom,atk,def,pv);
		this.Unique=unique;
		this.setWeaknesses(new String[5]);
		this.getWeaknesses()[0]="Balai";
		this.quest=quest;
		this.setTypeMonster(type);
	}
	public Monstre(String nom, int atk, int def, int pv,boolean unique, String quest) {
		super(nom,atk,def,pv);
		this.Unique=unique;
		this.setWeaknesses(new String[5]);
		this.getWeaknesses()[0]="Balai";
		this.quest=quest;
	}
	public Monstre(String nom, int atk, int def, int pv) {
		super(nom,atk,def,pv);
		this.setWeaknesses(new String[5]);
		this.getWeaknesses()[0]="Balai";
	}
	public Monstre(String nom, int atk, int def, int pv,Monstres type) {
		super(nom,atk,def,pv);
		this.setTypeMonster(type);
		this.setWeaknesses(new String[5]);
		if (type.equals(Monstres.Rat)) {
		this.getWeaknesses()[0]="Balai";
		}
	}
	
	public String getQuest() {
		if (this.Unique) {
			return this.quest;
		}else {
			return "ce monstre n'est pas unique et par cons�quent ne dispose pas de qu�te associ�e";
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
