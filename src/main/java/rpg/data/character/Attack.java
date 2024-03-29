package rpg.data.character;

import rpg.RPGApp;
import rpg.system.Systems;

public class Attack extends Skill {
	private int pourcentage;
	public Attack(String name, int cost, int pourcentage) {
		super(name);
		this.cost=cost;
		this.pourcentage=pourcentage;
		this.type=SkillType.attaque;
		this.inFight=true;
	}
	
	public void effect(Character a) {
		Systems.Combat_attaque(a,(int) (RPGApp.hero.getAtk()*(1+pourcentage/100)));
		RPGApp.hero.useSkill(getCost());
		
	}
}
