package elementsInteractifs;

import java.util.ArrayList;
import java.util.List;

import item.Item;

public class PNJ {

	private String name;
	private int posX;
	private int posY;
	private String texte;
	private String texteQueteOk;
	private String texteGagne;
	private String textePerd;
	private List<Item> items=new ArrayList<>();

	public String getTexte() {
		return texte;
	}

	public String getTexteGagne() {
		return texteGagne;
	}

	public String getTextePerd() {
		return textePerd;
	}

	public String getTexteQueteOk() {
		return texteQueteOk;
	}

	public PNJ(String name, int x, int y, String message) {

		this.name = name;

		// on redonne la position du pnj maintenant que l'image est créée
		this.posX = x;
		this.posY = y;

		this.texte = message;
		this.texteQueteOk = message;
		this.texteGagne = message;
		this.textePerd = message;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public String getName() {
		return name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getTextitems() {
		String txt = "";
		for (Item i : items) {
			txt = i.getNom() + "\n";
		}
		return txt;
	}
}
