package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import rpgapp.RPGApp;
import rpgapp.data.character.Hero;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;


public class InventoryHandler implements EventHandler<MouseEvent> {
	private Entity itemViewGrand;
	private int pos;
	private String choix;
	
	public InventoryHandler(Entity itemViewGrand,int i,String choix){
		this.itemViewGrand=itemViewGrand;
		this.pos=i;
		this.choix=choix;
	}
	
	@Override
	public void handle(MouseEvent event) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		Hero hero = RPGApp.hero;
		Item item = hero.getInventaire()[pos];
		String a=" dans ton inventaire veux tu l'équiper ?";
		if (choix.equals("desequip")){
			a=" dans ton équipement veux tu le déséquiper ?";
		}
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				
				if(choix.equals("equip")) {
					
					if (RPGApp.hero.getEquipement().get(item.getType()) != null) {	
						
						DisplayInventaire.updateInventaire("remove", item,pos);
						RPGApp.hero.getEquipement().get(item.getType()).setPosition(pos);
						DisplayInventaire.updateInventaire("ajout",RPGApp.hero.getEquipement().get(item.getType()),pos);
						DisplayEquipment.updateEquipment("remove",  RPGApp.hero.getEquipement().get(item.getType()));
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.removeItemInventaire(item);
						RPGApp.hero.equip(item);
						
					}else {
						DisplayInventaire.updateInventaire("remove", item,item.getPosition());
						DisplayEquipment.updateEquipment("ajout",  item);
						RPGApp.hero.removeItemInventaire(item);
						RPGApp.hero.equip(item);
						
					}
				}else if(choix.equals("desequip")) {
//					DisplayInventaire.updateInventaire("ajout",item,RPGApp.hero.getPositionVoid());
//					RPGApp.hero.desequip(item);
//					DisplayEquipment.updateEquipment("remove",  item);
					
				}
				
				
			}
		});
		FXGL.getApp().getDisplay().showBox("tu as " + item.getNom() +a ,
				itemViewGrand.getView(), av);
	}

		
	}


