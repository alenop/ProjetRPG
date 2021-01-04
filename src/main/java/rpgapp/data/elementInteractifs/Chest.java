package rpgapp.data.elementInteractifs;
import java.io.Serializable;

import rpgapp.data.elementInteractifs.Item;

public class Chest implements Serializable {
	private Item loot;
	
	public Chest(Item a) {
		this.setLoot(a);
	}
	public Item getLoot() {
		return loot;
	}
	public void setLoot(Item loot) {
		this.loot = loot;
	}
}
