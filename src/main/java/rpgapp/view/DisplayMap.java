package rpgapp.view;

import java.util.Map;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.io.serialization.Bundle;

import javafx.geometry.Point2D;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.State;
import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Chest;
import rpgapp.data.elementInteractifs.PNJ;

public class DisplayMap extends DisplayBasic {
	public static void changeMap(String a, Point2D b) {
		

		String map = RPGApp.ListeMaps.get(a).getPortalList().get(b);
		MusicComponent.musicPlay(map);
		chargeMapProgress(map,b,a);
	}
		public static void chargeMapInit(String map) {
			
			EntityView abcd = null;
		
		
		FXGL.getApp().getGameWorld().setLevelFromMap(map);
		RPGApp.hero.setCurrentMap(map);
		if (abcd != null) {
			FXGL.getApp().getGameScene().addGameView(abcd);
		}
		if (RPGApp.ListeMaps.get(map) != null) {
			for (Map.Entry<Point2D, String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("portal", i.getKey());

			}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				if(i.getValue().isUnique() && i.getValue().getState()==State.dead ) {
				
				}
				else {	
				FXGL.getApp().getGameWorld().spawn("monstre", i.getKey());
				i.getValue().fullLife();
				i.getValue().setState(State.alive);
				System.out.println(i.getValue().isUnique());
				}
			}
			for (Map.Entry<Point2D, PNJ> i : RPGApp.ListeMaps.get(map).getPNJList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("pnj", i.getKey());
			}
			for (Entry<Point2D, Chest> i : RPGApp.ListeMaps.get(map).getCoffreList().entrySet()) {
				String a="Coffre_";
				if (i.getValue().getLoot()==null) {a+="Ouvert.png";}else {a+="Ferme.png";}
				FXGL.getApp().getGameWorld().spawn("coffre",i.getKey()).setViewFromTexture(a);
			}
		}

	}
public static void chargeMapProgress(String map,Point2D pos,String mapOf) {
			
			EntityView abcd = null;
			if (PlayerComponent.position.getEntity() != null) {
				abcd = PlayerComponent.position.getEntity().getView();
				RPGApp.hero.setView(abcd);
			}
		FXGL.getApp().getGameWorld().setLevelFromMap(map);
		RPGApp.hero.setCurrentMap(map);
		if (abcd != null) {
			FXGL.getApp().getGameScene().addGameView(abcd);
		}
		PlayerComponent.position.setValue(RPGApp.ListeMaps.get(map).getReturnPortalList().get(mapOf+pos.toString()));
		DisplayInventaire.createInventaire();
		DisplayEquipment.createEquipment();
		
		if (RPGApp.ListeMaps.get(map) != null) {
			for (Map.Entry<Point2D, String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("portal", i.getKey());

			}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				if(i.getValue().isUnique() && i.getValue().getState()==State.dead && RPGApp.hero.finishQuest("tuer le rat de la cave")) {
					
				}
				else {
					FXGL.getApp().getGameWorld().spawn("monstre", i.getKey());
					i.getValue().fullLife();
					i.getValue().setState(State.alive);
					}
			}
			for (Map.Entry<Point2D, PNJ> i : RPGApp.ListeMaps.get(map).getPNJList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("pnj", i.getKey());
			}
			for (Entry<Point2D, Chest> i : RPGApp.ListeMaps.get(map).getCoffreList().entrySet()) {
				String a="Coffre_";
				if (i.getValue().getLoot()==null) {a+="Ouvert.png";}else {a+="Ferme.png";}
				FXGL.getApp().getGameWorld().spawn("coffre",i.getKey()).setViewFromTexture(a);
			}
		}

	}
}
