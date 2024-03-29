package rpg.view;

import java.util.Map;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import rpg.RPGApp;
import rpg.control.MusicComponent;
import rpg.control.PlayerComponent;
import rpg.data.character.Monstre;
import rpg.data.character.State;
import rpg.data.elementInteractifs.Chest;
import rpg.data.elementInteractifs.Indice;
import rpg.data.elementInteractifs.PNJ;

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
		MusicComponent.musicPlay(map);
		if (abcd != null) {
			FXGL.getApp().getGameScene().addGameView(abcd);
		}
		if (RPGApp.ListeMaps.get(map) != null) {
			for (Map.Entry<Point2D, String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("portal", i.getKey());

			}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				if(i.getValue().isUnique() && RPGApp.hero.finishQuest(i.getValue().getQuest()) ) {
				
				}
				else {	

					i.getValue().fullLife();
					i.getValue().setState(State.alive);
					FXGL.getApp().getGameWorld().spawn("monstre", i.getKey()).setProperty("data",i.getValue());
				}
			}
			for (Map.Entry<Point2D, PNJ> i : RPGApp.ListeMaps.get(map).getPNJList().entrySet()) {
				String nom = i.getValue().getName();
				FXGL.getApp().getGameWorld().spawn("pnj", i.getKey()).setViewFromTexture(nom+"_Face.png");
			}
			for (Entry<Point2D, Chest> i : RPGApp.ListeMaps.get(map).getCoffreList().entrySet()) {
				String a="Coffre_";
				if (i.getValue().getLoot()==null) {a+="Ouvert.png";}else {a+="Ferme.png";}
				FXGL.getApp().getGameWorld().spawn("coffre",i.getKey()).setViewFromTexture(a);
			}
			for (Entry<Point2D, Indice> i : RPGApp.ListeMaps.get(map).getIndiceList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("indice", i.getKey());
			}
		}

	}
public static void chargeMapProgress(String map,Point2D pos,String mapOf) {
			Point2D m =RPGApp.ListeMaps.get(map).getReturnPortalList().get(mapOf+pos.toString());
			String mp=map;
			chargeMap(mp,m);

	}
public static void chargeMap(String map,Point2D pos) {
		RPGApp.ListeMaps.get(map).MonstreMove=true;
		RPGApp.ListeMaps.get(map).notif=null;
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
		PlayerComponent.position.setValue(pos);
		DisplayInventaire.createInventaire();
		DisplayEquipment.createEquipment();
		DisplayQuete.createQuete();
		DisplayHero.begin();

		if (RPGApp.ListeMaps.get(map) != null) {
			for (Map.Entry<Point2D, String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("portal", i.getKey());

			}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				if(i.getValue().isUnique() && RPGApp.hero.getCurrentquest().getName().equals(i.getValue().getQuest())==false) {
			
				}
				else if(i.getValue().getState()==State.dead && i.getValue().isUnique()) {
					
				}
				else {
			
					i.getValue().fullLife();
					i.getValue().setState(State.alive);
					FXGL.getApp().getGameWorld().spawn("monstre", i.getKey()).setProperty("data",i.getValue());
				}
			}	
			for (Map.Entry<Point2D, PNJ> i : RPGApp.ListeMaps.get(map).getPNJList().entrySet()) {
				String nom = i.getValue().getName();
				FXGL.getApp().getGameWorld().spawn("pnj", i.getKey()).setViewFromTexture(nom+"_Face.png");
			}
			for (Entry<Point2D, Chest> i : RPGApp.ListeMaps.get(map).getCoffreList().entrySet()) {
				String a="Coffre_";
				if (i.getValue().getLoot()==null) {
					a+="Ouvert.png";
					}
				else {
					a+="Ferme.png";
					}
				FXGL.getApp().getGameWorld().spawn("coffre",i.getKey()).setViewFromTexture(a);
			}
			for (Entry<Point2D, Indice> i : RPGApp.ListeMaps.get(map).getIndiceList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("indice", i.getKey());
			}
		}

	}
}
