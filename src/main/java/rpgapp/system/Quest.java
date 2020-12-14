package rpgapp.system;


import java.io.Serializable;

import rpgapp.RPGApp;
import rpgapp.data.character.Monstres;

public class Quest implements Serializable {
	private String nom;
	private int reward;
	private boolean succeed=false;
	private int nb_kill;
	private Monstres typeMonstre;
	private int kill=0;
	
	public Quest(String a,int b,Monstres c, int d) {
		this.nom=a;
		this.setReward(b);
		this.nb_kill=d;
		this.setTypeMonstre(c);
		
	}
	
	public Quest() {
		this.nom="null";
	}
	
	public boolean verifQuest() {
		boolean b=getKill()==nb_kill;
		if (succeed==false) {
			if (b) {
			RPGApp.hero.addListeQuêteFinies(nom);
			succeed=true;
			return (b) ;
			}else {
				return false;}
		}else {
			return false;
		}}
	
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
		return nom;
	}
}
