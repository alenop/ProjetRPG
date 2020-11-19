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
import system.Quest;

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

		// Créer la map à partir du fichier Tiled
		getGameWorld().setLevelFromMap(hero.getCurrentMap());
		// getGameWorld().addWorldListener(getPhysicsWorld());
		// getPhysicsWorld().onEntityRemoved();

		// paramètres de jeu pour tester
		// Item b = new Arme(40, "Hache");
		// hero.getInventaire()[0] = b;
		// hero.getInventaire()[1] = new Armure(0, "Armure");
		//Item a = new Armure(0, "Armure", "yo");
		// hero.equip(b);
		//hero.equip(a);

		initMap("mapMaison.json", new Point2D(1472, 448));
		initMap("map5.json", new Point2D(0, 0));

		System.out.println(ListeMaps.get(hero.getCurrentMap()));
		// quest = new Quest("kill souris",50000,Monstres.Souris,1);
		// hero.setCurrentquest(quest);
		createPortal("map5.json", new Point2D(0, 128), "mapMaison.json");
		createPortal("map5.json", new Point2D(0, 256), "map5.json");
		createCoffre("map5.json", new Point2D(192, 256), new Coffre(new Arme(40, "Hache", "Hache.png")));
		createCoffre("map5.json", new Point2D(256, 256), new Coffre(new Arme(15, "balai de ménagère", "Balai.png")));
		createCoffre("map5.json", new Point2D(320, 256), new Coffre(new Arme(30, "Epée", "Epee.png")));
		createPortal("mapMaison.json", new Point2D(960, 1280), "map5.json");
		createPortal("mapMaison.json", new Point2D(576, 384), "map5.json");
		createPortal("mapMaison.json", new Point2D(1024, 1280), "map5.json");
		createMonstre("map5.json", new Monstre("souris", 20, 20, 100), new Point2D(128 + 64, 0));

		getGameWorld().spawn("pnj", new Point2D(1024, 960));
		PNJ pnj1 = new PNJ("pnj1", 192, 128, "Tue la souris");
		PNJList.init();
		PNJList.pnjList.put(new Point2D(1024, 960), pnj1);
		// test pour quête monstre
//		for (int i=0;i<11;i++) {
//		createMonstre("map5.json",new Monstre("souris",10,20,100),new Point2D(128+i*64,0));
//		}

		// Créer le joueur
		player = Entities.builder().at(ListeMaps.get(hero.getCurrentMap()).getPositionHero())
				.viewFromTexture("HerosFace.png").with(new PlayerComponent()).type(EntityType.PLAYER)
				.buildAndAttach(getGameWorld());

		// Lie la camera au personnage

		getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

		playerComponent = player.getComponent(PlayerComponent.class);

		DisplayInventaire.createInventaire();
		DisplayEquipment.createEquipment();
		hero.addItemInventaire(new Arme(40, "Hache", "Hache.png"));
		hero.addItemInventaire(new Arme(40, "Epée", "Epee.png"));
		hero.addItemInventaire(new Arme(40, "balai de ménagère", "Balai.png"));
		hero.addItemInventaire(new Arme(40, "balai de ménagère", "Balai.png"));
	}

	@Override
	protected void initInput() {
		// Initialise les commandes de l'utilisateur
		Input input = getInput();

		input.addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {
				playerComponent.moveRight();
				player.setViewFromTexture("HerosDroiteMV.gif");
			}
		}, KeyCode.D);

		input.addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {
				playerComponent.moveLeft();
				player.setViewFromTexture("HerosGaucheMV.gif");
			}
		}, KeyCode.Q);

		input.addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {
				playerComponent.moveUp();
				player.setViewFromTexture("HerosDosMV.gif");
			}
		}, KeyCode.Z);

		input.addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {
				playerComponent.moveDown();
				player.setViewFromTexture("HerosMV.gif");
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