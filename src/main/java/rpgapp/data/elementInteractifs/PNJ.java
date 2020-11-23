package rpgapp.data.elementInteractifs;

public class PNJ {

	private String name;
	private int posX;
	private int posY;
	private String texte;
	private String texteQueteOk;
	private String texteGagne;
	private String textePerd;

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
	
	public PNJ(String name, String message) {
		
		this.name = name;

		// on redonne la position du pnj maintenant que l'image est créée

		this.texte = message;
		this.texteQueteOk = "Tu as trouver les fleurs !";
		this.texteGagne = "Bravo ! Super classe !";
		this.textePerd = "Dommage... Rejoue !";
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
		
}
