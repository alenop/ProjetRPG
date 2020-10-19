package character;

public abstract class Character {
	protected String nom;
	//protected Apparence apparence;
	private int atk;
	private int def;
	private int pvmax;
	private int pv;
	private Etat etat;
	private int spd;
	private Faiblesse faiblesses2[];
	private String faiblesses[];
	
	public Character(String nom) {
		this.nom=nom;
		this.pvmax=pvmax;
		this.setAtk(atk);
		this.setDef(def);
		this.setPv(pv);
		this.setSpd(20);
		this.setEtat(Etat.vivant);
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

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public int getSpd() {
		return spd;
	}

	public void setSpd(int spd) {
		this.spd = spd;
	}

	public Faiblesse[] getFaiblesses2() {
		return faiblesses2;
	}

	public void setFaiblesses2(Faiblesse faiblesses2[]) {
		this.faiblesses2 = faiblesses2;
	}
	public String[] getFaiblesses() {
		return faiblesses;
	}

	public void setFaiblesses(String faiblesses[]) {
		this.faiblesses = faiblesses;
	}
}
