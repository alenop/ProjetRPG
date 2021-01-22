package rpgapp.data.character;

import java.io.Serializable;

public abstract class Skill implements Serializable {
	private String name;
	protected int cost;
	protected SkillType type;
	protected boolean inFight;
	public Skill(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCost() {
		return cost;
	}
	public SkillType getType() {
		return this.type;
	}
	public boolean canInFight() {
		return this.inFight;
	}
}
