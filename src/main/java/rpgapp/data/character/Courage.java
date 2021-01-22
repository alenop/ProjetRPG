package rpgapp.data.character;

import rpgapp.RPGApp;

public class Courage extends Skill_InFight {
	private int nbturn;
	private int stat;
	private int finalturn;

	public Courage(String name) {
		super(name);
		this.cost=6;
		this.type=SkillType.Boost;
		this.nbturn=3;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(Character a) {
		this.stat=(int) (RPGApp.hero.getAtk()*0.2);
		RPGApp.hero.give("atk",(int) (RPGApp.hero.getAtk()*0.2));
		RPGApp.hero.useSkill(getCost());
		// TODO Auto-generated method stub
		
	}
	public void effect(int turn) {
		this.stat=(int) (RPGApp.hero.getAtk()*0.2);
		RPGApp.hero.give("atk",(int) (RPGApp.hero.getAtk()*0.2));
		RPGApp.hero.useSkill(getCost());
		//RPGApp.hero.addBoost(this);
		this.finalturn=turn+nbturn;
		// TODO Auto-generated method stub
		
	}
	public void deleteBonus() {
		RPGApp.hero.give("atk",-stat);
	}
	public int getFinalTurn() {
		return finalturn;
	}

}
