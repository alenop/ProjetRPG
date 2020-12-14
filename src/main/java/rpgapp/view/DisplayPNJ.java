package rpgapp.view;

import java.util.HashMap;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import rpgapp.RPGApp;
import rpgapp.data.character.Monstres;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.eventhandler.PnjEventHandler;
import rpgapp.system.Quest;

public class DisplayPNJ extends DisplayBasic {
	public static void init(PNJ pnj) {
		Entity pnjentity = Entities.builder()
                .viewFromTexture(pnj.getImage())
                .build();
		EntityView pnjview = pnjentity.getView();
		if (RPGApp.hero.getFinishQuests().contains(pnj.getQuest().getName())) {
			DisplayPNJ.dialogue2(pnjview, pnj,"finish");
		}else if(RPGApp.hero.getCurrentquest()!=null && RPGApp.hero.getCurrentquest()==pnj.getQuest() ) {
			DisplayPNJ.dialogue2(pnjview, pnj,"en cours");
			}
		else {
		DisplayPNJ.dialogue2(pnjview,pnj,"begin");
		}
	}
	public static void dialogue2(EntityView pnjview, PNJ pnj,String étape) {
		Button[]listButton;
		HashMap<String, String[]> chat =pnj.getListChat().get(étape);
		String[] textButton =chat.get("answers");
		listButton = new Button[textButton.length];
			
			for (int i=0;i<textButton.length;i++) {
				listButton[i] =  new Button(textButton[i]);
				listButton[i].setOnAction(new PnjEventHandler(listButton[i],pnj,pnjview));
			}
		FXGL.getApp().getDisplay().showBox(chat.get("message")[0], pnjview, listButton);
	}
}
