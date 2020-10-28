package character;

public class Monstre extends Character{
	private Monstres monstre;
	
	
	public Monstre(String nom, int atk, int def, int pv) {
		super(nom);
		this.setAtk(atk);
		this.setDef(def);
		this.setPv(pv);
		this.monstre=Monstres.Souris;
		this.setFaiblesses(new String[5]);
		this.getFaiblesses()[0]="balai de ménagère";
	}

}
