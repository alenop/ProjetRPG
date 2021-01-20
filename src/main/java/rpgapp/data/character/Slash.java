package rpgapp.data.character;

import rpgapp.RPGApp;
import rpgapp.system.Systems;

public class Slash extends Skill_InFight {

	public Slash(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void effect(Character a) {
		Systems.Combat_attaque(a,(int) (RPGApp.hero.getAtk()*1.5));
		// TODO Auto-generated method stub
		
	}

}
