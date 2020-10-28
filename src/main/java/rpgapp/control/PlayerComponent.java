package rpgapp.control;

import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;

import character.Hero;
import character.MonsterList;
import character.Monstre;
import elementsInteractifs.PNJ;
import elementsInteractifs.PNJList;
import item.Arme;
import item.Armure;
import item.Item;
import javafx.geometry.Point2D;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import system.Systems;

public class PlayerComponent extends Component {

	private PositionComponent position;

	@Override
	public void onUpdate(double tpf) {
	}

	// Les methodes move ne fonctionnent que si "CanMove" est vérifié

	public void moveRight() {
		if (canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.BLOC)
				&& canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.Monstre)
				&& canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.PNJ)) {
			try {
				position.translateX(RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.Monstre) == false) {
			// Monstre monstre = new Monstre("souris");
			Hero hero = new Hero("ian");
			Item b = new Arme(40, "Hache");
			hero.getInventaire()[0] = b;
			hero.getInventaire()[1] = new Armure(0, "Armure");
			hero.equip();

			String a = String.valueOf(Math.round(position.getX() + RPGApp.TILE_SIZE));
			String g = String.valueOf(Math.round(position.getY()));
			String tot = a + g;
			Monstre monstre = MonsterList.MonsterList.get(tot);
			try {
				try {
					Systems.Combat(hero, monstre, "attaque");
				} catch (Exception e) {

				}

				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if (canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.PNJ) == false) {
			
			double xPNJ = position.getX() + RPGApp.TILE_SIZE;
			double yPNJ = position.getY();
			//PNJ p = PNJList.pnjList.get(position);
			for(Entry<String, PNJ> pnj : PNJList.pnjList.entrySet()) {
			PNJ p = pnj.getValue();
				if(p.getPosX() == xPNJ && p.getPosY() == yPNJ) {
				
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
					FXGL.getApp().getDisplay().showMessageBox(p.getTexte());{
					}
				}
			}
		}
	}

	private Object create_color(int i, int j, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveLeft() {
		if (canMove(new Point2D(-RPGApp.TILE_SIZE, 0), EntityType.BLOC)) {
			try {
				position.translateX(-RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void moveDown() {
		if (canMove(new Point2D(position.getX(), position.getY() + RPGApp.TILE_SIZE), EntityType.BLOC)) {
			try {
				position.translateY(RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void moveUp() {
		if (canMove(new Point2D(0, -RPGApp.TILE_SIZE), EntityType.BLOC)) {
			try {
				position.translateY(-RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		canMove(new Point2D(0, -RPGApp.TILE_SIZE), EntityType.BLOC);
	}

	private boolean canMove(Point2D direction, EntityType a) {
		// Vérifie que la case n'est pas un Bloc
		// System.out.println(position.getX()+"/"+position.getY());
		// System.out.println(position.getGridX(RPGApp.TILE_SIZE));
		Point2D newPosition = position.getValue().add(direction);

		return FXGL.getApp().getGameWorld().getEntitiesAt(newPosition).stream()
				.filter(e -> e.hasComponent(TypeComponent.class)).map(e -> e.getComponent(TypeComponent.class))
				.noneMatch(type -> type.isType(a));

	}

//	private void CheckAction(Point2D direction) {
//		if (canMove(direction, EntityType.BLOC) && canMove(direction, EntityType.Monstre)) {
//			try {
//				position.translateX(RPGApp.TILE_SIZE);
//
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		} else if (canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.Monstre) == false) {
//			// Monstre monstre = new Monstre("souris");
//			Hero hero = new Hero("ian");
//			Item b = new Arme(40, "Hache");
//			hero.getInventaire()[0] = b;
//			hero.getInventaire()[1] = new Armure(0, "Armure");
//			hero.equip();
//
//			String a = String.valueOf(Math.round(position.getX() + RPGApp.TILE_SIZE));
//			String g = String.valueOf(Math.round(position.getY()));
//			String tot = a + g;
//			Monstre monstre = MonsterList.MonsterList.get(tot);
//			try {
//				try {
//					Systems.Combat(hero, monstre, "attaque");
//				} catch (Exception e) {
//
//				}
//
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
}
