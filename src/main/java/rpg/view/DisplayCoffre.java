package rpg.view;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import rpg.RPGApp;
import rpg.data.elementInteractifs.Chest;
import rpg.eventhandler.CoffreHandler;

public class DisplayCoffre extends DisplayBasic {
	public static void trouveCoffre(Point2D newPosition) {
		Chest a = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getCoffreList().get(newPosition);
		if (a.getLoot()!=null) {
		Entity item = Entities.builder()
                .viewFromTexture(a.getLoot().getImage())
                .build();
		EntityView an = item.getView();
                
		DisplayCoffre.trouveCoffre(an,a,newPosition);
	}
		else {
			FXGL.getApp().getDisplay().showMessageBox("tu as d�ja ouvert ce coffre");
			}
	}
	public static void trouveCoffre(EntityView an,Chest a,Point2D c) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		av[1].setOnAction(new CoffreHandler(a,c));
		FXGL.getApp().getDisplay().showBox("Tu as trouve "+a.getLoot().getName()+" dans ce coffre veux tu l'�quiper ?", an, av);
	}
}
