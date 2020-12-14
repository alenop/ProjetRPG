package rpgapp.data.elementInteractifs;

import java.io.Serializable;

public class Item implements Serializable {
	private int stat;
	private String name;
	protected String type;
	private String image;
	private int position=-2;
	
	public Item(int stat,String name,String image) {
		this.setStat(stat);
		this.setName(name);
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
