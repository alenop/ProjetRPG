package system;

import character.Character;
import character.Hero;
import character.Monstre;

public class SystemsView {
	
	
	public static void main(String[] args) {
		Character hero= new Hero();
		Character monstre = new Monstre();
		System.out.println(hero.getPv()+"//"+monstre.getPv());
		Systems.Combat(hero,monstre,"defense");
		System.out.println(hero.getPv()+"//"+monstre.getPv());
		Systems.Combat(hero,monstre,"attaque");
		System.out.println(hero.getPv()+"//"+monstre.getPv());
		Hero thehero = (Hero)hero;
		System.out.println(thehero.getInventaire().toString());
		
	}
}
