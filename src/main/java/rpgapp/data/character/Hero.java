package rpgapp.data.character;

import java.util.ArrayList;
import java.util.HashMap;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import java.io.Serializable;


import rpgapp.system.Quest;
import rpgapp.system.Systems;
import rpgapp.view.DisplayBasic;
import rpgapp.RPGApp;
import rpgapp.control.PlayerComponent;
import rpgapp.control.QuestComponent;
import rpgapp.data.elementInteractifs.Equipment;
import rpgapp.data.elementInteractifs.Item;

public class Hero extends Character implements Serializable {
	private transient Item inventory[];
	// private ArrayList<Item> inventaire = new ArrayList<Item>();
	private int experience = 0;
	private int requis = 1000;
	private transient HashMap<String, Equipment> equipment = new HashMap<String, Equipment>();
	private HashMap<Integer, Integer> levels;
	private HashMap<String, Integer> stats = new HashMap<String,Integer>();
	private Quest currentquest;
	private String currentMap;
	private transient EntityView view;
	private ArrayList<String>listFinishQuests=new ArrayList<String>();
	private transient Point2D position ;
	private ArrayList<Skill> Skills= new ArrayList<Skill>();
	
	private final int Mpgrowth=1;
	private final int Atkgrowth=2;
	private final int Defgrowth=2;
	private final int Pvgrowth=5;

	public Hero(String nom) {
		super(nom, 20, 20, 50);
		this.setInventory(new Item[16]);
		this.setAtk(20);
		this.setDef(20);
		this.setPv(50);
		this.levels = new HashMap<Integer, Integer>();
		this.setView(view);
		initLevels();
		Skills.add(new FirstAid("firstAid"));
		Skills.add(new Slash("Slash"));
		Skills.add(new Courage("Courage"));
		this.Mp=10;
		this.MpMax=10;
		stats.put("atk",20);
		stats.put("def",20);
		stats.put("pv",50);
		stats.put("mp",10);
	}
	public int getLv() {
		return level;
	}
	public void setPosition(Point2D pos) {
		this.position=pos;
	}
	public ArrayList<Skill> getSkills() {
		return this.Skills;
	}
	public Point2D getPosition() {
		return this.position;
	}
	public void gainExp(int experience) {
		this.experience = getExperience() + experience;
		while(this.experience >= this.requis) {
			gainLevel(this.experience);
			this.experience = this.experience - requis;
			this.requis = this.requis + 500;
		}
		
		
		
	}

	public void gainLevel(int experience) {
		if (experience >= this.levels.get(this.level)) {
			this.level += 1;
			System.out.println("gain de niveau ! Niveau actuel : " + this.level);
			setAtk(getAtk() + Atkgrowth);
			setDef(getDef() + Defgrowth);
			Mp=MpMax+Mpgrowth;
			atkmax=atkmax+Atkgrowth;
			defmax=defmax+Defgrowth;
			MpMax=MpMax+Mpgrowth;
			Pvmax = Pvmax + Pvgrowth;
			setPv(getPv()+Pvgrowth);
			
			PlayerComponent.levelUp(this.level);
			
		}
	}
	public int getPositionVoid() {
		int j=0;
		for (int i = 0; i < this.inventory.length; i++) {
			if (inventory[i] == null) {
				return j;
			}
			j++;
		}return (Integer) null;
	}
	public Item[] getInventory() {
		return inventory;
	}

	public void initLevels() {
		for (int i = 1; i < 50; i++) {
			this.levels.put(i, i * 500 + 50 * i * i);
		}
	}

	public void setInventory(Item inventaire[]) {
		this.inventory = inventaire;
	}

	public void equip(Equipment equipment) {
		System.out.println(equipment);
		System.out.println(equipment.getName());
		if (this.equipment.get(equipment.getType()) != null) {
			unequip(this.equipment.get(equipment.getType()));
		}
		this.equipment.put(equipment.getType(), equipment);

		if (equipment.getType().equals("Arme")) {
			this.setAtk(this.getAtk() + equipment.getStat());
			System.out.println("yo");
		} else if (equipment.getType().equals("Armure")) {
			this.setDef(this.getDef() + equipment.getStat());
		}
	}

	public HashMap<String, Equipment> getEquipement() {
		return equipment;
	}
	public void setEquipment(HashMap<String, Equipment> equip) {
		 this.equipment=equip;
	}

	public void unequip() {

		unequip(this.equipment.get("Arme"));
		unequip(this.equipment.get("Armure"));
	}

	public void unequip(Equipment equipment) {
		if (equipment.getType() == "Arme") {
			this.setAtk(this.getAtk() - equipment.getStat());
		} else if (equipment.getType() == "Armure") {
			this.setDef(this.getDef() - equipment.getStat());
		}
		addItemInventory(equipment);
		this.equipment.remove(equipment.getType());
	}

	public void addItemInventory(Item item) {
		int i =getPositionVoid();
		inventory[i]=item;
		item.setPosition(i);
}
	public void removeItemInventory(Item item) {
		for (int i = 0; i < this.inventory.length; i++) {
			if (inventory[i] == item) {
				inventory[i] = null;
				break;
			}
		}
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

	public EntityView getView() {
		return view;
	}

	public void setView(EntityView view) {
		this.view = view;
	}

	public void addlistFinishQuests(String nomFinishQuest) {
		listFinishQuests.add(nomFinishQuest);
	}
	public ArrayList<String> getFinishQuests() {
		return listFinishQuests;
	}
	public boolean finishQuest(String namequest) {
		return listFinishQuests.contains(namequest);
	}
	public void heal(double a) {
		double result = this.Pvmax*(a/100);
		if(result+this.getPv()>=this.Pvmax) {
			this.setPv(this.Pvmax);
		}else {
			this.setPv(this.getPv()+result);
		}
	}
	public void skillHeal() {
		heal(40);
	}
	public void skillSlash(Character a) {
		Systems.Combat_attaque(a,(int) (this.getAtk()*1.5));
	}
	public void useSkill(int a) {
		Mp=Mp-a;
		// TODO Auto-generated method stub
		
	}
	public void updateStats() {
		stats.replace("atk",getAtk());
		stats.replace("def",getDef());
		stats.replace("pv",getPv());
		stats.replace("mp",Mp);
	}
	public HashMap<String, Integer> getStats() {
		updateStats();
		return stats;
	}
	public void givePermanently(String type,int nb) {
		switch(type) {
		case "atk":
			atkmax=(atkmax+nb);
			setAtk(getAtk()+nb);
			
			break;
		case "def":
			defmax=(defmax+nb);
			setDef(getDef()+nb);
			
			break;
		case "pv":
			Pvmax=(Pvmax+nb);
			setPv(getPv()+nb);
			
			break;
		case "mp":
			MpMax=(MpMax+nb);
			Mp =(getMp()+nb);
			break;
		}
	}
	public void give(String type,int nb) {
		switch(type) {
		case "atk":
			setAtk(getAtk()+nb);
			break;
		case "def":
			setDef(getDef()+nb);
			break;
		case "pv":
			setPv(getPv()+nb);
			break;
		case "mp":
			Mp =(getMp()+nb);
			break;
		}
	}
	public void restore(String type,int nb) {
		switch(type) {
		case "pv":
			heal(nb);
			break;
		case "mp":
			if(getMp()+nb<MpMax) {
				Mp =(getMp()+nb);
			}else {
				Mp=MpMax;
			}
			break;
			
		}
	}
	public void resetTemporaryBonus() {
		setAtk(atkmax+getEquipement().get("Arme").getStat());
		setDef(defmax+getEquipement().get("Armure").getStat());
		restore("pv",0);
		restore("mp",0);
	}
	
	
		
	
	

}
