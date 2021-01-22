package rpgapp.data.elementInteractifs;

import rpgapp.RPGApp;

public class ManaPotion extends Consumable {

	public ManaPotion(String name, String image) {
		super(name, image);
		this.details="Restaure 10 mp au joueur";
	}

	@Override
	public void effect() {
		RPGApp.hero.restore("mp",10);
		
	}

}
