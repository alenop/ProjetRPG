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

public class PlayerComponent extends Component {

	private PositionComponent position;

	@Override
	public void onUpdate(double tpf) {
	}

	// Les methodes move ne fonctionnent que si "CanMove" est vérifié

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

	private boolean canMove(Point2D direction, EntityType a) {
		// Vérifie que la case n'est pas un Bloc
		// System.out.println(position.getX()+"/"+position.getY());
		// System.out.println(position.getGridX(RPGApp.TILE_SIZE));

//		return FXGL.getApp()
//				.getGameScene()
//				.getViewport()
//				.getVisibleArea()
//				.contains(newPosition)
//				
//				&&

		return FXGL.getApp().getGameWorld().getEntitiesAt(direction).stream()
				.filter(e -> e.hasComponent(TypeComponent.class)).map(e -> e.getComponent(TypeComponent.class))
				.noneMatch(type -> type.isType(a));

	}

	private void CheckAction(Point2D direction) {
		Point2D newPosition = position.getValue().add(direction);
		if (canMove(newPosition, EntityType.BLOC) && canMove(newPosition, EntityType.Monstre)) {
			try {
				position.translate(direction);

				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (canMove(newPosition, EntityType.Monstre) == false) {
			Monstre monstre = MonsterList.MonsterList.get(newPosition);
			if (monstre.getEtat() == Etat.vivant) {
				// Monstre monstre = new Monstre("souris");
				try {
					try {
						Systems.Combat(RPGApp.hero, monstre, "attaque");
						System.out.println(monstre.getPv());
						System.out.println(RPGApp.hero.getPv());
						if (monstre.getEtat() == Etat.mort) {
							FXGL.getApp().getGameWorld()
									.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));

						}
					} catch (Exception e) {

					}

					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
