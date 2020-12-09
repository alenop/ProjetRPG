package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Item implements Serializable {
	private int stat;
	private String nom;
	protected String type;
	private String image;
	private int position=-2;
	
	public Item(int stat,String nom,String image) {
		this.setStat(stat);
		this.setNom(nom);
		this.image=image;
	}

	public String getImage() {
		return image;
	}
	public int getStat() {
		return stat;
	}
	public String getInventaireImage() {
		String[] a =image.split("\\.");
		return a[0]+"_Inventaire."+a[1];
	}


	public void setStat(int stat) {
		this.stat = stat;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
