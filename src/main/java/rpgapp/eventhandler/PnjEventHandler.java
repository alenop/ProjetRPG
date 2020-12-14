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
	private Button a;
	private PNJ pnj;
	private EntityView pnjview;
	public  PnjEventHandler(Button a,PNJ pnj,EntityView pnjview) {
		this.a=a;
		this.pnj=pnj;
		this.pnjview=pnjview;
	}
	
	@Override
	public void handle(ActionEvent ActionEvent) {
		if (pnj.getgiveQuest().equals(a.getText())) {
			RPGApp.hero.setCurrentquest(pnj.getQuest());
		}else if (pnj.getListChat().get(a.getText())!=null) {
			DisplayPNJ.dialogue2(pnjview,pnj,a.getText());
		}
	}}

