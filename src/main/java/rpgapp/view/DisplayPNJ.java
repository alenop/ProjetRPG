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
		Entity item = Entities.builder()
                .viewFromTexture(pnj.getImage())
                .build();
		EntityView an = item.getView();
		DisplayPNJ.dialogue2(an,pnj,"begin");
	}
	public static void dialogue2(EntityView an, PNJ pnj,String étape) {
		Button[]av;
		HashMap<String, String[]> step =pnj.getTraceConversation().get(étape);
		String[] textButton =step.get("answers");
		av = new Button[textButton.length];
			
			for (int i=0;i<textButton.length;i++) {
				av[i] =  new Button(textButton[i]);
				av[i].setOnAction(new PnjEventHandler(av[i],pnj,an));
			}
		FXGL.getApp().getDisplay().showBox(step.get("message")[0], an, av);
	}
}
