package rpgapp.data.character;

import rpgapp.RPGApp;

public class FirstAid extends SkillOutFight {

	public FirstAid(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect() {
		RPGApp.hero.heal(40);
		// TODO Auto-generated method stub
		
	}

}
