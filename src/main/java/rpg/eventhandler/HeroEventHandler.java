package rpg.eventhandler;

import com.almasb.fxgl.entity.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import rpg.RPGApp;
import rpg.data.character.Heal;
import rpg.data.character.Skill;
import rpg.view.DisplayHero;

public class HeroEventHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		int f=0;
		ComboBox combo = ((ComboBox)event.getSource());
		for (Skill i : RPGApp.hero.getSkills()) {
			if (i.canInFight()==false) {
				if(combo.getSelectionModel().getSelectedIndex()==f) {
					((Heal) i).effect();
					((Entity) combo.getUserData()).removeFromWorld();
					DisplayHero.updateStats(DisplayHero.getStatusWindow());
					DisplayHero.updatebarres(DisplayHero.getStatusWindow());
					return;
				}
				f++;
			}
		}
	}

}
