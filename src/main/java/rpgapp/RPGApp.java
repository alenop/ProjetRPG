package rpgapp;

import java.util.HashMap;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Hero;
import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Arme;
import rpgapp.data.elementInteractifs.Armure;
import rpgapp.data.elementInteractifs.Coffre;

import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.data.elementInteractifs.PNJList;
import rpgapp.data.map.ModeleMap;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;
import rpgapp.view.DisplayMap;
import rpgapp.system.Quest;

public class RPGApp extends GameApplication {

	public static final int TILE_SIZE = 64;
	public static Hero hero = new Hero("ian");
	public static Quest quest;
	private Entity player;
	private PlayerComponent playerComponent;
	public static HashMap<String, ModeleMap> ListeMaps = new HashMap<String, ModeleMap>();
	public static Entity notif = null;

	@Override
	protected void initSettings(GameSettings settings) {
		// Initialise la fenetre
		settings.setWidth(10 * 64);
		settings.setHeight(10 * 64);
		settings.setTitle("Basic Game App");
		settings.setVersion("0.1");
		settings.setFullScreenAllowed(true);
		// other settings
	}

	@Override
	protected void initGame() {

		// Initialise le jeu
		hero.setCurrentMap("mapMaison.json");
		// AJoute la Factory
		getGameWorld().addEntityFactory(new RPGFactory());

		initMap("map5.json", new Point2D(0,0));
		initMap("mapMaison.json", new Point2D(1472, 448));
		initMap("mapCave.json", new Point2D(1280, 896));
		createPortal("mapCave.json", new Point2D(1280, 960), "mapMaison.json");
		createPortal("map5.json", new Point2D(1344, 448), "mapCave.json");
		createCoffre("mapMaison.json", new Point2D(768, 768), new Coffre(new Arme(40, "Hache", "Hache.png")));
		createCoffre("mapMaison.json", new Point2D(768+64, 768), new Coffre(new Arme(15, "balai de ménagère", "Balai.png")));
		createCoffre("mapMaison.json", new Point2D(768+128, 768), new Coffre(new Arme(30, "Epée", "Epee.png")));
		createPortal("mapMaison.json", new Point2D(960, 1280), "map5.json");
		createPortal("mapMaison.json", new Point2D(576, 384), "mapCave.json");
		createPortal("mapMaison.json", new Point2D(1024, 1280), "map5.json");
		createMonstre("mapCave.json", new Monstre("souris", 20, 20, 100), new Point2D(320, 704));
		createPNJ("mapMaison.json",new PNJ("pnj1", "Tue la souris"), new Point2D(1024,960));
	    getGameWorld().spawn("pnj", new Point2D(1024, 960));
//		PNJ pnj1 = new PNJ("pnj1", 192, 128, "Tue la souris");
//		PNJList.init();
//		PNJList.pnjList.put(new Point2D(1024, 960), pnj1);


		DisplayMap.chargeMap(hero.getCurrentMap(),"init");
		// Créer le joueur
		player = Entities.builder().at(ListeMaps.get(hero.getCurrentMap()).getPositionHero())
				.viewFromTexture("HerosFace.png").with(new PlayerComponent()).type(EntityType.PLAYER)
				.buildAndAttach(getGameWorld());

		// Lie la camera au personnage

		getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

		playerComponent = player.getComponent(PlayerComponent.class);
		
		hero.addItemInventaire(new Arme(40, "Hache", "Hache.png"));
		hero.addItemInventaire(new Arme(40, "Epée", "Epee.png"));
		hero.addItemInventaire(new Arme(40, "balai de ménagère", "Balai.png"));
		hero.addItemInventaire(new Arme(40, "balai de ménagère", "Balai.png"));
		DisplayInventaire.createInventaire();
		hero.equip(new Arme(40, "Hache", "Hache.png"));
		DisplayEquipment.createEquipment();
		
	}

	@Override
	protected void initInput() {
		// Initialise les commandes de l'utilisateur
		Input input = getInput();

		input.addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {
				
				playerComponent.moveRight();
			}
			
			protected void onActionEnd() {
				player.setViewFromTexture("HerosDroite.png");
			}
		}, KeyCode.D);

		input.addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {
				//player.setViewFromTexture("HerosGaucheMV.gif");
				playerComponent.moveLeft();
			}
			
			protected void onActionEnd() {
				player.setViewFromTexture("HerosGauche.png");
			}
		}, KeyCode.Q);

		input.addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {
				//player.setViewFromTexture("HerosDosMV.gif");
				playerComponent.moveUp();
			}
			
			protected void onActionEnd() {
				player.setViewFromTexture("HerosDos.png");
			}
		}, KeyCode.Z);

		input.addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {
				//player.setViewFromTexture("HerosMV.gif");
				playerComponent.moveDown();
			}
			
			protected void onActionEnd() {
				player.setViewFromTexture("HerosFace.png");
			}
		}, KeyCode.S);

		input.addAction(new UserAction("see Inventory") {
			@Override
			protected void onAction() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					
				}
				if (DisplayInventaire.getInventory().getView().isVisible()) {
					DisplayInventaire.removeInventaire();
				}
				else {
					DisplayInventaire.afficheInventaire();					
				}
			}
		}, KeyCode.I);
		
		input.addAction(new UserAction("see Equipment") {
			@Override
			protected void onAction() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}
				if (DisplayEquipment.getEquipment().getView().isVisible()) {				
					DisplayEquipment.removeEquipment();
				}
				else {
					DisplayEquipment.afficheEquipment();				
				}
			}
		}, KeyCode.E);
	}

	public void createMonstre(String a, Monstre b, Point2D c) {

		if (ListeMaps.get(a).getMonsterList().get(c) == null) {
			ListeMaps.get(a).getMonsterList().put(c, b);
		}
	}
	public void createPNJ(String a, PNJ b, Point2D c) {
		
		if (ListeMaps.get(a).getPNJList().get(c) == null) {
			ListeMaps.get(a).getPNJList().put(c, b);
		}
	}

	public void createPortal(String a, Point2D b, String c) {

		if (ListeMaps.get(a).getPortalList().get(b) == null) {
			ListeMaps.get(a).getPortalList().put(b, c);
		}
	}

	public void createCoffre(String a, Point2D b, rpgapp.data.elementInteractifs.Coffre c) {

		if (ListeMaps.get(a).getCoffreList().get(b) == null) {
			ListeMaps.get(a).getCoffreList().put(b, c);
		}
	}

	public void createPortal2(String a, Point2D b, String c) {

		if (ListeMaps.get(a).getPortalList().get(b) == null) {
			getGameWorld().spawn("portal", b);
			ListeMaps.get(a).getPortalList().put(b, c);
		}
	}

	public void initMap(String a, Point2D b) {
		ModeleMap mapbase = new ModeleMap();
		mapbase.init();
		mapbase.setPositionHero(b);
		ListeMaps.put(a, mapbase);
	}


	public static void main(String[] args) {
		launch(args);
	}

}