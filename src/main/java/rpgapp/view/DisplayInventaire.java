package rpgapp.view;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.eventhandler.EquipmentHandler;
import rpgapp.eventhandler.InventoryHandler;
import rpgapp.eventhandler.ItemEventHandler;

public class DisplayInventaire extends DisplayBasic {
	public static void removeInventaire() {
		getInventory().getView().setVisible(false);
		MusicComponent.soundPlay("open");
	}

	public static void afficheInventaire() {
		getInventory().getView().setVisible(true);
		MusicComponent.soundPlay("open");
	}

	public static Entity getInventory() {
		Entity inventaire = trouveEntity(new Point2D(PlayerComponent.position.getX() + 3*RPGApp.TILE_SIZE,
				PlayerComponent.position.getY() + RPGApp.TILE_SIZE -32), EntityType.Inventaire);
		return inventaire;
	}

	public static void updateInventaire(String a, Item b, int i) {
		if (a.equals("ajout")) {
			ajoutItem(b, i);
		} else if (a.equals("remove")) {
			removeItem(b, i);
		}
	}

	public static void removeItem(Item a, int position) {

		Node i = getInventory().getView().getNodes().get(position + 1);
		EntityView v=(EntityView)i;
		v.clearChildren();
		Rectangle c = DisplayBasic.createBorder(64,64);
		((Entity)i.getUserData()).setViewWithBBox(c);
		
		
	}

	public static void ajoutItem(Item a, int i) {
		itemViewInventaire(a,
				((EntityView) ((Entity) getInventory().getView().getNodes().get(i+1).getUserData()).getView()),"equip");
	}

	public static void itemViewInventaire(Item i, EntityView a,String choix) {

		Entity itemViewPetit = Entities.builder()
				.viewFromTextureWithBBox(i.getInventaireImage()).build();
		Entity itemViewGrand = itemViewPetit.copy();
		itemViewPetit.getView().setUserData(itemViewPetit);
		itemViewGrand.setViewFromTexture(i.getImage());
		if (choix.equals("desequip")) {
			a.setOnMouseClicked(new EquipmentHandler(itemViewGrand,i.getType()));
			a.setOnMouseEntered(new ItemEventHandler(i.getType()));
		}else if(choix.equals("equip")) {
			a.setOnMouseClicked(new InventoryHandler(itemViewGrand,i.getPosition()));
			a.setOnMouseEntered(new ItemEventHandler(i.getPosition()) );
		}
		
		
		a.addNode(itemViewPetit.getView());

	}

	public static void createInventaire() {

		double BG_WIDTH = 64 * 4 + 16;
		double BG_HEIGHT = 64 * 4 + 32;

		Entity inventaire = createRectangle(BG_WIDTH, BG_HEIGHT,
				new Point2D(PlayerComponent.position.getX() + 3*RPGApp.TILE_SIZE,
						PlayerComponent.position.getY() + RPGApp.TILE_SIZE -32));

		inventaire.setType(EntityType.Inventaire);
		inventaire.setViewFromTexture("InventaireFond.png");
		EntityView inventaireView = inventaire.getView();
		inventaireView.setUserData(inventaire);

		int x = 0;
		int y = 0;
		for (Item i : RPGApp.hero.getInventory()) {

			if (x >= 4) {
				x = 0;
				y = y + 1;
			}
			Entity item = createRectangle(64, 64, new Point2D(64 * x +8, 64 * y +32));
			item.getView().setUserData(item);
		

			if (i != null) {	
				itemViewInventaire(i, item.getView(),"equip");
			}
			inventaireView.addNode(item.getView());
			x++;

		}

		inventaire.getView().setVisible(false);
		FXGL.getApp().getGameWorld().addEntity(inventaire);
	}
}
