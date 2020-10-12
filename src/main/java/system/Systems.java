package system;
import character.Hero;
import character.Monstre;
import character.Character;
public class Systems {
	
	public static void main(String[] args) {
		Character hero= new Hero();
		Character monstre = new Monstre();
		Combat_attaque(hero,monstre);
		Combat_attaque(monstre,hero);
		
	}

 

	public static void Combat_attaque(Character a,Character b) {
		System.out.println(a.getPv());
		System.out.println(b.getPv());
		a.setPv(b.getAtk()-a.getDef());
		System.out.println(a.getPv());
		System.out.println(b.getPv());
	}
}
