package rpgapp.control;

import com.almasb.fxgl.app.FXGL;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;


import javafx.geometry.Point2D;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.data.character.Etat;
import rpgapp.data.character.Monstre;

import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.data.elementInteractifs.PNJList;
import rpgapp.view.Display;
import rpgapp.view.DisplayCoffre;
import rpgapp.view.DisplayCombat;
import rpgapp.view.DisplayMap;
import rpgapp.view.DisplayPNJ;

public class PlayerComponent extends Component {

	public static PositionComponent position;
	

	@Override
	public void onUpdate(double tpf) {
	}

	// Les methodes move ne fonctionnent que si "CanMove" est v�rifi�

	public void moveRight() {
		CheckAction(new Point2D(RPGApp.TILE_SIZE, 0));
	}

	public void moveLeft() {
		CheckAction(new Point2D(-RPGApp.TILE_SIZE, 0));

	}

	public void moveDown() {
		CheckAction(new Point2D(0, RPGApp.TILE_SIZE));

	}

	public void moveUp() {
		CheckAction(new Point2D(0, -RPGApp.TILE_SIZE));
	}

	private boolean checkEntity(Point2D direction, EntityType a) {
		// V�rifie que la case n'est pas un EntityType a

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

	private void CheckAction(Point2D direction) {

		Point2D newPosition = position.getValue().add(direction);
		System.out.println(newPosition);
		System.out.println(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
		if (RPGApp.notif!=null) {
			FXGL.getApp().getGameWorld().removeEntity(RPGApp.notif);
			}
		if (checkEntity(newPosition, EntityType.BLOC) && checkEntity(newPosition, EntityType.Monstre)
				&& checkEntity(newPosition, EntityType.PNJ) && checkEntity(newPosition, EntityType.Coffre)) {
			try {
				if (checkEntity(new Point2D(position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE),EntityType.Inventaire)==false) {
					Entity i =Display.trouveEntity(new Point2D(position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE),EntityType.Inventaire);
					i.translate(direction);
				}
				if (checkEntity(new Point2D(position.getX()-3*RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE),EntityType.Equipment)==false) {
					Entity i =Display.trouveEntity(new Point2D(position.getX()-3*RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE),EntityType.Equipment);
					i.translate(direction);
				}
				
				
				position.translate(direction);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (checkEntity(newPosition, EntityType.Monstre) == false) {
			Monstre monstre = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().get(newPosition);
			if (monstre.getEtat() == Etat.vivant) {
				DisplayCombat.begin(monstre,newPosition);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (monstre.getEtat()==Etat.mort){
				FXGL.getApp().getGameWorld()
				.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
				
			}
		}
		else if (checkEntity(newPosition, EntityType.PNJ) == false) {
			
			
			DisplayPNJ.init(newPosition);
			PNJ p =PNJList.pnjList.get(newPosition);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(" Bonjour je suis " + p.getName());
					System.out.println(p.getTexte());
					System.out.println(" quete " + p.getName());
					System.out.println(p.getTexteQueteOk());
					System.out.println(" gain " + p.getName());
					System.out.println(p.getTexteGagne());
					System.out.println(" perdu " + p.getName());
					System.out.println(p.getTextePerd());
					
					
				}
		else if (checkEntity(newPosition, EntityType.Coffre) == false) {
			
			DisplayCoffre.trouveCoffre(newPosition);
			}
		if (checkEntity(newPosition, EntityType.Portal) == false) {
			DisplayMap.changeMap(RPGApp.hero.getCurrentMap(), newPosition);
		}
	

	}
}
