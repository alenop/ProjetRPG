package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Item implements Serializable {
	private String name;
	protected String type;
	private String image;
	private int position=-2;
	
	public Item(String name,String image) {
		this.setName(name);
		this.image=image;
	}

	public String getImage() {
		return image;
	}
	public String getInventaireImage() {
		String[] a =image.split("\\.");
		return a[0]+"_Inventaire."+a[1];
	}


	public String getName() {
		return name;
	}


	public void setName(String nom) {
		this.name = nom;
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
