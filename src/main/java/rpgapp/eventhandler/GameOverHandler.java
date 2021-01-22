package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GameOverHandler implements EventHandler<ActionEvent> {
	
		@Override
		public void handle(ActionEvent ActionEvent) {
			
			FXGL.exit();
			
			
		}

}
