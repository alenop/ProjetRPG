package rpgapp.data.character;

import java.io.Serializable;

public abstract class Skill implements Serializable {
	private String name;
	public Skill(String name) {
		this.setName(name);
	}
	//public abstract void effect();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
