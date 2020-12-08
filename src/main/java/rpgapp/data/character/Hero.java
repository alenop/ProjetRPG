package rpgapp.data.character;

import java.util.ArrayList;
import java.util.HashMap;

import com.almasb.fxgl.entity.view.EntityView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import rpgapp.system.Quest;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Item;

public class Hero extends Character implements Serializable {
	private transient Item inventaire[];
	// private ArrayList<Item> inventaire = new ArrayList<Item>();
	private int experience = 0;
	private transient HashMap<String, Item> equipement = new HashMap<String, Item>();
	private HashMap<Integer, Integer> niveaux;
	private Quest currentquest;
	private String currentMap;
	private EntityView view;
	private ArrayList<String>listeQuêteFinies=new ArrayList<String>();

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
		System.out.println(inventaire);
		for (int i = 0; i < this.inventaire.length; i++) {
			System.out.println(inventaire[i]);
			if (inventaire[i].equals(a)) {
				return j;
			}
			j++;
		}return (Integer) null;
	}
	public int getPositionVoid() {
		int j=0;
		for (int i = 0; i < this.inventaire.length; i++) {
			if (inventaire[i] == null) {
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
	public void setEquipement(HashMap<String, Item> equip) {
		 this.equipement=equip;
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
				a.setPosition(i);
				break;
			}
		}
	}

	public void removeItemInventaire(Item a) {
		for (int i = 0; i < this.inventaire.length; i++) {
			if (inventaire[i] == a) {
				inventaire[i] = null;
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

	public void addListeQuêteFinies(String nom) {
		listeQuêteFinies.add(nom);
	}
	public ArrayList<String> getFinishQuests() {
		return listeQuêteFinies;
	}
	public void save() {
		File f= new File("src\\main\\resources\\example.txt");
		if(f.isFile()) {
		System.out.println(f.canWrite()+"//"+f.canRead());
		}else {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintWriter writer;
		ObjectOutputStream oos = null;
		Files yo;
		String test=String.valueOf(RPGApp.hero);
		Path fichier = Paths.get("src\\main\\resources\\example.txt");
		try {
			
			writer = new PrintWriter(new BufferedWriter(new FileWriter("src\\main\\resources\\example.txt",true)));
			//writer.print(RPGApp.hero);
			//writer.println(test.getBytes());
			Files.write(fichier, test.getBytes());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("yo");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("yo2");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("yo3");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void load() {
		BufferedReader fr;
		Files yo;
		String test="test la joie est grande si ca marche";
		Path fichier = Paths.get("src\\main\\resources\\example.txt");
		try {
			//fr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src\\main\\resources\\example.txt"))));
			//String line = "";
			//while((line = fr.readLine()) != null) {
			    //System.out.println(line);
			//}
			System.out.println(Files.readAllLines(fichier));
		} catch ( IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}
		
	
	

}
