package item;

public class Item {
	private int stat;
	protected static String nom;
	
	public Item(int stat,String nom) {
		this.setStat(stat);
		this.setNom(nom);
	}


	public int getStat() {
		return stat;
	}


	public void setStat(int stat) {
		this.stat = stat;
	}


	public static String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}
}
