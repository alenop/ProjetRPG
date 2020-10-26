package rpgapp.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.io.serialization.Bundle;
import com.almasb.fxgl.util.Consumer;
import com.almasb.fxgl.util.Predicate;

import character.Monstre;
import item.Arme;
import item.Armure;
import item.Item;
import character.Etat;
import character.Hero;
import character.MonsterList;
import javafx.geometry.Point2D;
import map.PortalList;
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
		System.out.println("yo droite");
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
				// .filter(e -> e.hasComponent(TypeComponent.class)).map(e ->
				// e.getComponent(TypeComponent.class))
				.noneMatch(type -> type.isType(a));

	}

	private void CheckAction(Point2D direction) {

		Point2D newPosition = position.getValue().add(direction);
		System.out.println("yo" + newPosition);
		System.out.println(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
		if (canMove(newPosition, EntityType.BLOC) && canMove(newPosition, EntityType.Monstre)) {
			try {
				position.translate(direction);

				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (canMove(newPosition, EntityType.Monstre) == false) {
			System.out.println("yo le monstre");
			System.out.println(RPGApp.hero.getExperience());
			Monstre monstre = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().get(newPosition);
			if (monstre.getEtat() == Etat.vivant) {
				try {
					Predicate<String> bg = (x) -> x.equals("attaque") || x.equals("defense");
					Consumer<String> c = (x) -> {
						try {
							Systems.Combat(RPGApp.hero, monstre, x);
							if (monstre.getEtat() == Etat.mort) {
								FXGL.getApp().getGameWorld().removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
								position.translate(direction);//nécéssaire si attaque via boite de dialogue avec arrêt	
								
							}
							//nécéssaire si attaque via le mouvement sans arrêt pour éviter une attaque constante
//							if (monstre.getEtat() == Etat.vivant) {
//								try {
//									Thread.sleep(500);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//
//							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
					//FXGL.getApp().getDisplay().
					FXGL.getApp().getDisplay().showInputBox("Vous avez lancé un combat avec le monstre", bg, c);
				} catch (Exception e) {
				}
			}
			if (monstre.getEtat() == Etat.mort) {
				System.out.println("le lion est mort");
				FXGL.getApp().getGameWorld().removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
				RPGApp.hero.gainExp(monstre.getGive_experience());

				RPGApp.hero.getCurrentquest().setKill(RPGApp.hero.getCurrentquest().getKill() + 1);
				if (RPGApp.hero.getCurrentquest().verifQuest()) {
					System.out.println("quest succeed");
					RPGApp.hero.gainExp(RPGApp.hero.getCurrentquest().getReward());
				}
				System.out.println(RPGApp.hero.getLevel() + "***");
			}
			if (monstre.getEtat() == Etat.vivant) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
		if (canMove(newPosition, EntityType.Portal) == false) {
			// PortalList.PortalList.get(RPGApp.hero.getCurrentMap()+newPosition.toString())!=null)
			// {
			changeMap(RPGApp.hero.getCurrentMap(), newPosition);
		}
	}

	public void changeMap(String a, Point2D b) {

		System.out.println("yo portal");
		EntityView abcd = null;
		if (position.getEntity() != null) {
			abcd = position.getEntity().getView();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String map = RPGApp.ListeMaps.get(a).getPortalList().get(b);
		FXGL.getApp().getGameWorld().setLevelFromMap(map);
		RPGApp.hero.setCurrentMap(map);
		position.setValue(RPGApp.ListeMaps.get(map).getPositionHero());
		if (abcd != null) {
			FXGL.getApp().getGameScene().addGameView(abcd);
		}

		if (RPGApp.ListeMaps.get(map) != null) {
//		for (Map.Entry<Point2D,String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
//			FXGL.getApp().getGameWorld().spawn("portal", i.getKey());
//
//		}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("monstre", i.getKey());

			}
		}

	}

}
