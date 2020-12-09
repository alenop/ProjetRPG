package rpgapp.view;

import java.util.HashMap;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
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
	public static void updateEquipment(String a, Item b) {
		if (a.equals("ajout")) {
			ajoutItemEquipment(b);
		} else if (a.equals("remove")) {
			removeItemEquipment(b);
		}
	}

	public static void removeItemEquipment(Item a) {

		for(Node i : getEquipment().getView().getNodes()){
			if(i.getAccessibleText()!=null) {
			if(i.getAccessibleText().equals(a.getType())) {
				EntityView v=(EntityView)i;
				v.clearChildren();
				Rectangle c = DisplayBasic.createBorder(64,64);
				((Entity)i.getUserData()).setViewWithBBox(c);
			break;
		}}}
		
	}

	public static void ajoutItemEquipment(Item a) {
		for(Node i : getEquipment().getView().getNodes()){
			if(i.getAccessibleText()!=null) {
			if(i.getAccessibleText().equals(a.getType())) {
			DisplayInventaire.itemViewInventaire(a,
			((Entity)i.getUserData()).getView(),"desequip");
			break;
		}}}
	}
	
	
	public static void createEquipment() {

		double BG_WIDTH = 64 * 4;
		double BG_HEIGHT = 64 * 4;

		Entity equipment = createRectangle(BG_WIDTH, BG_HEIGHT,
				new Point2D(PlayerComponent.position.getX() -3* RPGApp.TILE_SIZE,
						PlayerComponent.position.getY() + RPGApp.TILE_SIZE));

		equipment.setType(EntityType.Equipment);
		EntityView equipmentView = equipment.getView();
		Entity fond=CreateEntityWithPicture("EquipmentFond.jpg", 0, 0);
		equipmentView.addNode(fond.getView());

		int x = 0;
		int y = 0;
		HashMap<String, Item> i = RPGApp.hero.getEquipement();
		//String[] list={"Arme","Armure"};
		HashMap<String,Point2D> list =new HashMap<String,Point2D>();
		list.put("Arme", new Point2D(2*64+32+16,64+32+16));
		list.put("Armure", new Point2D(64+32,64));
		for (Entry<String, Point2D> type : list.entrySet()) {

			if (x >= 4) {
				x = 0;
				y = y + 1;
			}
			Entity item = createRectangle(64, 64, type.getValue());
			item.getView().setUserData(item);
			item.getView().setAccessibleText(type.getKey());

			if (i.get(type.getKey()) != null) {		
				DisplayInventaire.itemViewInventaire(i.get(type.getKey()), item.getView(),"desequip");
			}
			equipmentView.addNode(item.getView());
			x++;

		}
		
		equipment.getView().setVisible(false);
		FXGL.getApp().getGameWorld().addEntity(equipment);

		// FXGL.getApp().getGameWorld().

	}
}
