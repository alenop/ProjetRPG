package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;

import javafx.event.ActionEvent;
import rpgapp.RPGApp;
import javafx.event.EventHandler;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;

public class GameOverHandler implements EventHandler<ActionEvent> {
	
		@Override
		public void handle(ActionEvent ActionEvent) {
			
			FXGL.exit();
			
			
		}

}
