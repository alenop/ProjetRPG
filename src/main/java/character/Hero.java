package character;
import item.Arme;
import item.Armure;
import item.Item;
public class Hero extends Character {
	private Item inventaire[];
	
	public Hero(String nom) {
		super(nom);
		this.setInventaire(new Item[2]);
		this.setAtk(20);
		this.setDef(20);
		this.setPv(50);
		
	}

	public Item[] getInventaire() {
		return inventaire;
	}

	public void setInventaire(Item inventaire[]) {
		this.inventaire = inventaire;
	}
	public void equip() {
		this.setAtk(this.getAtk()+this.getInventaire()[0].getStat());
		this.setDef(this.getDef()+this.getInventaire()[1].getStat());
	}
	
	public void desequip() {
		this.setAtk(this.getAtk()-this.getInventaire()[0].getStat());
		this.setDef(this.getDef()-this.getInventaire()[1].getStat());
	}
}
