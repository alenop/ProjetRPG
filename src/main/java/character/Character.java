package character;

import java.util.HashMap;

public abstract class Character {
	private String nom;
	//protected Apparence apparence;
	private int atk;
	private int def;
	protected int Pvmax;
	protected int atkmax;
	protected int defmax;
	private int pv;
	private Etat etat;
	private int spd;
	private Faiblesse faiblesses2[];
	private String faiblesses[];
	protected int level=1;
	
	public Character(String nom, int atk, int def, int pv) {
		// comment
		this.setNom(nom);
		this.Pvmax=pv;
		this.atkmax=atk;
		this.defmax=def;
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



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}
}
