package rpgapp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;

import character.MonsterList;
import character.Monstre;
import elementsInteractifs.PNJ;
import elementsInteractifs.PNJList;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import rpgapp.control.PlayerComponent;

public class RPGApp extends GameApplication {

	public static final int TILE_SIZE = 64;
	private Entity player;
	private PlayerComponent playerComponent;


	@Override
	protected void initSettings(GameSettings settings) {
		// Initialise la fenetre
		settings.setWidth(10 * 64);
		settings.setHeight(10 * 64);
		settings.setTitle("Basic Game App");
		settings.setVersion("0.1");
		// other settings
	}

	@Override
	protected void initGame() {
		// Initialise le jeu

		// AJoute la Factory
		getGameWorld().addEntityFactory(new RPGFactory());
		// Créer la map à partir du fichier Tiled
		getGameWorld().setLevelFromMap("map5.json");

		getGameWorld().spawn("bloc", new Point2D(-64, 0));
		getGameWorld().spawn("souris", new Point2D(128, 0));
		Monstre souris = new Monstre("souris", 10, 20, 100);
		MonsterList.init();
		MonsterList.MonsterList.put("128:0", souris);

		getGameWorld().spawn("pnj", new Point2D(192, 128));
		PNJ pnj1=new PNJ("pnj1", 192, 128, "Tue la souris");
		PNJList.init();
		PNJList.pnjList.put("pnj1",pnj1);
		
		getGameWorld().spawn("pnj", new Point2D( 192, 128));
		PNJ pnj2=new PNJ("pnj2", 192, 128, "Ceuille des fleurs");
		PNJList.pnjList.put("pnj2",pnj2);
	
		getGameWorld().spawn("pnj", new Point2D( 192, 128));
		PNJ pnj4=new PNJ("pnj3", 192, 128, "Bavo!");
		PNJList.pnjList.put("pnj3",pnj4);
		
		getGameWorld().spawn("pnj", new Point2D(  192, 128));
		PNJ pnj5=new PNJ("pnj4",  192, 128, "Perdu! rejoue");
		PNJList.pnjList.put("pnj4",pnj5);
		
		// Créer le joueur
		player = Entities.builder().at(0, 0).viewFromTexture("image.png").with(new PlayerComponent())
				.type(EntityType.PLAYER).buildAndAttach(getGameWorld());

		// Lie la camera au personnage
		getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
		playerComponent = player.getComponent(PlayerComponent.class);
	}

	@Override
	protected void initInput() {
		// Initialise les commandes de l'utilisateur
		Input input = getInput();

		input.addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {
				playerComponent.moveRight();
				player.setViewFromTexture("image.png");
			}
		}, KeyCode.D);

		input.addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {
				playerComponent.moveLeft();
				player.setViewFromTexture("image2.png");
			}
		}, KeyCode.Q);

		input.addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {
				playerComponent.moveUp();
			}
		}, KeyCode.Z);

		input.addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {
				playerComponent.moveDown();
			}
		}, KeyCode.S);
	}

	public void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.Monstre) {
			@Override
			protected void onCollisionBegin(Entity pl, Entity monstre) {
				int a = 2 * 4;
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}