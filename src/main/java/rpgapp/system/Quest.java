package rpgapp.system;


import rpgapp.data.character.Monstres;

public class Quest {
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
//		FOR (CONDITIONS I : CONDITIONS) {
//			//I.VERIF_CONDITION(A, B)
//		}
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
}
