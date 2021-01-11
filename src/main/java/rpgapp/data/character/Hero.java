package rpgapp.data.character;

import java.util.ArrayList;
import java.util.HashMap;

import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import java.io.Serializable;


import rpgapp.system.Quest;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Item;

public class Hero extends Character implements Serializable {
	private transient Item inventory[];
	// private ArrayList<Item> inventaire = new ArrayList<Item>();
	private int experience = 0;
	private transient HashMap<String, Item> equipment = new HashMap<String, Item>();
	private HashMap<Integer, Integer> levels;
	private Quest currentquest;
	private String currentMap;
	private transient EntityView view;
	private ArrayList<String>listFinishQuests=new ArrayList<String>();
	private transient Point2D position ;

	public Hero(String nom) {
		super(nom, 20, 20, 50);
		this.setInventory(new Item[16]);
		this.setAtk(20);
		this.setDef(20);
		this.setPv(50);
		this.levels = new HashMap<Integer, Integer>();
		this.setView(view);
		initLevels();

	}
	public void setPosition(Point2D pos) {
		this.position=pos;
	}
	public Point2D getPosition() {
		return this.position;
	}
	public void gainExp(int experience) {
		this.experience = getExperience() + experience;
		gainLevel(this.experience);

	}

	public void gainLevel(int experience) {
		if (experience >= this.levels.get(this.level)) {
			this.level += 1;
			System.out.println("gain de niveau ! Niveau actuel : " + this.level);
			this.setAtk(this.atkmax + this.getAtk());
			this.setDef(this.defmax + this.getDef());
			this.Pvmax = this.Pvmax * this.level;
			gainLevel(experience);
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

	public void equip(Item item) {
		if (this.equipment.get(item.getType()) != null) {
			unequip(this.equipment.get(item.getType()));
		}

		this.equipment.put(item.getType(), item);

		if (item.getType() == "Arme") {
			this.setAtk(this.getAtk() + item.getStat());
		} else if (item.getType() == "Armure") {
			this.setDef(this.getDef() + item.getStat());
		}
	}

	public HashMap<String, Item> getEquipement() {
		return equipment;
	}
	public void setEquipment(HashMap<String, Item> equip) {
		 this.equipment=equip;
	}

	public void unequip() {

		unequip(this.equipment.get("Arme"));
		unequip(this.equipment.get("Armure"));
	}

	public void unequip(Item item) {
		if (item.getType() == "Arme") {
			this.setAtk(this.getAtk() - item.getStat());
		} else if (item.getType() == "Armure") {
			this.setDef(this.getDef() - item.getStat());
		}
		addItemInventory(item);
		this.equipment.remove(item.getType());
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
	
		
	
	

}
