package rpgapp;

import java.awt.Button;
import java.awt.Font;
import javafx.geometry.Insets;
import java.util.HashMap;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.saving.DataFile;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.FXGLScene;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Hero;
import rpgapp.data.character.Monstre;
import rpgapp.data.character.Monstres;
import rpgapp.data.character.SaveLoad;
import rpgapp.data.elementInteractifs.Arme;
import rpgapp.data.elementInteractifs.Armure;
import rpgapp.data.elementInteractifs.Chest;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.data.elementInteractifs.PNJList;
import rpgapp.data.map.ModeleMap;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;
import rpgapp.view.DisplayMap;
import rpgapp.system.Quest;
import rpgapp.Menu;

public class RPGApp extends GameApplication {

	public static final int TILE_SIZE = 64;
	public static Hero hero = new Hero("ian");
	public static Quest quest;
	public static Entity player;
	private PlayerComponent playerComponent;
	public static HashMap<String, ModeleMap> ListeMaps = new HashMap<String, ModeleMap>();
	public static Entity notif = null;
	public static Music test;

	@Override
	protected void initSettings(GameSettings settings) {
		// Initialise la fenetre
		settings.setWidth(15*64);
		settings.setHeight(10*64);
		settings.setTitle("The RPG");
		settings.setVersion("0.1");
		settings.setFullScreenAllowed(true);
		settings.setManualResizeEnabled(true);
		
		// other settings
	}

	@Override
	protected void initGame() {

		// Initialise le jeu
		hero.setCurrentMap("mapMaison.json");
		// AJoute la Factory
		getGameWorld().addEntityFactory(new RPGFactory());
		getAudioPlayer().setGlobalMusicVolume(0.15);
		getAudioPlayer().setGlobalSoundVolume(0.25);
		MusicComponent.musicPlay("House");
		initMap("map5.json", new Point2D(0,0));
		initMap("mapMaison.json", new Point2D(1472, 448));
		initMap("mapCave.json", new Point2D(1472, 896));
		initMap("mapJardin.json", new Point2D(1216,1536));
		initMap("mapPnj1.json", new Point2D(832,1088));
		initMap("mapPnj2.json", new Point2D(832,1088));
		initMap("mapPnj3.json", new Point2D(832,1088));
		//Portail de la cave
		createPortal("mapCave.json", new Point2D(1472, 960), "mapMaison.json",new Point2D(768, 448));
		createPortal("map5.json", new Point2D(1344, 448), "mapCave.json",new Point2D(1472, 960));
		//Portail de la maison
		createChest("mapMaison.json", new Point2D(896, 768), new Chest(new Arme(40, "Hache", "Hache.png")));
		createChest("mapMaison.json", new Point2D(896+64, 768), new Chest(new Arme(15, "balai de ménagère", "Balai.png")));
		createChest("mapMaison.json", new Point2D(896+128, 768), new Chest(new Arme(30, "Epée", "Epee.png")));
		createPortal("mapMaison.json", new Point2D(1152, 1216), "mapJardin.json", new Point2D(1216, 1536));
		createPortal("mapMaison.json", new Point2D(768, 384), "mapCave.json", new Point2D(1472, 896));
		createPortal("mapMaison.json", new Point2D(1216, 1216), "mapJardin.json", new Point2D(1216, 1536));
		//Portail du jardin
		createPortal("mapJardin.json", new Point2D(1216, 1472), "mapMaison.json", new Point2D(1216, 1152));
		createPortal("mapJardin.json", new Point2D(960, 2688), "mapPnj1.json", new Point2D(832, 1024));
		createPortal("mapJardin.json", new Point2D(2112, 2176), "mapPnj2.json", new Point2D(832, 1024));
		createPortal("mapJardin.json", new Point2D(2432, 2816), "mapPnj3.json", new Point2D(832, 1024));
		//Portail des maisons PNJ
		createPortal("mapPnj1.json", new Point2D(768, 1088), "mapJardin.json", new Point2D(960, 2752));
		createPortal("mapPnj1.json", new Point2D(832, 1088), "mapJardin.json", new Point2D(960, 2752));
		createPortal("mapPnj2.json", new Point2D(768, 1088), "mapJardin.json", new Point2D(2112, 2240));
		createPortal("mapPnj2.json", new Point2D(832, 1088), "mapJardin.json", new Point2D(2112, 2240));
		createPortal("mapPnj3.json", new Point2D(768, 1088), "mapJardin.json", new Point2D(2432, 2880));
		createPortal("mapPnj3.json", new Point2D(832, 1088), "mapJardin.json", new Point2D(2432, 2880));
		
		
		createMonster("mapCave.json", new Monstre("souris", 50, 40, 100,true), new Point2D(512, 704));
		
		String[] liste=new String[2];
		liste[0]="Une arme adaptée ?";
		liste[1]="Oui papa";
		HashMap<String,String[]> conversation = Chat(liste,"Un rat mange tout mon fromage dans la cave\n va t'équiper d'une arme adaptée et tue le");
		String[] liste2=new String[2];
		liste2[0]="Oui papa";
		liste2[1]="protéiné ?";
		HashMap<String,String[]> conversation2 = Chat(liste2,"Seule une arme adaptée te permettra de\n vaincre ce rat mangeur de fromage protéiné");
		String[] liste3=new String[1];
		liste3[0]="Oui papa";
		HashMap<String,String[]> conversation3 = Chat(liste3,"Va me tuer ce rat et arrête de poser tant de questions !");
		String[] liste4=new String[1];
		liste4[0]="Merci papa";
		HashMap<String,String[]> conversation4 = Chat(liste4,"Bravo fils!");
		String[] liste5=new String[1];
		liste5[0]="oui papa";
		HashMap<String,String[]> conversation5 = Chat(liste5,"Eh bien ce rat te pose des soucis ? n'oublie\n pas seule une arme adaptée te permettra\n de vaincre ce rat");
		HashMap<String,HashMap<String,String[]>> conversationComplète=new HashMap<String,HashMap<String,String[]>>();
		conversationComplète.put("begin", conversation);
		conversationComplète.put("Une arme adaptée ?", conversation2);
		conversationComplète.put("protéiné ?", conversation3);
		conversationComplète.put("finish", conversation4);
		conversationComplète.put("en cours", conversation5);
		PNJ père =new PNJ("père","PnjFace.png",conversationComplète,new Quest("tuer le rat de la cave",1000,Monstres.Rat,1),"Oui papa");
		createPNJ("mapMaison.json",père, new Point2D(1024,960));
//  	getGameWorld().spawn("pnj", new Point2D(1024, 960));
//		PNJ pnj1 = new PNJ("pnj1", 192, 128, "Tue la souris");
//		PNJList.init();
//		PNJList.pnjList.put(new Point2D(1024, 960), pnj1);


		DisplayMap.chargeMapInit(hero.getCurrentMap());
		// Créer le joueur
		player = Entities.builder().at(ListeMaps.get(hero.getCurrentMap()).getPositionHero())
				.viewFromTexture("HerosFace.png").with(new PlayerComponent()).type(EntityType.PLAYER)
				.buildAndAttach(getGameWorld());

		// Lie la camera au personnage

		getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

		playerComponent = player.getComponent(PlayerComponent.class);
		
		hero.addItemInventory(new Arme(40, "Hache", "Hache.png"));
		hero.addItemInventory(new Arme(40, "Epée", "Epee.png"));
		hero.addItemInventory(new Arme(40, "balai de ménagère", "Balai.png"));
		hero.addItemInventory(new Arme(40, "balai de ménagère", "Balai.png"));
		DisplayInventaire.createInventaire();
		hero.equip(new Arme(40, "Hache", "Hache.png"));
		hero.equip(new Armure(21, "t-shirt", "t-shirt.jpg"));
		DisplayEquipment.createEquipment();
		DisplayInventaire.createInventaire();
		
		System.out.println(getGameplay().getStats());		
		//FXGLScene
		//FXGLMenu;
		//new Menu(FXGL.getApp(), MenuType.GAME_MENU);
		System.out.println(getGameScene().getUINodes());
		//new Menu(FXGL.getApp(), MenuType.GAME_MENU);
		
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
					e.printStackTrace();
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
					e.printStackTrace();
				}
				if (DisplayEquipment.getEquipment().getView().isVisible()) {				
					DisplayEquipment.removeEquipment();
				}
				else {
					DisplayEquipment.afficheEquipment();				
				}
			}
		}, KeyCode.E);
		input.addAction(new UserAction("Save") {
			@Override
			protected void onAction() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("yo");
				SaveLoad.save(RPGApp.hero);
			}
		}, KeyCode.M);
		
		input.addAction(new UserAction("Load") {
			@Override
			protected void onAction() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("yo");
				System.out.println(RPGApp.hero.getInventory());
				for (Item i:RPGApp.hero.getInventory()) {
					if(i!=null) {
					DisplayInventaire.updateInventaire("remove", i, i.getPosition());
					}
				}
				for (Entry<String, Item> i:RPGApp.hero.getEquipement().entrySet()) {
					if(i.getValue()!=null) {
					DisplayEquipment.updateEquipment("remove", i.getValue());
					}
				}
				RPGApp.hero=SaveLoad.load();
				
				for (Item i:RPGApp.hero.getInventory()) {
					if(i!=null) {
					DisplayInventaire.updateInventaire("ajout", i, i.getPosition());
					}
				}
				for (Entry<String, Item> i:RPGApp.hero.getEquipement().entrySet()) {
					if(i.getValue()!=null) {
					DisplayEquipment.updateEquipment("ajout", i.getValue());
					}
				}
				System.out.println(RPGApp.hero.getInventory());
				
			}
		}, KeyCode.L);
	}

	public void createMonster(String map, Monstre monstre, Point2D posmonstre) {

		if (ListeMaps.get(map).getMonsterList().get(posmonstre) == null) {
			ListeMaps.get(map).getMonsterList().put(posmonstre, monstre);
		}
	}
	public void createPNJ(String map, PNJ pnj, Point2D pospnj) {
		System.out.println(ListeMaps.get(map).getPNJList());
		if (ListeMaps.get(map).getPNJList().get(pospnj) == null) {
			ListeMaps.get(map).getPNJList().put(pospnj, pnj);
		}
	}

	public void createPortal(String map, Point2D entry, String mapExit,Point2D exit) {

		if (ListeMaps.get(map).getPortalList().get(entry) == null) {
			ListeMaps.get(map).getPortalList().put(entry, mapExit);
		}
		if (ListeMaps.get(mapExit).getReturnPortalList().get(map+entry.toString()) == null) {
			ListeMaps.get(mapExit).getReturnPortalList().put(map+entry.toString(), exit);
		}
	}

	public void createChest(String map, Point2D pos, rpgapp.data.elementInteractifs.Chest chest) {

		if (ListeMaps.get(map).getCoffreList().get(pos) == null) {
			ListeMaps.get(map).getCoffreList().put(pos, chest);
		}
	}
	
	
	
//	public void musicStop() {
//		getAudioPlayer().stopMusic(music);
//	}

	public void initMap(String map, Point2D poshero) {
		ModeleMap mapbase = new ModeleMap();
		mapbase.init();
		mapbase.setPositionHero(poshero);
		ListeMaps.put(map, mapbase);
	}
	public HashMap<String,String[]> Chat (String[] answers, String question){
		HashMap<String,String[]> chat = new HashMap<String,String[]>();
		chat.put("answers",answers);
		String[] listeQuestion = new String[1];
		listeQuestion[0]=question;
		chat.put("message",listeQuestion);
		return chat;
	}


	public static void main(String[] args) {
		launch(args);
	}

}