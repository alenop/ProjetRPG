package system;

import character.Character;
import character.Etat;
import character.Hero;
import character.Monstre;

public abstract class Systems {

	public static void Combat_attaque(Character b, Character a) {
		int result=(b.getAtk() - a.getDef());
		int result_final=a.getPv()-Math.max(1,result);
		if (result_final<=0) {
			a.setEtat(Etat.mort);
			a.setPv(0);
		}
		else {
			a.setPv(result_final);
		}
		
	}

	public static void Combat(Character a, Character b, String x) throws Exception {
		if (a.getEtat()==Etat.mort || b.getEtat()==Etat.mort) {
			throw (new Exception("Erreur l'un des 2 Character est mort"));
				
		}
		if (x.equals("attaque")) {
			if (a instanceof Hero) {
				Hero c = (Hero)a;
				for (int i=0;i<b.getFaiblesses().length;i++) {
				if (c.getInventaire()[0].getNom().equals(b.getFaiblesses()[i])) {
					int g =a.getAtk();
					a.setAtk(a.getAtk()*4);
					Combat_attaque(a, b);
					a.setAtk(g);
					break;
				}}
			}
				Combat_attaque(a, b);
			}
			
			
			if (b instanceof Monstre && b.getEtat()==Etat.vivant) {
					Combat_attaque(b, a);
			}
			
		else if(x.equals("defense")) {
			int c = a.getDef();
			a.setDef(a.getDef()*2);
			Combat_attaque(a, b);
			a.setDef(c);
		}
	}
}
