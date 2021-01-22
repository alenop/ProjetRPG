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
import rpgapp.data.character.Attack;
import rpgapp.data.character.Boost;
import rpgapp.data.character.Courage;
import rpgapp.data.character.Heal;
import rpgapp.data.character.Monstre;
import rpgapp.data.character.Skill;
import rpgapp.data.character.SkillOutFight;
import rpgapp.data.character.SkillType;
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
	private int nbSkill;

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

		if (choix.equals("défense") || choix.equals("attaque") || choix.equals("skills")) {
			try {
				if (nb_tour == 2) {
					monstre.setAtk(monstre.getAtk() * 2);
				}
				if(choix.equals("skills")) {
					this.nbSkill = ((ComboBox) arg0.getSource()).getSelectionModel().getSelectedIndex();
					((Entity)((ComboBox) arg0.getSource()).getUserData()).removeFromWorld();
					System.out.println(nbSkill);
					Systems.CombatSkill(RPGApp.hero, monstre,this);
				}else {
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
			if ((choix.equals("défense")|| (choix.equals("skills") && RPGApp.hero.getSkills().get(nbSkill).getType()!=SkillType.attaque )) && RPGApp.hero.getState() == State.alive) {
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
				MusicComponent.soundPlay("shield");

			}
		}
		if (choix.equals("attaque") || RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.attaque) {
			MusicComponent.soundPlay("attack");
			if (monstre.getState() == State.dead) {
				FXGL.getApp().getGameWorld().getEntitiesAt(posMonstre).get(0).setViewFromTexture(monstre.getTypeMonstre().name()+"RatMort.png");
				RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().remove(posMonstre);
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 0);
				RPGApp.hero.getCurrentquest().upNbCibles();
				DisplayQuete.updateQuete();
				MusicComponent.soundPlay("win");
				MusicComponent.musicPlay("victory");
			} else if(RPGApp.hero.getState()==State.alive) {
				DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
			}}
//		} else if (choix.equals("skills")) {
//			int index = ((ComboBox) arg0.getSource()).getSelectionModel().getSelectedIndex();
//			((Entity)((ComboBox) arg0.getSource()).getUserData()).removeFromWorld();
//			int nbSkill = index ;
//				System.out.println("index :"+index+"nb tour :"+nb_tour);
//				
//				if (RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.heal) {
//					Heal skill = ((Heal)RPGApp.hero.getSkills().get(nbSkill));
//					skill.effect();
//				} else if (RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.attaque) {
//					Attack skill = (Attack) RPGApp.hero.getSkills().get(nbSkill);
//					skill.effect(monstre);
//				} else if (RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.Boost) {
//					Boost skill = ((Boost)RPGApp.hero.getSkills().get(nbSkill));
//					skill.effect(nb_tour);
//				}
//				Systems.Combat_attaque(RPGApp.hero, monstre.getAtk());
//				if (monstre.getState() == State.dead) {
//					FXGL.getApp().getGameWorld().getEntitiesAt(posMonstre).get(0).setViewFromTexture(// monstre.getTypeMonstre().name()+
//							"RatMort.png");
//					DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 0);
//					MusicComponent.soundPlay("win");
//					MusicComponent.musicPlay("victory");
//				} else if (RPGApp.hero.getState() == State.alive) {
//					DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, nb_tour + 1);
//				}
//			
//		}
		
		else if(choix.equals("fuir") || choix.equals("partir")) {
			RPGApp.move=true;
			RPGApp.hero.resetTemporaryBonus();
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
			RPGApp.hero.resetTemporaryBonus();
			DisplayCombat.mode_combat2(viewcombat, monstre, posMonstre, 1);
			MusicComponent.musicPlay("battle");
		}
	for (Boost i : RPGApp.hero.getBoosts()) {
		if(i.getFinalTurn()==nb_tour) {
			i.deleteBonus();
		}
		}
	}
	public void useSkill() {
		if (RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.heal) {
			Heal skill = ((Heal)RPGApp.hero.getSkills().get(nbSkill));
			skill.effect();
		} else if (RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.attaque) {
			Attack skill = (Attack) RPGApp.hero.getSkills().get(nbSkill);
			skill.effect(monstre);
		} else if (RPGApp.hero.getSkills().get(nbSkill).getType()==SkillType.Boost) {
			Boost skill = ((Boost)RPGApp.hero.getSkills().get(nbSkill));
			skill.effect(nb_tour);
		}
	}

}
