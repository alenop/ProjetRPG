package rpgapp.data.elementInteractifs;
import rpgapp.data.elementInteractifs.Item;

public class Chest {
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
