package rpg.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import rpg.RPGApp;
import rpg.data.elementInteractifs.Chest;
import rpg.data.elementInteractifs.Consumable;
import rpg.data.elementInteractifs.Equipment;
import rpg.data.elementInteractifs.Item;
import rpg.view.DisplayEquipment;
import rpg.view.DisplayInventaire;

public class CoffreHandler implements EventHandler<ActionEvent> {

	private Entity itemViewGrand;
	private Chest chest;
	private Point2D posChest;
	public CoffreHandler(Chest Chest,Point2D posChest){
		this.chest=Chest;
		this.posChest = posChest;
	}
	public void handle(ActionEvent ActionEvent) {
				
					Item item2 = chest.getLoot();
					String b;
					if(item2 instanceof Consumable) {
						b=" dans ce coffre veux tu l'utiliser ?";
					}else {
						b=" dans ce coffre veux tu l'�quiper ?";
					}
					String a="tu as trouve "+item2.getName()+b;
					int positionItem =RPGApp.hero.getPositionVoid();
					if (item2 instanceof Consumable) {
						Consumable item = (Consumable) item2;
						item.effect();
						RPGApp.hero.removeItemInventory(item);
						DisplayInventaire.updateInventaire("remove", item,item.getPosition());
					}else {
						Equipment item=(Equipment) item2;
					if (RPGApp.hero.getEquipement().get(item.getType()) != null) {	
						
						RPGApp.hero.getEquipement().get(item.getType()).setPosition(positionItem);
						DisplayInventaire.updateInventaire("ajout",RPGApp.hero.getEquipement().get(item.getType()),positionItem);
						DisplayEquipment.updateEquipment("remove",  RPGApp.hero.getEquipement().get(item.getType()));
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.equip(item);
					}else {
						RPGApp.hero.equip(item);
						DisplayEquipment.updateEquipment("ajout",  item);
						
						
					}}
					FXGL.getApp().getGameWorld().getEntitiesAt(posChest).get(0).setViewFromTexture("Coffre_Ouvert.png");
					
					chest.setLoot(null);
				
				
			}
		
	}

		