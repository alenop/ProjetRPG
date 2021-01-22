package rpgapp.eventhandler;

import java.util.List;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.QuestComponent;
import rpgapp.data.character.State;
import rpgapp.data.character.Monstre;
import rpgapp.data.character.SkillOutFight;
import rpgapp.data.character.Skill_InFight;
import rpgapp.system.Systems;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayCombat;
import rpgapp.view.DisplayQuete;

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

		if (choix.equals("d�fense") || choix.equals("attaque") || choix.equals("Skills")) {
			try {
				if (nb_tour == 2) {
					monstre.setAtk(monstre.getAtk() * 2);
				}
				if(choix.equals("Skills")==false) {
				Systems.Combat(RPGApp.hero, monstre, choix);
				}

				if (nb_tour == 2) {
					monstre.setAtk(monstre.getAtk() / 2);
				}
				if (RPGApp.hero.getState() == State.dead) {
					DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, -1);
					MusicComponent.musicPlay("gameover");

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (choix.equals("d�fense") && RPGApp.hero.getState() == State.alive) {
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
				MusicComponent.soundPlay("shield");

			}
		}
		if (choix.equals("attaque")) {
			MusicComponent.soundPlay("attack");
			if (monstre.getState() == State.dead) {
				if(RPGApp.hero.getCurrentquest().getName()=="Une Invasion de Rat ? Partie III:") {
					FXGL.getApp().getGameWorld().getEntitiesAt(posMonstre).get(0).setViewFromTexture("vide.png");
				}
				else {
					FXGL.getApp().getGameWorld().getEntitiesAt(posMonstre).get(0).setViewFromTexture(monstre.getTypeMonstre().name()+"Mort.png");
				}
				
				RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().remove(posMonstre);
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 0);
				RPGApp.hero.getCurrentquest().upNbCibles();
				DisplayQuete.updateQuete();
				MusicComponent.soundPlay("win");
				MusicComponent.musicPlay("victory");
			} else if(RPGApp.hero.getState()==State.alive) {
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
			}
		} else if (choix.equals("skills")) {
			int index = ((ComboBox) arg0.getSource()).getSelectionModel().getSelectedIndex();
			((Entity)((ComboBox) arg0.getSource()).getUserData()).removeFromWorld();
			int nbSkill = index ;
				System.out.println("index :"+index+"nb tour :"+nb_tour);
				
				if (RPGApp.hero.getSkills().get(nbSkill) instanceof SkillOutFight) {
					SkillOutFight skill = (SkillOutFight) RPGApp.hero.getSkills().get(nbSkill);
					skill.effect();
				} else {
					Skill_InFight skill = (Skill_InFight) RPGApp.hero.getSkills().get(nbSkill);
					skill.effect(monstre);
				}
				Systems.Combat_attaque(RPGApp.hero, monstre.getAtk());
				if (monstre.getState() == State.dead) {
					FXGL.getApp().getGameWorld().getEntitiesAt(posMonstre).get(0).setViewFromTexture(// monstre.getTypeMonstre().name()+
							"RatMort.png");
					DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 0);
					MusicComponent.soundPlay("win");
					MusicComponent.musicPlay("victory");
				} else if (RPGApp.hero.getState() == State.alive) {
					DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
				}
			
		}
		
		else if(choix.equals("fuir") || choix.equals("partir")) {
			RPGApp.move=true;
			FXGL.getApp().getGameWorld().removeEntity(viewcombat);
			MusicComponent.musicPlay(RPGApp.hero.getCurrentMap());
			if (choix.equals("partir")) {
				//RPGApp.hero.gainExp(RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().get(posMonstre).getGive_experience());
				if(RPGApp.hero.getCurrentquest()!=null) {
					QuestComponent.verifQuest();
			}}
			else if(choix.equals("fuir")) {
				MusicComponent.soundPlay("run");
			}
		} else if (choix.equals("retry")) {
			RPGApp.hero.fullLife();
			monstre.fullLife();
			DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 1);
			MusicComponent.musicPlay("battle");
		}
	}

}
