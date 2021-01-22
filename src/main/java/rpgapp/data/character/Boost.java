package rpgapp.data.character;

import rpgapp.RPGApp;

public class Boost extends Skill {
	private int nbturn;
	private int boost;
	private int finalturn;
	private String stat;
	private int pourcentage;

	public Boost(String name, int cost, int nbturn, String stat,int pourcentage) {
		super(name);
		this.cost=cost;
		this.type=SkillType.Boost;
		this.nbturn=nbturn;
		this.pourcentage=pourcentage;
		this.inFight=true;
		this.stat=stat;
	}
	public void effect(int turn) {
		this.boost=RPGApp.hero.givemultiply("atk",(int) (this.pourcentage));
		RPGApp.hero.give(stat, boost);
		RPGApp.hero.useSkill(getCost());
		RPGApp.hero.addBoost(this);
		this.finalturn=turn+nbturn;
		
	}
	public void deleteBonus() {
		RPGApp.hero.give(stat,-boost);
	}
	public int getFinalTurn() {
		return finalturn;
	}

}
