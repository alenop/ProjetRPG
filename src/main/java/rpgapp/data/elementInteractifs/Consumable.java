package rpgapp.data.elementInteractifs;

public abstract class Consumable extends Item {

	public Consumable(String name, String image) {
		super(name, image);
		// TODO Auto-generated constructor stub
	}
	public abstract void effect();
}
