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
import com.almasb.fxgl.entity.view.EntityView;
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
import rpgapp.control.QuestComponent;
import rpgapp.data.character.Hero;
import rpgapp.data.character.Monstre;
import rpgapp.data.character.Monstres;
import rpgapp.data.character.SaveLoad;
import rpgapp.data.elementInteractifs.Arme;
import rpgapp.data.elementInteractifs.Armure;
import rpgapp.data.elementInteractifs.Chest;
import rpgapp.data.elementInteractifs.Equipment;
import rpgapp.data.elementInteractifs.Indice;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.data.elementInteractifs.PNJList;
import rpgapp.data.elementInteractifs.Potion;
import rpgapp.data.map.ModeleMap;
import rpgapp.factory.MenuSceneFactory;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;
import rpgapp.view.DisplayMap;
import rpgapp.view.DisplayQuete;
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
	public static boolean save=false;
	public static Entity dialogBox = null;
	public static boolean move=true;
	public static boolean MonstreMove=true;

	@Override
	protected void initSettings(GameSettings settings) {
		// Initialise la fenetre
		settings.setWidth(15*64);
		settings.setHeight(10*64);
		settings.setTitle("The RPG");
		settings.setVersion("0.1");
		settings.setFullScreenAllowed(true);
		settings.setManualResizeEnabled(true);
		settings.setMenuEnabled(true);
		settings.setSceneFactory(new MenuSceneFactory());
		// other settings
	}

	@Override
	protected void initGame() {

		// Initialise le jeu
		Point2D pos=new Point2D(0,0);
		//MusicComponent.musicPlay("cave");
		if(save) {
			RPGApp.hero=SaveLoad.load("MainMenu");
			pos=RPGApp.hero.getPosition();
		}else {
			hero.setCurrentMap("mapMaison.json");
		}
		// AJoute la Factory
		getGameWorld().addEntityFactory(new RPGFactory());
		getAudioPlayer().setGlobalMusicVolume(0.15);
		getAudioPlayer().setGlobalSoundVolume(0.25);
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
		createChest("mapJardin.json", new Point2D(1472, 320), new Chest(new Arme(40, "Hache", "Hache.png")));
		createChest("mapJardin.json", new Point2D(1408, 320), new Chest(new Arme(15, "Balai", "Balai.png")));
		createChest("mapMaison.json", new Point2D(1664, 448), new Chest(new Arme(30, "Epee", "Epee.png")));
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
		
		
//		createMonster("mapCave.json", new Monstre("le boss des Rats", 50, 40, 100,true,"tuer le rat de la cave"), new Point2D(512, 704));
		createMonster("mapCave.json", new Monstre("le boss des Rats", 50, 40, 100,true,"tuer le rat de la cave",Monstres.BossRat), new Point2D(512, 704));
//		createMonster("mapJardin.json", new Monstre("souris", 30, 20, 100,true,"tuer le rat de la cave"), new Point2D(896, 2048));
//		createMonster("mapJardin.json", new Monstre("souris", 30, 20, 100,true,"tuer le rat de la cave"), new Point2D(2304, 2304));
//		createMonster("mapJardin.json", new Monstre("souris", 30, 20, 100,true,"tuer le rat de la cave"), new Point2D(2624, 2880));
		
		createIndice("mapCave.json", new Point2D(512, 704) ,new Indice("Morceau de fromage", "Water_Icon.png"));
		
		String[] liste=new String[2];
		liste[0]="Une arme adaptée ?";
		liste[1]="Oui papa";
		HashMap<String,String[]> conversation = Chat(liste,"Un rat mange tout mon fromage dans la cave ! va t'equiper d'une arme adaptée et tue le !");
		String[] liste2=new String[2];
		liste2[0]="Oui papa";
		liste2[1]="protéiné ?";
		HashMap<String,String[]> conversation2 = Chat(liste2,"Seule une arme adaptée te permettra de vaincre ce rat mangeur de fromage protéiné");
		String[] liste3=new String[1];
		liste3[0]="Oui papa";
		HashMap<String,String[]> conversation3 = Chat(liste3,"Va me tuer ce rat et arrête de poser tant de questions !");
		String[] liste4=new String[1];
		liste4[0]="Merci papa";
		HashMap<String,String[]> conversation4 = Chat(liste4,"Bravo fils!");
		String[] liste5=new String[1];
		liste5[0]="oui papa";
		HashMap<String,String[]> conversation5 = Chat(liste5,"Eh bien ce rat te pose des soucis ? n'oublie\n pas seule une arme adaptée te permettra\n de vaincre ce rat");
		HashMap<String,HashMap<String,String[]>> conversationComplete=new HashMap<String,HashMap<String,String[]>>();
		conversationComplete.put("begin", conversation);
		conversationComplete.put("Une arme adaptée ?", conversation2);
		conversationComplete.put("protéiné ?", conversation3);
		conversationComplete.put("finish", conversation4);
		conversationComplete.put("en cours", conversation5);
		//PNJ pere =new PNJ("Père","Cadre.png",conversationComplete,new Quest("tuer le rat de la cave",1000,Monstres.BossRat,1),"Oui papa");
		Quest q = new Quest("Contact avec le Rat, Partie I:", 100, "Examiner", "Indice de la cave", 1, "Votre père vous demande de vous debarasser du rat de la cave. Trouvez le !");
		
		PNJ pere =new PNJ("Père","Cadre.png",conversationComplete,q,"Oui papa");
		createPNJ("mapMaison.json",pere, new Point2D(1024,960));
		
		String[] answer1A = new String[2];
		answer1A[0] = "Pourquoi pas.";
		answer1A[1] = "Bof ça ira, merci...";
		String[] answer1B = new String[1];
		answer1B[0] = "...";
		
		String[] answer2A = new String[2];
		answer2A[0] = "Pas de soucis.";
		answer2A[1] = "Desole, je n'ai pas le temps.";
		String[] answer2B = new String[1];
		answer2B[0] = "D'accord.";
		
		String[] answer3A = new String[1];
		answer3A[0] = "Merci.";
		
		HashMap<String, String[]> conversation1A = Chat(answer1A, "Oh bonjour mon ptit "+hero.getName()+" ! \nTu veux que je te raconte la fois ou j'ai échouer \nà vaincre un criminel de mon épée ? \nQuel rat celui la !");
		HashMap<String, String[]> conversation1B1 = Chat(answer1B, "Je crois que je m'en souviens plus..");
		HashMap<String, String[]> conversation1B2 = Chat(answer1B, "Ah...Je m'en souviens plus de toute façon..");
		
		HashMap<String, String[]> conversation2A = Chat(answer2A, "Hey "+hero.getName()+" ! \nJ'ai faim, si tu vas dans ton jardin n'hesite \npas à me ramener des pommes de ton coffre.");
		HashMap<String, String[]> conversation2B1 = Chat(answer2B, "Merci ! j'ai faim.");
		HashMap<String, String[]> conversation2B2 = Chat(answer2B, "Tu es sûr ? Penses y si tu dois t'équiper");
		
		HashMap<String, String[]> conversation3A = Chat(answer3A, "Coucou "+hero.getName()+". Fais attention si tu utilises un objet tranchant, \ntu risquerais de te faire mal.");

		HashMap<String,HashMap<String,String[]>> conversationComplete1=new HashMap<String,HashMap<String,String[]>>();
		conversationComplete1.put("begin", conversation1A);
		conversationComplete1.put("Pourquoi pas.", conversation1B1);
		conversationComplete1.put("Bof ça ira, merci...", conversation1B2);
		
		HashMap<String,HashMap<String,String[]>> conversationComplete2=new HashMap<String,HashMap<String,String[]>>();
		conversationComplete2.put("begin", conversation2A);
		conversationComplete2.put("Pas de soucis.", conversation2B1);
		conversationComplete2.put("Desole, je n'ai pas le temps.", conversation2B2);
		
		HashMap<String,HashMap<String,String[]>> conversationComplete3=new HashMap<String,HashMap<String,String[]>>();
		conversationComplete3.put("begin", conversation3A);
		
		PNJ pnj1 =new PNJ("MrGeorges","Cadre1.png",conversationComplete1);
		createPNJ("mapPnj1.json",pnj1, new Point2D(640,768));
		
		PNJ pnj2 =new PNJ("MrBernard","Cadre2.png",conversationComplete2);
		createPNJ("mapPnj2.json",pnj2, new Point2D(960,640));
		
		PNJ pnj3 =new PNJ("MmeJuliette","Cadre3.png",conversationComplete3);
		createPNJ("mapPnj3.json",pnj3, new Point2D(896,704));
	
		DisplayMap.chargeMapInit(hero.getCurrentMap());
		if (save==false) {
			pos=ListeMaps.get(hero.getCurrentMap()).getPositionHero();
		}
		// Créer le joueur
		player = Entities.builder().at(pos)
				.viewFromTexture("HerosFace.png").with(new PlayerComponent()).type(EntityType.PLAYER)
				.buildAndAttach(getGameWorld());

		// Lie la camera au personnage

		getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
		playerComponent = player.getComponent(PlayerComponent.class);
		if (save==false) {
		hero.equip(new Armure(21, "t-shirt", "t-shirt.png"));
		hero.addItemInventory(new Potion("potion de soin","potion_de_soin.png"));
		}
		
		DisplayEquipment.createEquipment();
		DisplayInventaire.createInventaire();
		DisplayQuete.createQuete();
		//get
		
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

		input.addAction(new UserAction("Open Inventory") {
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
		
		input.addAction(new UserAction("Open Equipment") {
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
		
		input.addAction(new UserAction("Open TabQuest") {
			@Override
			protected void onAction() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (DisplayQuete.getQuete().getView().isVisible()) {
					DisplayQuete.removeQuete();
				}
				else {
					DisplayQuete.afficheQuete();
				}
			}
		}, KeyCode.K);
		
		input.addAction(new UserAction("Analyse") {
			@Override
			protected void onActionBegin() {
				try {
					playerComponent.analyse();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, KeyCode.A);
		
		
		input.addAction(new UserAction("Quete Suivante") {
			@Override
			protected void onActionBegin() {
				try {
					QuestComponent.suiteQuete(hero.getCurrentquest());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, KeyCode.B);
		
		
		input.addAction(new UserAction("Save") {
			@Override
			protected void onAction() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				SaveLoad.save(RPGApp.hero);
			}
		}, KeyCode.M);
		
		input.addAction(new UserAction("Load") {
			@Override
			protected void onAction() {
				load();
				RPGApp.hero=SaveLoad.load("GameMenu");
				DisplayMap.chargeMap(RPGApp.hero.getCurrentMap(),RPGApp.hero.getPosition());
				load2();
			}
		}, KeyCode.L);
	}

	public static void createMonster(String map, Monstre monstre, Point2D posmonstre) {

		if (ListeMaps.get(map).getMonsterList().get(posmonstre) == null) {
			ListeMaps.get(map).getMonsterList().put(posmonstre, monstre);
		}
		if (ListeMaps.get(map).getMonsterListActual().get(posmonstre) == null) {
			ListeMaps.get(map).getMonsterListActual().put(posmonstre, monstre);
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
	
	public static void createIndice(String map, Point2D pos, rpgapp.data.elementInteractifs.Indice indice) {
		
		if (ListeMaps.get(map).getIndiceList().get(pos) == null) {
			ListeMaps.get(map).getIndiceList().put(pos, indice);
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
	public static HashMap<String,String[]> Chat (String[] answers, String question){
		HashMap<String,String[]> chat = new HashMap<String,String[]>();
		chat.put("answers",answers);
		question=DisplayBasic.retourLigne(question,30);
		String[] listeQuestion = new String[1];
		listeQuestion[0]=question;
		chat.put("message",listeQuestion);
		return chat;
	}
	public static void load() {
		try {
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Item i:RPGApp.hero.getInventory()) {
			if(i!=null) {
			DisplayInventaire.updateInventaire("remove", i, i.getPosition());
			}
		}
		for (Entry<String, Equipment> i:RPGApp.hero.getEquipement().entrySet()) {
			if(i.getValue()!=null) {
			DisplayEquipment.updateEquipment("remove", i.getValue());
			}
		}
		
		System.out.println(RPGApp.hero.getInventory());
	}
	public static void load2() {
		
		
		for (Item i:RPGApp.hero.getInventory()) {
			if(i!=null) {
			DisplayInventaire.updateInventaire("ajout", i, i.getPosition());
			}
		}
		for (Entry<String, Equipment> i:RPGApp.hero.getEquipement().entrySet()) {
			if(i.getValue()!=null) {
			DisplayEquipment.updateEquipment("ajout", i.getValue());
			}
		}
	}


	public static void main(String[] args) {
		launch(args);
	}

}