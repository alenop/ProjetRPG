package rpgapp.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;

import character.Monstre;
import item.Arme;
import item.Armure;
import item.Item;
import character.Etat;
import character.Hero;
import character.MonsterList;
import javafx.geometry.Point2D;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import system.Systems;
import system.SystemsView;

public class PlayerComponent extends Component{
 
	private PositionComponent position;
	
	@Override
	public void onUpdate(double tpf) {}
	
	//Les methodes move ne fonctionnent que si "CanMove" est vérifié
	
	public void moveRight() {
		CheckAction(new Point2D(RPGApp.TILE_SIZE,0));
//		try {
//			position.translateX(RPGApp.TILE_SIZE);
//		
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }}
//		else if (canMove(new Point2D(RPGApp.TILE_SIZE,0),EntityType.Monstre)==false) {
//			//Monstre monstre = new Monstre("souris");
//				Hero hero = new Hero("ian");
//				Item b = new Arme(40,"Hache");
//				hero.getInventaire()[0]=b;
//				hero.getInventaire()[1]=new Armure(0,"Armure");
//				hero.equip();
//				
//				String a = String.valueOf(Math.round(position.getX()+RPGApp.TILE_SIZE));
//				String g = String.valueOf(Math.round(position.getY()));
//				String tot =a+g;
//				Monstre monstre = MonsterList.MonsterList.get(new Point2D(position.getX()+RPGApp.TILE_SIZE,position.getY()));
//				System.out.println(monstre.getPv());
//				try {
//					try {Systems.Combat(hero,monstre,"attaque");}
//					catch (Exception e) {
//						
//					}
//				
//		            Thread.sleep(500);
//		        } catch (InterruptedException e) {
//		            e.printStackTrace();
//		        }
//
//			
//			
//			
//		}
	}
	
	public void moveLeft() {
		if (canMove(new Point2D(-RPGApp.TILE_SIZE,0),EntityType.BLOC)){
			try {
				position.translateX(-RPGApp.TILE_SIZE);
			
	            Thread.sleep(200);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }}
			
	}
	
	public void moveDown() {
		if (canMove(new Point2D(position.getX(),position.getY()+RPGApp.TILE_SIZE),EntityType.BLOC)){
			try {
				position.translateY(RPGApp.TILE_SIZE);
			
	            Thread.sleep(200);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }}
			
	}
	
	public void moveUp() {
		if (canMove(new Point2D(0,-RPGApp.TILE_SIZE),EntityType.BLOC)){
			try {
				position.translateY(-RPGApp.TILE_SIZE);
			
	            Thread.sleep(200);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }}
			
		canMove(new Point2D(0,-RPGApp.TILE_SIZE),EntityType.BLOC);
	}
	
	private boolean canMove(Point2D direction, EntityType a) {
		//Vérifie que la case n'est pas un Bloc
		//System.out.println(position.getX()+"/"+position.getY());
		//System.out.println(position.getGridX(RPGApp.TILE_SIZE));
		Point2D newPosition = position.getValue().add(direction);
	
//		return FXGL.getApp()
//				.getGameScene()
//				.getViewport()
//				.getVisibleArea()
//				.contains(newPosition)
//				
//				&&
				
		return FXGL.getApp()
					.getGameWorld()
					.getEntitiesAt(newPosition)
					.stream()
					.filter(e -> e.hasComponent(TypeComponent.class))
					.map(e -> e.getComponent(TypeComponent.class))
					.noneMatch(type -> type.isType(a));
				
	}
	
	private void CheckAction(Point2D direction) {
		if (canMove(direction,EntityType.BLOC) && canMove(direction,EntityType.Monstre)) {
			try {
				position.translateX(RPGApp.TILE_SIZE);
			
	            Thread.sleep(200);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }}
			else if (canMove(direction,EntityType.Monstre)==false) {
				//Monstre monstre = new Monstre("souris");
					Hero hero = new Hero("ian");
					Item b = new Arme(40,"Hache");
					hero.getInventaire()[0]=b;
					hero.getInventaire()[1]=new Armure(0,"Armure");
					hero.equip();
					Monstre monstre = MonsterList.MonsterList.get(direction);
					System.out.println(monstre.getPv());
					try {
						try {Systems.Combat(hero,monstre,"attaque");}
						catch (Exception e) {
							
						}
					
			            Thread.sleep(500);
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }

				
				
				
			}
	}
}
