package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import rpgapp.RPGApp;
import rpgapp.data.character.Hero;
import rpgapp.data.elementInteractifs.Consumable;
import rpgapp.data.elementInteractifs.Equipment;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;


public class InventoryHandler implements EventHandler<MouseEvent> {
	private Entity itemViewGrand;
	private int pos;
	
	public InventoryHandler(Entity itemViewGrand,int pos){
		this.itemViewGrand=itemViewGrand;
		this.pos=pos;
	}
	
	@Override
	public void handle(MouseEvent event) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		Hero hero = RPGApp.hero;
		String a;
		Item item2 = hero.getInventory()[pos];
		if(hero.getInventory()[pos] instanceof Consumable) {
			a=" dans ton inventaire veux tu l'utiliser ?";
		}else {
			a=" dans ton inventaire veux tu l'équiper ?";
		}
		
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
					if(item2 instanceof Consumable) {
						Consumable item = (Consumable)item2;
						item.effect();
						RPGApp.hero.removeItemInventory(item);
						DisplayInventaire.updateInventaire("remove", item,item.getPosition());
					}else {
					Equipment item = (Equipment) item2;
					if (RPGApp.hero.getEquipement().get(item.getType()) != null) {	
						
						DisplayInventaire.updateInventaire("remove", item,pos);
						RPGApp.hero.getEquipement().get(item.getType()).setPosition(pos);
						DisplayInventaire.updateInventaire("ajout",RPGApp.hero.getEquipement().get(item.getType()),pos);
						DisplayEquipment.updateEquipment("remove",  RPGApp.hero.getEquipement().get(item.getType()));
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.removeItemInventory(item);
						RPGApp.hero.equip(item);
						
					}else {
						DisplayInventaire.updateInventaire("remove", item,item.getPosition());
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.removeItemInventory(item);
						RPGApp.hero.equip(item);
						
					}
				
				
				
			}}
		});
		FXGL.getApp().getDisplay().showBox("tu as " + item2.getName() +a ,
				itemViewGrand.getView(), av);
	}

		
	}


