package system;
import character.Hero;
import character.Monstre;
import character.Character;
public class Systems {
	
	public static void main(String[] args) {
		Character hero= new Hero();
		Character monstre = new Monstre();
		Combat(hero,monstre);
		Hero thehero = (Hero)hero;
		//System.out.println(thehero.getInventaire().toString());
		
	}



	public static void Combat_attaque(Character a,Character b) {
		System.out.println(a.getPv());
		System.out.println(b.getPv());
		a.setPv(b.getAtk()-a.getDef());
		System.out.println(a.getPv());
		System.out.println(b.getPv());
	}
	
	public static void Combat(Character a,Character b) {
		Combat_attaque(a,b);
		Combat_attaque(b,a);
	}
}
