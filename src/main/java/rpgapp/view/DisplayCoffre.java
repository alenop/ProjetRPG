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
import rpgapp.data.elementInteractifs.Coffre;

public class DisplayCoffre extends DisplayBasic {
	public static void trouveCoffre(Point2D newPosition) {
		Coffre a = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getCoffreList().get(newPosition);
		if (a.getContenu()!=null) {
		Entity item = Entities.builder()
                .viewFromTexture(a.getContenu().getImage())
				//.viewFrom
                .build();
		EntityView an = item.getView();
                
		DisplayCoffre.trouveCoffre(an,a,newPosition);
	}
		else {
			FXGL.getApp().getDisplay().showMessageBox("tu as déja ouvert ce coffre");
			}
	}
	public static void trouveCoffre(EntityView an,Coffre a,Point2D c) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		av[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				
				FXGL.getApp().getGameWorld().getEntitiesAt(c).get(0).setViewFromTexture("Coffre_Ferme.png");
				
			}
		});
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				RPGApp.hero.equip(a.getContenu());
				FXGL.getApp().getGameWorld().getEntitiesAt(c).get(0).setViewFromTexture("Coffre_Ouvert.png");
				
				a.setContenu(null);
				
			}
		});
		FXGL.getApp().getDisplay().showBox("tu as trouve "+a.getContenu().getNom()+" dans ce coffre veux tu l'équiper ?", an, av);
	}
}
