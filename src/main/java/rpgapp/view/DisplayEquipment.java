package rpgapp.view;

import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.PlayerComponent;
import rpgapp.data.elementInteractifs.Item;

public class DisplayEquipment extends DisplayBasic {
	public static void removeEquipment() {
		getEquipment().getView().setVisible(false);
	}

	public static void afficheEquipment() {
		getEquipment().getView().setVisible(true);
	}
	public static Entity getEquipment() {
		Entity equipment = trouveEntity(new Point2D(PlayerComponent.position.getX() -3* RPGApp.TILE_SIZE,
				PlayerComponent.position.getY() + RPGApp.TILE_SIZE), EntityType.Equipment);
		return equipment;
	}
	public static void updateInventaire(String a, Item b, int i) {
		if (a.equals("ajout")) {
			ajoutItem(b, i);
		} else if (a.equals("remove")) {
			removeItem(b, i);
		}
	}

	public static void removeItem(Item a, int position) {

		Node i = getEquipment().getView().getNodes().get(position + 1);
		i.setVisible(false);
	}

	public static void ajoutItem(Item a, int i) {
		getEquipment().getView().getNodes().get(i + 1).setVisible(true);
		DisplayInventaire.itemViewInventaire(a,
				((EntityView) ((Entity) getEquipment().getView().getNodes().get(i + 1).getUserData()).getView()),"equip");
	}
	
	
	public static void createEquipment() {

		double BG_WIDTH = 64 * 4;
		double BG_HEIGHT = 64 * 4;

		Entity equipment = createRectangle(BG_WIDTH, BG_HEIGHT,
				new Point2D(PlayerComponent.position.getX() -3* RPGApp.TILE_SIZE,
						PlayerComponent.position.getY() + RPGApp.TILE_SIZE));

		equipment.setType(EntityType.Equipment);
		EntityView equipmentView = equipment.getView();

		int x = 0;
		int y = 0;
		for (Entry<String, Item> i : RPGApp.hero.getEquipement().entrySet()) {

			if (x >= 4) {
				x = 0;
				y = y + 1;
			}
			Entity item = createRectangle(64, 64, new Point2D(64 * x, 64 * y));
			item.getView().setUserData(item);

			if (i != null) {
				DisplayInventaire.itemViewInventaire(i.getValue(), item.getView(),"desequip");
			}
			equipmentView.addNode(item.getView());
			x++;

		}

		equipment.getView().setVisible(false);
		FXGL.getApp().getGameWorld().addEntity(equipment);

		// FXGL.getApp().getGameWorld().

	}
}
