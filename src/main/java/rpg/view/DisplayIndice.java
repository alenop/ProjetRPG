package rpg.view;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import rpg.RPGApp;
import rpg.data.elementInteractifs.Indice;

public class DisplayIndice extends DisplayBasic {
	
	public static void trouveIndice(Point2D position) {
		Indice indice = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getIndiceList().get(position);
		Entity image = Entities.builder()
                .viewFromTexture(indice.getImage())
                .build();
		EntityView imageView = image.getView();        
		afficheIndice(imageView,indice,position);
	}
	public static void afficheIndice(EntityView imageView, Indice indice, Point2D position) {
		Button[] bouton = new Button[1];
		bouton[0] = new Button("Continuer");
		FXGL.getApp().getDisplay().showBox("Tu as trouv� "+indice.getName()+".", imageView, bouton);
	}

}
