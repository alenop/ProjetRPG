package rpgapp.system;


import java.io.Serializable;

import rpgapp.RPGApp;
import rpgapp.data.character.Monstres;
import rpgapp.view.DisplayQuete;

public class Quest implements Serializable {
	private String name;
	private int reward;
	private boolean succeed=false;
	private int nb_kill;
	private int nbCiblesMax;
	private int nbCibles;
	private String action;
	private Monstres typeMonstre;
	private String typeCibles;
	private int kill=0;
	private String description;
	private int id;
	
	public Quest(String a,int b,Monstres c, int d) {
		this.name=a;
		this.setReward(b);
		this.nb_kill=d;
		this.setTypeMonstre(c);
		
	}
	
	public Quest(String name, int reward, String action, String typeCibles, int nbCiblesMax, String description) {
		this.name = name;
		this.reward = reward;
		this.action = action;
		this.typeCibles = typeCibles;
		this.nbCibles = 0;
		this.nbCiblesMax = nbCiblesMax;
		this.description = description;
		this.id = 0;
	}
	
	public Quest(String name, int reward, String action, String typeCibles, int nbCiblesMax, String description, int id) {
		this.name = name;
		this.reward = reward;
		this.action = action;
		this.typeCibles = typeCibles;
		this.nbCibles = 0;
		this.nbCiblesMax = nbCiblesMax;
		this.description = description;
		this.id = id;
	}
	
	public Quest(String name) {
		this.name = name;
	}
	
	public Quest() {
		this.name="null";
	}
	
	public boolean verifQuest() {
		boolean b=getKill()==nb_kill;
		if (succeed==false) {
			if (b) {
			succeed=true;
			DisplayQuete.updateQuete();
			return (b) ;
			}else {
				return false;}
		}else {
			return false;
		}}
	
	public boolean verifQuest2() {
		boolean b=getKill()==nb_kill;
		if (succeed==false) {
			if (b) {
			succeed=true;
			DisplayQuete.updateQuete();
			return (b) ;
			}else {
				return false;}
		}else {
			return false;
		}}
	
	public void validQuest() {
		RPGApp.hero.addlistFinishQuests(name);
		RPGApp.hero.setCurrentquest(null);
		RPGApp.hero.gainExp(this.reward);
	}
	
	public void upNbCibles() {
		this.nbCibles +=1;
	}
	
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
	public int getKill() {
		return kill;
	}
	public void setKill(int kill) {
		this.kill = kill;
	}
	public Monstres getTypeMonstre() {
		return typeMonstre;
	}
	public void setTypeMonstre(Monstres typeMonstre) {
		this.typeMonstre = typeMonstre;
	}
	public String getName() {
		return name;
	}
	
	public int getNbKill() {
		return nb_kill;
	}
	
	public int getNbCibles() {
		return nbCibles;
	}

	public void setNbCibles(int nbCibles) {
		this.nbCibles = nbCibles;
	}
	
	public int getNbCiblesMax() {
		return nbCiblesMax;
	}

	public void setNbCiblesMax(int nbCiblesMax) {
		this.nbCiblesMax = nbCiblesMax;
	}

	public String getTypeCibles() {
		return typeCibles;
	}

	public void setTypeCibles(String typeCibles) {
		this.typeCibles = typeCibles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
