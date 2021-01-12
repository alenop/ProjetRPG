package rpgapp.data.elementInteractifs;

import rpgapp.RPGApp;

public class Potion extends Consumable {

	public Potion(String name, String image) {
		super(name, image);
		this.type="potion";
		//this.setStat(50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect() {
		RPGApp.hero.heal(50);
		// TODO Auto-generated method stub
		
	}

}
