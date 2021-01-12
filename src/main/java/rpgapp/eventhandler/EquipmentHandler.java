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


public class EquipmentHandler implements EventHandler<MouseEvent> {
	private Entity itemViewGrand;
	private String type;
	private String choix;
	
	public EquipmentHandler(Entity itemViewGrand,String type){
		this.itemViewGrand=itemViewGrand;
		this.type=type;
	}
	
	@Override
	public void handle(MouseEvent event) {
		Button[] listButton = new Button[2];
		listButton[0] = new Button("non");
		listButton[1] = new Button("oui");
		Hero hero = RPGApp.hero;
		Item item = hero.getEquipement().get(type);
		String findephrase=" dans ton équipement veux tu la déséquiper ?";
		listButton[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
					item.setPosition(RPGApp.hero.getPositionVoid());
					DisplayInventaire.updateInventaire("ajout",item,RPGApp.hero.getPositionVoid());
					RPGApp.hero.unequip(item);
					DisplayEquipment.updateEquipment("remove",  item);
					
				}
				
				
			});
		try {
			FXGL.getApp().getDisplay().showBox("tu as " + item.getName() +findephrase ,
					itemViewGrand.getView(), listButton);
		} catch (Exception e) {
			System.out.println("Il n'y a plus aucun objet dans cet emplacement");
		}
		
	}

		
	}