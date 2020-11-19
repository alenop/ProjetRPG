package rpgapp.data.character;

import java.util.HashMap;
import java.util.Map.Entry;

import com.almasb.fxgl.entity.view.EntityView;

import rpgapp.system.Quest;
import rpgapp.view.DisplayInventaire;
import rpgapp.data.elementInteractifs.Item;

public class Hero extends Character {
	private Item inventaire[];
	// private ArrayList<Item> inventaire = new ArrayList<Item>();
	private int experience = 0;
	private HashMap<String, Item> equipement = new HashMap<String, Item>();
	private HashMap<Integer, Integer> niveaux;
	private Quest currentquest;
	private String currentMap;
	private EntityView view;

	public Hero(String nom) {
		super(nom, 20, 20, 50);
		this.setInventaire(new Item[10]);
		this.setAtk(20);
		this.setDef(20);
		this.setPv(50);
		this.niveaux = new HashMap<Integer, Integer>();
		this.setView(view);
		init();

	}

	public void gainExp(int experience) {
		this.experience = getExperience() + experience;
		gainLevel(this.experience);

	}

	public void gainLevel(int experience) {
		if (experience >= this.niveaux.get(this.level)) {
			this.level += 1;
			System.out.println("gain de niveau ! Niveau actuel : " + this.level);
			this.setAtk(this.atkmax + this.getAtk());
			this.setDef(this.defmax + this.getDef());
			this.Pvmax = this.Pvmax * this.level;
			gainLevel(experience);
		}
	}
	public int getPositionItem(Item a) {
		int j=0;
		for (Entry<String, Item> i : getEquipement().entrySet()) {
			if (i.getValue() == a) {
				return j;
			}
			j++;
		}return (Integer) null;
	}
	public Item[] getInventaire() {
		return inventaire;
	}

	public void init() {
		for (int i = 1; i < 50; i++) {
			this.niveaux.put(i, i * 500 + 50 * i * i);
		}
	}

	public void setInventaire(Item inventaire[]) {
		this.inventaire = inventaire;
	}

	public void equip(Item a) {
		if (this.equipement.get(a.getType()) != null) {
			desequip(this.equipement.get(a.getType()));
		}

		this.equipement.put(a.getType(), a);

		if (a.getType() == "Arme") {
			this.setAtk(this.getAtk() + a.getStat());
		} else if (a.getType() == "Armure") {
			this.setDef(this.getDef() + a.getStat());
		}
	}

	public HashMap<String, Item> getEquipement() {
		return equipement;
	}

	public void desequip() {

		desequip(this.equipement.get("Arme"));
		desequip(this.equipement.get("Armure"));
	}

	public void desequip(Item a) {
		if (a.getType() == "Arme") {
			this.setAtk(this.getAtk() - a.getStat());
		} else if (a.getType() == "Armure") {
			this.setDef(this.getDef() - a.getStat());
		}
		addItemInventaire(a);
		this.equipement.remove(a.getType());
	}

	public void addItemInventaire(Item a) {
		for (int i = 0; i < this.inventaire.length; i++) {
			if (inventaire[i] == null) {
				inventaire[i] = a;
				DisplayInventaire.updateInventaire("ajout", a, i);
				break;
			}
		}
	}

	public void removeItemInventaire(Item a) {
		for (int i = 0; i < this.inventaire.length; i++) {
			if (inventaire[i] == a) {
				inventaire[i] = null;
				DisplayInventaire.updateInventaire("remove", a, i);
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
}
