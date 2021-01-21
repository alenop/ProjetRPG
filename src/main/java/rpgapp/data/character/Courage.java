package rpgapp.data.character;

import rpgapp.RPGApp;

public class Courage extends Skill_InFight {

	public Courage(String name) {
		super(name);
		this.cost=6;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(Character a) {
		RPGApp.hero.give("atk",(int) (RPGApp.hero.getAtk()*0.2));
		RPGApp.hero.useSkill(getCost());
		// TODO Auto-generated method stub
		
	}

}
