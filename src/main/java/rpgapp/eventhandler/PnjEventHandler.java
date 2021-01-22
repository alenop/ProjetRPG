package rpgapp.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.QuestComponent;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.system.Quest;
import rpgapp.view.DisplayPNJ;
import rpgapp.view.DisplayQuete;

public class PnjEventHandler implements EventHandler<ActionEvent> {
	private Button button;
	private PNJ pnj;
	public  PnjEventHandler(Button button,PNJ pnj) {
		this.button=button;
		this.pnj=pnj;
	}
	
	@Override
	public void handle(ActionEvent ActionEvent) {
		Quest q = RPGApp.hero.getCurrentquest();
		if (pnj.getgiveQuest().equals(button.getText())) {
			if(q == null) {
				RPGApp.hero.setCurrentquest(pnj.getQuest());
			}
			else {
				if(q.getAction()=="Parler à" && q.getTypeCibles()==this.pnj.getName()) {
					q.upNbCibles();
					QuestComponent.verifQuest();
				}
			}
			DisplayQuete.updateQuete();
			RPGApp.dialogBox.removeFromWorld();
			MusicComponent.soundPlay("accept");
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

