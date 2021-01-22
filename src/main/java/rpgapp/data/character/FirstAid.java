package rpgapp.data.character;

import rpgapp.RPGApp;

public class FirstAid extends SkillOutFight {

	public FirstAid(String name) {
		super(name);
		this.cost=3;
		this.type=SkillType.heal;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect() {
		RPGApp.hero.heal(40);
		RPGApp.hero.useSkill(getCost());
		// TODO Auto-generated method stub
		
	}

}
