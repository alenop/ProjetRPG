package rpgapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.settings.GameSettings;

import character.Hero;
import character.MonsterList;
import character.Monstre;
import item.Arme;
import item.Armure;
import item.Item;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import rpgapp.control.PlayerComponent;
import system.Quest;
import system.Spawn;

public class RPGApp extends GameApplication  {
	
	
	public static final int TILE_SIZE = 64;
	public static Hero hero=new Hero("ian");
	public static Quest quest;
	private Entity player;
	private PlayerComponent playerComponent;
	
	@Override
    protected void initSettings(GameSettings settings) {
		//Initialise la fenetre
        settings.setWidth(10 * 64);
        settings.setHeight(10 * 64);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
        settings.setFullScreenAllowed(true);
        // other settings
    }

	@Override
	protected void initGame() {
		//Initialise le jeu
		
		//AJoute la Factory
		getGameWorld().addEntityFactory(new RPGFactory());
		//Créer la map à partir du fichier Tiled
		getGameWorld().setLevelFromMap("map5.json");
		//getGameWorld().addWorldListener(getPhysicsWorld());
		//getPhysicsWorld().onEntityRemoved();
		
		//System.out.println(getGameplay().getStats());
		//System.out.println(getGameState().getProperties());
		//paramètres de jeu pour tester
		Item b = new Arme(40, "Hache");
		hero.getInventaire()[0] = b;
		hero.getInventaire()[1] = new Armure(0, "Armure");
		hero.equip();
		MonsterList.init();
		getGameWorld().spawn("bloc",new Point2D(-64,0));
		getGameWorld().spawn("portal",new Point2D(0,128));
		Monstre souris = new Monstre("souris",10,20,100);
		createMonstre(souris.getNom(),new Monstre("souris",10,20,100),new Point2D(128+64,0));
		
		quest = new Quest("kill souris",50000,souris,10);
		hero.setCurrentquest(quest);
		
//		for (int i=0;i<11;i++) {
//		createMonstre(souris.getNom(),new Monstre("souris",10,20,100),new Point2D(128+i*64,0));
//		}
		
		
		
		
		
//		listeMonstres= [];
//		listeMonstres.append("80:0":souris);
		
		
		//Créer le joueur
		player = Entities.builder()
	                     .at(0, 0)
	                     .viewFromTexture("image.png")
	                     .with(new PlayerComponent())
	                     .type(EntityType.PLAYER)
	                     .buildAndAttach(getGameWorld());
	    
	    
	    
	    //Lie la camera au personnage
	    getGameScene().getViewport().bindToEntity(player, getWidth()/2 , getHeight()/2 );
//	    EntityView view = player.getView();
//	    player.setView(view);
	    playerComponent = player.getComponent(PlayerComponent.class);

	}
	
	@Override
	protected void initInput() {
		//Initialise les commandes de l'utilisateur
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
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER,EntityType.Monstre){
			@Override
			protected void onCollisionBegin(Entity pl, Entity monstre) {
				int a =2*4;
			}
		});
	}
	public void createMonstre(String a,Monstre b,Point2D c) {
		getGameWorld().spawn(a,c);
		if(MonsterList.MonsterList.put(c, b)!=null) {
		MonsterList.MonsterList.put(c, b);
	}}


    public static void main(String[] args) {
        launch(args);
    }
	
	
	
}