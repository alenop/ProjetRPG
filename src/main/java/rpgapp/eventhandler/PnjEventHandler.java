package rpgapp.eventhandler;

import javafx.scene.control.Button;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import rpgapp.RPGApp;
import rpgapp.data.character.Monstres;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.system.Quest;
import rpgapp.view.DisplayPNJ;

public class PnjEventHandler implements EventHandler<ActionEvent> {
	private Button button;
	private PNJ pnj;
	public  PnjEventHandler(Button button,PNJ pnj) {
		this.button=button;
		this.pnj=pnj;
	}
	
	@Override
	public void handle(ActionEvent ActionEvent) {
		if (pnj.getgiveQuest().equals(button.getText())) {
			RPGApp.hero.setCurrentquest(pnj.getQuest());
			RPGApp.dialogBox.removeFromWorld();
			RPGApp.dialogBox=null;
			RPGApp.move=true;
		}else if (pnj.getListChat().get(button.getText())!=null) {
			DisplayPNJ.dialogue2(pnj,button.getText());
		}else {
			RPGApp.dialogBox.removeFromWorld();
			RPGApp.dialogBox=null;
			RPGApp.move=true;
		}
	}}

