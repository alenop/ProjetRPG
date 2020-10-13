package character;

public class Monstre extends Character{
	private Monstres monstre;
	
	
	public Monstre(String nom) {
		super(nom);
		this.setAtk(40);
		this.setDef(10);
		this.setPv(100);
		this.monstre=Monstres.Souris;
	}

}
