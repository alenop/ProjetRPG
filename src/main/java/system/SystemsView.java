package system;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import character.Character;
import character.Etat;
import character.Hero;
import character.MonsterList;
import character.Monstre;
import item.Arme;
import item.Armure;
import item.Item;

public class SystemsView {
	
	
	public static void main(String[] args) {
		Item a = new Arme(30,"Epée","yo");
		Item b = new Arme(40,"Hache","yo");
		Item c = new Arme(15,"balai de ménagère","yo");
		
		Hero hero= new Hero("ian");
		Monstre monstre = new Monstre("Monster",10,20,100);
		System.out.println(hero.getAtk());
		hero.getInventaire()[1]=new Armure(0,"armure","yo");
		String [] liste = {"rien","Hache","Epée","balai de ménagère"};
		//liste= [1:"Hache",2:"Epée",3:"balai de ménagère"];
		System.out.println("vous avez le choix entre une Hache:1, une Epée:2 ou 3:un balai de ménagère");
		Scanner scan = new Scanner(System.in);
		int u = scan.nextInt();
		Item sol;
		if (liste[u]==a.getNom()) {
			sol=a;
		}else if(liste[u]==b.getNom()) {
			sol=b;
		}
		else {
			sol=c;
		}
		hero.getInventaire()[0]=sol;
		System.out.println(hero.getInventaire()[0].getNom());
		//hero.equip();
//		try {
//			Systems.Combat(hero,monstre,"defense");
//		}catch(Exception e) {
//			
//		}
//		System.out.println(hero.getPv()+"//"+monstre.getPv());
//		try {
//			Systems.Combat(hero,monstre,"attaque");
//		}catch(Exception e) {
//			
//		}
//		System.out.println(hero.getPv()+"//"+monstre.getPv());
		
		System.out.println(hero.getEtat()+"//"+monstre.getEtat());
		
		
	}

	public static void fight(Hero hero,Monstre monstre) {
		while(hero.getEtat()==Etat.vivant && monstre.getEtat()==Etat.vivant) {
			try {
				Systems.Combat(hero,monstre,"attaque");
			}catch(Exception e) {
				
			}
			System.out.println(hero.getPv()+"//"+monstre.getPv());
		}
	}
}
