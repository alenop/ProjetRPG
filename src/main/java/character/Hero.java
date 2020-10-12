package character;
import item.Item;

public class Hero extends Character {
	private Item inventaire[];
	
	public Hero() {
		super();
		this.setInventaire(new Item[2]);
		this.setAtk(30);
		this.setDef(20);
		this.setPv(50);
		
	}

	public Item[] getInventaire() {
		return inventaire;
	}

	public void setInventaire(Item inventaire[]) {
		this.inventaire = inventaire;
	}
}
