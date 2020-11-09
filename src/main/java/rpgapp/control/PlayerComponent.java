package rpgapp.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.data.character.Etat;
import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Coffre;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.data.elementInteractifs.PNJList;
import rpgapp.view.Display;

public class PlayerComponent extends Component {

	public static PositionComponent position;
	

	@Override
	public void onUpdate(double tpf) {
		if (RPGApp.invent==true) {
        	RPGApp.removeNotif();
        	}
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

	private boolean checkEntity(Point2D direction, EntityType a) {
		// Vérifie que la case n'est pas un EntityType a
		//Point2D newPosition = position.getValue().add(direction);

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
				
				
				position.translate(direction);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (checkEntity(newPosition, EntityType.Monstre) == false) {
			Monstre monstre = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().get(newPosition);
			if (monstre.getEtat() == Etat.vivant) {
				Entity monstreview = Entities.builder()
	                    .viewFromTexture("RatCombatGif.gif")
						//.viewFrom
	                    .build();
				EntityView an = monstreview.getView();
				Display.mode_combat2(an,monstre,newPosition,1);
				
				
			}
			if (monstre.getEtat()==Etat.mort){
				FXGL.getApp().getGameWorld()
				.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
				//position.translate(direction);
			}
		}
		else if (checkEntity(newPosition, EntityType.PNJ) == false) {
			
			//PNJ p = PNJList.pnjList.get(position);
			int i=0;
			Entity item = Entities.builder()
                    .viewFromTexture("PnjFace.png")
                    .build();
			EntityView an = item.getView();
			Display.dialogue(an,i);
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
					//FXGL.getApp().getDisplay().showMessageBox(p.getTexte());
					
				}
		else if (checkEntity(newPosition, EntityType.Coffre) == false) {
			//EntityView an = FXGL.getApp().getGameWorld().getEntitiesAt(newPosition).get(0).getView();
			Coffre a = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getCoffreList().get(newPosition);
			if (a.getContenu()!=null) {
			Entity item = Entities.builder()
                    .viewFromTexture(a.getContenu().getImage())
					//.viewFrom
                    .build();
			EntityView an = item.getView();
                    //.buildAndAttach(getGameWorld());
			Display.trouveCoffre(an,a,newPosition);
		}
			else {
				FXGL.getApp().getDisplay().showMessageBox("tu as déja ouvert ce coffre");
				}
			}
		if (checkEntity(newPosition, EntityType.Portal) == false) {
			// PortalList.PortalList.get(RPGApp.hero.getCurrentMap()+newPosition.toString())!=null)
			// {
			Display.changeMap(RPGApp.hero.getCurrentMap(), newPosition);
		}
	

	}
}
