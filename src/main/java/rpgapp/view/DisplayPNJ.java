package rpgapp.view;

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
import rpgapp.system.Quest;

public class DisplayPNJ extends DisplayBasic {
	public static void init(Point2D newPosition) {
		int i=0;
		Entity item = Entities.builder()
                .viewFromTexture("PnjFace.png")
                .build();
		EntityView an = item.getView();
		DisplayPNJ.dialogue(an,i);
	}
	public static void dialogue(EntityView an, int i) {
		String[] text = new String[7];
		text[0] = "Pourquoi ?";
		text[1] = "oui papa";
		text[2] = "Va tuer la souris";
		text[3] = "oui papa";
		text[4] = "oui papa";
		text[5] = "Elle mange tout mon fromage dépêche toi !";
		Button[]av;
		if (text[i].equals(text[i+1])) {
			av = new Button[1];
		}else {
			av = new Button[2];
			av[1] = new Button(text[i]);
			av[1].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent ActionEvent) {
					if (av[1].getText().equals("oui papa")) {
						RPGApp.hero.setCurrentquest(new Quest("kill souris",5000,Monstres.Souris,1));
						FXGL.getApp().getDisplay().showMessageBox("elle est dans le jardin !");
					}else {
					dialogue(an, i + 3);
					}
		}});}
		
		
		av[0] = new Button(text[i+1]);
		
		av[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				if (av[0].getText().equals("oui papa")) {
				RPGApp.hero.setCurrentquest(new Quest("kill souris",5000,Monstres.Souris,1));
				FXGL.getApp().getDisplay().showMessageBox("elle est dans le jardin !");
				}
				//dialogue(an, i + 2);
			}
		});
		FXGL.getApp().getDisplay().showBox(text[i + 2], an, av);
	}
}
