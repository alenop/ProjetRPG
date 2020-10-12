package character;

public abstract class Character {
	protected String nom;
	private int atk;
	private int def;
	private int pv;
	
	public Character() {
		this.nom=nom;
		this.setAtk(atk);
		this.setDef(def);
		this.setPv(pv);
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
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

	public void setPv(int pv) {
		this.pv = pv;
	}
}
