package character;
import java.util.HashMap;

import item.Arme;
import item.Armure;
import item.Item;
import system.Quest;
public class Hero extends Character {
	private Item inventaire[];
	private int experience;
	private HashMap<Integer,Integer> niveaux ;
	private Quest currentquest;
	private String currentMap;
	
	public Hero(String nom) {
		super(nom,20,20,50);
		this.setInventaire(new Item[2]);
		this.setAtk(20);
		this.setDef(20);
		this.setPv(50);
		this.niveaux=new HashMap<Integer,Integer>();
		init();
		
	}
	public void gainExp(int experience) {
		this.experience=getExperience()+experience;
		gainLevel(this.experience);
		
	}
	public void gainLevel(int experience) {
		if(experience>=this.niveaux.get(this.level)) {
			this.level+=1;
			System.out.println(this.getAtk()+"**"+this.getDef()+"**");
			this.setAtk(this.atkmax+this.getAtk());
			this.setDef(this.defmax+this.getDef());
			this.Pvmax=this.Pvmax*this.level;
			gainLevel(experience);
		}
	}

	public Item[] getInventaire() {
		return inventaire;
	}
	
	public void init() {
		for (int i=1;i<50;i++) {
			this.niveaux.put(i, i*500+50*i*i);
		}
	}

	public void setInventaire(Item inventaire[]) {
		this.inventaire = inventaire;
	}
	public void equip() {
		this.setAtk(this.getAtk()+this.getInventaire()[0].getStat());
		this.setDef(this.getDef()+this.getInventaire()[1].getStat());
	}
	
	public void desequip() {
		this.setAtk(this.getAtk()-this.getInventaire()[0].getStat());
		this.setDef(this.getDef()-this.getInventaire()[1].getStat());
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}
	public Quest getCurrentquest() {
		return currentquest;
	}
	public void setCurrentquest(Quest currentquest) {
		this.currentquest = currentquest;
	}
	public String getCurrentMap() {
		return currentMap;
	}
	public void setCurrentMap(String currentMap) {
		this.currentMap = currentMap;
	}
	public void rewardQuest() {
		gainExp(currentquest.getReward());
	}
	public int getLevel() {
		return level;
	}
}
