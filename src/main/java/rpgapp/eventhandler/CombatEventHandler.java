package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import rpgapp.RPGApp;
import rpgapp.data.character.Etat;
import rpgapp.data.character.Monstre;
import rpgapp.system.Systems;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayCombat;

public class CombatEventHandler extends DisplayBasic implements EventHandler<ActionEvent> {
	private Monstre monstre;
	private Point2D point;
	private int nb_tour;
	private Entity viewcombat;
	private String choix;

	public CombatEventHandler(Monstre monstre, int nb_tour, Entity viewcombat, Point2D c, String choix) {
		this.monstre = monstre;
		this.nb_tour = nb_tour;
		this.point = c;
		this.viewcombat = viewcombat;
		this.choix = choix;
	}

	public CombatEventHandler(String choix, Entity viewcombat) {
		this.choix = choix;
		this.viewcombat = viewcombat;
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (choix.equals("défense")|| choix.equals("attaque")) {
		try {
			if (nb_tour == 2) {
				monstre.setAtk(monstre.getAtk() * 2);
			}
			Systems.Combat(RPGApp.hero, monstre, choix);
			
			if (nb_tour == 2) {
				monstre.setAtk(monstre.getAtk() / 2);
			}
			if (RPGApp.hero.getEtat()==Etat.mort) {
				DisplayCombat.mode_combat2(viewcombat, monstre, point, -1);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (choix.equals("défense") && RPGApp.hero.getEtat()==Etat.vivant) {
		DisplayCombat.mode_combat2(viewcombat, monstre, point, nb_tour + 1);
		}
	}
		if(choix.equals("attaque")){
			if (monstre.getEtat() == Etat.mort) {
				FXGL.getApp().getGameWorld().getEntitiesAt(point).get(0).setViewFromTexture("RatMort.png");
				DisplayCombat.mode_combat2(viewcombat, monstre, point, 0);
			} else if(RPGApp.hero.getEtat()==Etat.vivant) {
				DisplayCombat.mode_combat2(viewcombat, monstre, point, nb_tour + 1);
			}
		}else if(choix.equals("fuir") || choix.equals("partir")) {
			FXGL.getApp().getGameWorld().removeEntity(viewcombat);
			if(choix.equals("partir")) {
				if(RPGApp.hero.getCurrentquest()!=null) {
				if (RPGApp.hero.getCurrentquest().verifQuest()) {
					RPGApp.notif = DisplayBasic.createNotif("Quest succeed");
					FXGL.getApp().getGameWorld().addEntity(RPGApp.notif);
				}
			}}
		}else if(choix.equals("retry")) {
			RPGApp.hero.fullLife();
			monstre.fullLife();
			DisplayCombat.mode_combat2(viewcombat, monstre, point, 1);
		}
	}

}
