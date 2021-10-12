package rpg.data.character;

import rpg.RPGApp;

public class Heal extends Skill {
	private int pourcentage;
	public Heal(String name, int cost, int pourcentage) {
		super(name);
		this.cost=cost;
		this.type=SkillType.heal;
		this.pourcentage=pourcentage;
		this.inFight=false;
	}

	
	public void effect() {
		RPGApp.hero.heal(pourcentage);
		RPGApp.hero.useSkill(getCost());
		
	}
}
