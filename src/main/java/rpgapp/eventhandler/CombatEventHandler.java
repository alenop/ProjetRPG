package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.data.character.State;
import rpgapp.data.character.Monstre;
import rpgapp.system.Systems;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayCombat;

public class CombatEventHandler extends DisplayBasic implements EventHandler<ActionEvent> {
	private Monstre monstre;
	private Point2D posMonstre;
	private int nb_tour;
	private Entity viewcombat;
	private String choix;

	public CombatEventHandler(Monstre monster, int nb_tour, Entity viewcombat, Point2D posMonstre, String choix) {
		this.monstre = monster;
		this.nb_tour = nb_tour;
		this.posMonstre = posMonstre;
		this.viewcombat = viewcombat;
		this.choix = choix;
	}

	public CombatEventHandler(String choix, Entity viewcombat) {
		this.choix = choix;
		this.viewcombat = viewcombat;
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		if (choix.equals("défense")|| choix.equals("attaque")) {
		try {
			if (nb_tour == 2) {
				monstre.setAtk(monstre.getAtk() * 2);
			}
			Systems.Combat(RPGApp.hero, monstre, choix);
			
			if (nb_tour == 2) {
				monstre.setAtk(monstre.getAtk() / 2);
			}
			if (RPGApp.hero.getState()==State.dead) {
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, -1);
				MusicComponent.musicPlay("gameover");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (choix.equals("défense") && RPGApp.hero.getState()==State.alive) {
			DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
			MusicComponent.soundPlay("shield");
		
		}
	}
		if(choix.equals("attaque")){
			MusicComponent.soundPlay("attack");
			if (monstre.getState() == State.dead) {
				FXGL.getApp().getGameWorld().getEntitiesAt(posMonstre).get(0).setViewFromTexture(monstre.getTypeMonstre().name()+"Mort.png");
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 0);
				MusicComponent.soundPlay("win");
				MusicComponent.musicPlay("victory");
			} else if(RPGApp.hero.getState()==State.alive) {
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
			}
		}else if(choix.equals("fuir") || choix.equals("partir")) {
			RPGApp.move=true;
			FXGL.getApp().getGameWorld().removeEntity(viewcombat);
			MusicComponent.musicPlay("cave");
			MusicComponent.soundPlay("run");
			if(choix.equals("partir")) {
				if(RPGApp.hero.getCurrentquest()!=null) {
				if (RPGApp.hero.getCurrentquest().verifQuest()) {
					String niveau="";
					int nvA=RPGApp.hero.getLevel();
					RPGApp.hero.gainExp(RPGApp.hero.getCurrentquest().getReward());
					if(RPGApp.hero.getLevel()>nvA) {
						niveau="Félicitations tu est maintenant niveau "+RPGApp.hero.getLevel();
					}
					String notif="Quête : "+RPGApp.hero.getCurrentquest().getName() +" accomplie !"+niveau;
					RPGApp.notif = DisplayBasic.createNotif(notif);
					FXGL.getApp().getGameWorld().addEntity(RPGApp.notif);
					MusicComponent.soundPlay("succes");
				}
			}}
		}else if(choix.equals("retry")) {
			RPGApp.hero.fullLife();
			monstre.fullLife();
			DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 1);
			MusicComponent.musicPlay("battle");
		}
	}

}
