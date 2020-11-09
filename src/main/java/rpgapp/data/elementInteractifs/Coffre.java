package rpgapp.data.elementInteractifs;
import rpgapp.data.elementInteractifs.Item;

public class Coffre {
	private Item contenu;
	public Coffre(Item a) {
		this.setContenu(a);
	}
	public Item getContenu() {
		return contenu;
	}
	public void setContenu(Item contenu) {
		this.contenu = contenu;
	}
}
