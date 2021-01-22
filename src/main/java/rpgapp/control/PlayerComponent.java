package rpgapp.control;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;


import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.data.character.State;
import rpgapp.data.character.Monstre;
import rpgapp.data.character.Monstres;
import rpgapp.data.elementInteractifs.Indice;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.data.elementInteractifs.PNJList;
import rpgapp.view.Display;
import rpgapp.view.DisplayBasic;
import rpgapp.view.DisplayCoffre;
import rpgapp.view.DisplayCombat;
import rpgapp.view.DisplayIndice;
import rpgapp.view.DisplayMap;
import rpgapp.view.DisplayPNJ;
import rpgapp.view.DisplayQuete;

public class PlayerComponent extends Component {

	public static PositionComponent position;
	

	@Override
	public void onUpdate(double tpf) {
		

//		try {
//			Thread.sleep(500);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().entrySet()) {
//			if(DisplayBasic.trouveEntity(i.getKey(),EntityType.Monstre)!=null) {
//				DisplayBasic.trouveEntity(i.getKey(),EntityType.Monstre).setPosition(i.getKey().add(64, 0));
//			}
//			else if(DisplayBasic.trouveEntity(i.getKey().add(64, 0),EntityType.PNJ)!=null) {
//				DisplayBasic.trouveEntity(i.getKey().add(64, 0),EntityType.PNJ).setPosition(i.getKey());
//			}
//		}
	}

	// Les methodes move ne fonctionnent que si "CanMove" est vérifié

	public void moveRight() {
		CheckAction(new Point2D(RPGApp.TILE_SIZE, 0),"Droite");
	}

	public void moveLeft() {
		CheckAction(new Point2D(-RPGApp.TILE_SIZE, 0),"Gauche");

	}

	public void moveDown() {
		CheckAction(new Point2D(0, RPGApp.TILE_SIZE),"Face");

	}

	public void moveUp() {
		CheckAction(new Point2D(0, -RPGApp.TILE_SIZE),"Dos");
	}

	private boolean checkEntity(Point2D direction, EntityType a) {
		// Vérifie que la case n'est pas un EntityType a

//		return FXGL.getApp()
//				.getGameScene()
//				.getViewport()
//				.getVisibleArea()
//				.contains(direction)

//				&&

		return FXGL.getApp().getGameWorld().getEntitiesAt(direction).stream()
				.filter(e -> e.hasComponent(TypeComponent.class)).map(e ->
				e.getComponent(TypeComponent.class))
				.noneMatch(type -> type.isType(a));

	}

	private void CheckAction(Point2D direction, String angle) {
		if (RPGApp.move) {
			System.out.println(RPGApp.hero.getCurrentquest());
		Point2D newPosition = position.getValue().add(direction);
		System.out.println(newPosition);
		System.out.println(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
		CheckMonsterRange(newPosition);
		if (RPGApp.notif!=null) {
			FXGL.getApp().getGameWorld().removeEntity(RPGApp.notif);
			RPGApp.notif=null;
			}
		if (checkEntity(newPosition, EntityType.BLOC)==false || checkEntity(newPosition, EntityType.PNJ)==false){
			RPGApp.player.setViewFromTexture("Heros"+angle+".png");
		}
		if (checkEntity(newPosition, EntityType.BLOC) && checkEntity(newPosition, EntityType.Monstre)
				&& checkEntity(newPosition, EntityType.PNJ) && checkEntity(newPosition, EntityType.Coffre)) {
			try {
				if (checkEntity(new Point2D(position.getX()+3*RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE-32),EntityType.Inventaire)==false) {
					Entity i =Display.trouveEntity(new Point2D(position.getX()+3*RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE-32),EntityType.Inventaire);
					i.translate(direction);
				}
				if (checkEntity(new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
						PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2), EntityType.HeroStatus)==false) {
					Entity i =Display.trouveEntity(new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
							PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2), EntityType.HeroStatus);
					i.translate(direction);
				}
				if (checkEntity(new Point2D(position.getX()-7*RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE-32),EntityType.Equipment)==false) {
					Entity i =Display.trouveEntity(new Point2D(position.getX()-7*RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE-32),EntityType.Equipment);
					i.translate(direction);
				}
				if (checkEntity(new Point2D(position.getX()+3.5*RPGApp.TILE_SIZE,position.getY()-5*RPGApp.TILE_SIZE),EntityType.TableauQuete)==false) {
					Entity i =Display.trouveEntity(new Point2D(position.getX()+3.5*RPGApp.TILE_SIZE,position.getY()-5*RPGApp.TILE_SIZE),EntityType.TableauQuete);
					i.translate(direction);
				}
				
				
				position.translate(direction);
				RPGApp.player.setViewFromTexture("Heros"+angle+"Mv.gif");
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (checkEntity(newPosition, EntityType.Monstre) == false) {
			Monstre monstre = DisplayBasic.trouveEntity(newPosition, EntityType.Monstre).getProperties().getObject("data");
			
			if (monstre.getState() == State.alive) {
				DisplayCombat.begin(monstre,newPosition);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (monstre.getState()==State.dead){
				FXGL.getApp().getGameWorld()
				.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
				
			}
		}
		else if (checkEntity(newPosition, EntityType.PNJ) == false) {
			DisplayPNJ.init(RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getPNJList().get(newPosition));
			DisplayPNJ.tourner(newPosition, angle);
		}
		else if (checkEntity(newPosition, EntityType.Coffre) == false) {
			MusicComponent.soundPlay("chest");
			DisplayCoffre.trouveCoffre(newPosition);
			}
		if (checkEntity(newPosition, EntityType.Portal) == false) {
			MusicComponent.soundPlay("door");
			DisplayMap.changeMap(RPGApp.hero.getCurrentMap(), newPosition);
			
		}
	

	}}
	
	public void analyse() {
		//Verifie que le joueur possède une quete
		if(RPGApp.hero.getCurrentquest() != null) {
			//Vérifie que le joueur possède une quete d'analyse
			if(RPGApp.hero.getCurrentquest().getAction()=="Examiner") {
				//Vérifie que le joueur est bien sur un indice
				for (Entry<Point2D, Indice> i : RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getIndiceList().entrySet()) {
					if(i.getKey().getX()== position.getX() && i.getKey().getY()==position.getY()) {
						MusicComponent.soundPlay("analyse");
						DisplayIndice.trouveIndice(i.getKey());
						RPGApp.hero.getCurrentquest().upNbCibles();
						QuestComponent.verifQuest();
						DisplayQuete.updateQuete();
						FXGL.getApp().getGameWorld()
						.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(new Point2D(position.getX(), position.getY())));
						RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getIndiceList().remove(i.getKey());
					}

				}
			}

		}
	}
	
	public void CheckMonsterRange(Point2D pos) {
		CheckMonsterRangeWithDir(pos,"left");
		CheckMonsterRangeWithDir(pos,"right");
		CheckMonsterRangeWithDir(pos,"down");
		CheckMonsterRangeWithDir(pos,"up");
		if (RPGApp.MonstreMove==false) {
			RPGApp.MonstreMove=true;
		}
	}
	public void CheckMonsterRangeWithDir(Point2D pos, String dir){
		int x=0;
		int y=0;
		int z=0;;
		switch (dir) {
			case "right":
				x=0;
				y=-128;
				z=-64;
				break;
			case "left":
				x=-64*4;
				y=-128;
				z=64;
				break;
			case "up":
				x=-128;
				y=(-64*4);
				z=64;
				break;
			case "down":
				x=-128;
				y=0;
				z=-64;
				break;
		}
		List<Entity> mob=FXGL.getApp().getGameWorld().getEntitiesInRange(new Rectangle2D(pos.getX()+x,pos.getY()+y,64*4,64*4));
		for (Entity a : mob) {
			if (a.isType(EntityType.Monstre)) {
				Monstre monstre= ((Monstre)a.getProperties().getObject("data"));
				System.out.println(a);
				if (dir.equals("right")||dir.equals("left")) {
					if(checkEntity(a.getPosition().add(z, 0), EntityType.BLOC)) {
						if (RPGApp.MonstreMove && monstre.getState()==State.alive && monstre.getTypeMonstre()!=Monstres.BossRat) {
							a.translateX(z);
							RPGApp.MonstreMove=false;
						}
						
					}
					
				}else
					if(checkEntity(a.getPosition().add(0, z), EntityType.BLOC)) {
						if (RPGApp.MonstreMove && monstre.getState()==State.alive && monstre.getTypeMonstre()!=Monstres.BossRat) {
						a.translateY(z);
						RPGApp.MonstreMove=false;
					}}
			}
		}
		
	}

	public static void levelUp(int level) {
		
		String notif="Félicitations tu est maintenant niveau " + level;
		RPGApp.notif = DisplayBasic.createNotif(notif);
		FXGL.getApp().getGameWorld().addEntity(RPGApp.notif);
		
	}
}
