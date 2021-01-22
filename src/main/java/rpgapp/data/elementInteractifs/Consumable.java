package rpgapp.data.elementInteractifs;

public abstract class Consumable extends Item {

	public Consumable(String name, String image) {
		super(name, image);
	}
	public abstract void effect();
}
