package rpg.data.character;

import java.io.Serializable;

public abstract class Character  implements Serializable {
	private String name;
	private int atk;
	private int def;
	protected int Pvmax;
	protected int atkmax;
	protected int defmax;
	protected int Mp;
	protected int MpMax;
	private int pv;
	private State state;
	private int spd;
	private String weaknesses[];
	protected int level=1;
	
	public Character(String nom, int atk, int def, int pv) {
		this.setName(nom);
		this.Pvmax=pv;
		this.atkmax=atk;
		this.defmax=def;
		this.setAtk(atk);
		this.setDef(def);
		this.setPv(pv);
		this.setSpd(20);
		this.setState(State.alive);
	}
	
	

	public int getDef() {
		return def;
	}
	public void fullLife() {
		this.state=State.alive;
		this.setPv(Pvmax);
	}

	public void setDef(int def) {
		this.def = def;
	}
	public int getPvMax() {
		return Pvmax;
	}

	public void setPvMax(int pvmax) {
		this.Pvmax = pvmax;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(double d) {
		this.pv = (int) d;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getSpd() {
		return spd;
	}

	public void setSpd(int spd) {
		this.spd = spd;
	}
	
	public String[] getWeaknesses() {
		return weaknesses;
	}

	public void setWeaknesses(String faiblesses[]) {
		this.weaknesses = faiblesses;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public int getMpMax() {
		return MpMax;
	}



	public int getMp() {
		return Mp;
	}
}
