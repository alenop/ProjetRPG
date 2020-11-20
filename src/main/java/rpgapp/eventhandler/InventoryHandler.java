package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;


public class InventoryHandler implements EventHandler<MouseEvent> {
	private Entity itemViewGrand;
	private Item item;
	private String choix;
	
	public InventoryHandler(Entity itemViewGrand,Item i,String choix){
		this.itemViewGrand=itemViewGrand;
		this.item=i;
		this.choix=choix;
	}
	
	@Override
	public void handle(MouseEvent event) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		String a=" dans ton inventaire veux tu l'équiper ?";
		if (choix.equals("desequip")){
			a=" dans ton équipement veux tu le déséquiper ?";
		}
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				
				if(choix.equals("equip")) {
					RPGApp.hero.equip(item);
					DisplayInventaire.updateInventaire("remove", item,RPGApp.hero.getPositionItem(item));
					RPGApp.hero.removeItemInventaire(item);
					DisplayEquipment.updateEquipment("ajout",  item);
					
				}else if(choix.equals("desequip")) {
					RPGApp.hero.desequip(item);
					DisplayEquipment.updateEquipment("remove",  item);
					DisplayInventaire.updateInventaire("ajout",item,RPGApp.hero.getPositionVoid());
				}
				
				
			}
		});
		FXGL.getApp().getDisplay().showBox("tu as " + item.getNom() +a ,
				itemViewGrand.getView(), av);
	}

		
	}


