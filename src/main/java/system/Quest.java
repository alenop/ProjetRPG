package system;

import character.Monstre;

public class Quest {
	private Conditions conditions[];
	private String nom;
	private int reward;
	private boolean succeed=false;
	private int nb_kill;
	private Monstre monstre;
	private int kill=0;
	
	public Quest(String a,int b,Monstre c, int d) {
		this.nom=a;
		this.setReward(b);
		this.nb_kill=d;
		this.monstre=c;
		
	}
	public boolean verifQuest() {
		boolean b=getKill()==nb_kill;
		if (succeed==false) {
			if (b) {
				
			succeed=true;
			return (b) ;
			}else {
				return false;}
		}else {
			return false;
		}}
	
	public void verif_Quest() {
		for (Conditions i : conditions) {
			//i.verif_condition(a, b)
		}
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
}
