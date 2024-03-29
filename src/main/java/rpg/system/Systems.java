package rpg.system;



import rpg.RPGApp;
import rpg.data.character.Character;
import rpg.data.character.Hero;
import rpg.data.character.Monstre;
import rpg.data.character.State;
import rpg.eventhandler.CombatEventHandler;


public abstract class Systems {
	public static void Combat_attaque(Character a,int c) {
		int result = (c - a.getDef());
		int result_final = a.getPv() - Math.max(1, result);
		if (result_final <= 0) {
			a.setState(State.dead);
			a.setPv(0);
		} else {
			a.setPv(result_final);
		}
	}
	public static void Combat_attaque(Character b, Character a) {
		int result = (b.getAtk() - a.getDef());
		int result_final = a.getPv() - Math.max(1, result);
		if (result_final <= 0) {
			a.setState(State.dead);
			a.setPv(0);
		} else {
			a.setPv(result_final);
		}

	}
	public static void CombatSkill(Hero a, Monstre b, CombatEventHandler combatEventHandler) throws Exception {
		if (a.getState() == State.dead || b.getState() == State.dead) {
			throw (new Exception("Erreur l'un des 2 Character est mort"));

		}
		combatEventHandler.useSkill();
			if (b.getState() == State.alive) {
				Combat_attaque(b, a);
			}
		
			else if (b.getState() == State.dead) {		
			RPGApp.hero.gainExp(b.getGive_experience());
			if (RPGApp.hero.getCurrentquest()!=null) {
			if (RPGApp.hero.getCurrentquest().getTypeMonstre() == b.getTypeMonstre()) {
				RPGApp.hero.getCurrentquest().setKill(RPGApp.hero.getCurrentquest().getKill() + 1);
			}
			
			}
		}
	}
	public static void Combat(Hero a, Monstre b, String x) throws Exception {
		if (a.getState() == State.dead || b.getState() == State.dead) {
			throw (new Exception("Erreur l'un des 2 Character est mort"));

		}
		if (x.equals("Skill")) {
			
		}
		else if (x.equals("attaque")) {
			if (a instanceof Hero) {
				Hero c = (Hero) a;
				if (c.getEquipement().get("Arme") != null) {
					for (int i = 0; i < b.getWeaknesses().length; i++) {
						if (c.getEquipement().get("Arme").getName().equals(b.getWeaknesses()[i])) {
							Combat_attaque(b, a.getAtk()*4);
							break;
						}		
					}Combat_attaque(a,b);
				}else {
					Combat_attaque(a,b);
				}
			}
			if (b instanceof Monstre && b.getState() == State.alive) {
				Combat_attaque(b, a);
			}
		}

		else if (x.equals("d�fense")) {
			int c = a.getDef();
			a.setDef(a.getDef() * 2);
			if (b instanceof Monstre && b.getState() == State.alive) {
				Combat_attaque(b, a);
			}
			a.setDef(c);
		}
		
		
		if (b.getState() == State.dead) {		
			RPGApp.hero.gainExp(b.getGive_experience());
			if (RPGApp.hero.getCurrentquest()!=null) {
			if (RPGApp.hero.getCurrentquest().getTypeMonstre() == b.getTypeMonstre()) {
				RPGApp.hero.getCurrentquest().setKill(RPGApp.hero.getCurrentquest().getKill() + 1);
			}
			
			}
		}
	}
}
