package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Chest;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;

public class CoffreHandler implements EventHandler<ActionEvent> {

	private Entity itemViewGrand;
	private Chest chest;
	private Point2D posChest;
	public CoffreHandler(Chest Chest,Point2D posChest){
		this.chest=Chest;
		this.posChest = posChest;
	}
	public void handle(ActionEvent ActionEvent) {
				
					Item item = chest.getLoot();
					String a="tu as trouve "+item.getName()+" dans ce coffre veux tu l'équiper ?";
					int positionItem =RPGApp.hero.getPositionVoid();
					if (RPGApp.hero.getEquipement().get(item.getType()) != null) {	
						
						RPGApp.hero.getEquipement().get(item.getType()).setPosition(positionItem);
						DisplayInventaire.updateInventaire("ajout",RPGApp.hero.getEquipement().get(item.getType()),positionItem);
						DisplayEquipment.updateEquipment("remove",  RPGApp.hero.getEquipement().get(item.getType()));
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.equip(item);
					}else {
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.equip(item);
						
					}
					FXGL.getApp().getGameWorld().getEntitiesAt(posChest).get(0).setViewFromTexture("Coffre_Ouvert.png");
					
					chest.setLoot(null);
				
				
			}
		
	}

		