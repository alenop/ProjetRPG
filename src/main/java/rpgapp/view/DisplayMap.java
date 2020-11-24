package rpgapp.view;

import java.util.Map;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import rpgapp.RPGApp;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Etat;
import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Coffre;
import rpgapp.data.elementInteractifs.PNJ;

public class DisplayMap extends DisplayBasic {
	public static void changeMap(String a, Point2D b) {
		

		String map = RPGApp.ListeMaps.get(a).getPortalList().get(b);
		chargeMap(map,"progress");
	}
		public static void chargeMap(String map, String init) {
			
			EntityView abcd = null;
			if(init!="init") {
			if (PlayerComponent.position.getEntity() != null) {
				abcd = PlayerComponent.position.getEntity().getView();
				RPGApp.hero.setView(abcd);
			}}
		FXGL.getApp().getGameWorld().setLevelFromMap(map);
		RPGApp.hero.setCurrentMap(map);
		if (abcd != null) {
			FXGL.getApp().getGameScene().addGameView(abcd);
		}
		if(init!="init") {
		PlayerComponent.position.setValue(RPGApp.ListeMaps.get(map).getPositionHero());
		DisplayInventaire.createInventaire();
		DisplayEquipment.createEquipment();
		}
		if (RPGApp.ListeMaps.get(map) != null) {
			for (Map.Entry<Point2D, String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("portal", i.getKey());

			}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("monstre", i.getKey());
				i.getValue().fullLife();
				i.getValue().setEtat(Etat.vivant);
			}
			for (Map.Entry<Point2D, PNJ> i : RPGApp.ListeMaps.get(map).getPNJList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("pnj", i.getKey());
			}
			for (Entry<Point2D, Coffre> i : RPGApp.ListeMaps.get(map).getCoffreList().entrySet()) {
				String a="Coffre_";
				if (i.getValue().getContenu()==null) {a+="Ouvert.png";}else {a+="Ferme.png";}
				FXGL.getApp().getGameWorld().spawn("coffre",i.getKey()).setViewFromTexture(a);
			}
		}

	}
}
