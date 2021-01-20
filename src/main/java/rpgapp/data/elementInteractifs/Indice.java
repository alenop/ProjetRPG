package rpgapp.data.elementInteractifs;
import java.io.Serializable;

public class Indice implements Serializable{
	
	private String name;
	private String image;
	
	public Indice(String name, String image) {
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public void setImage() {
		this.image = image;
	}

}
