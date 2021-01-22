package rpgapp.data.elementInteractifs;

import rpgapp.RPGApp;

public class HealPotion extends Consumable {

	public HealPotion(String name, String image) {
		super(name, image);
		this.type="potion";
		this.details="restaure 50 points de vie";
	}

	@Override
	public void effect() {
		RPGApp.hero.restore("pv",50);
	}

}
