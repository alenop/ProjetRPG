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
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		Hero hero = RPGApp.hero;
		Item item = hero.getEquipement().get(type);
		String a=" dans ton équipement veux tu la déséquiper ?";
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
					item.setPosition(RPGApp.hero.getPositionVoid());
					DisplayInventaire.updateInventaire("ajout",item,RPGApp.hero.getPositionVoid());
					RPGApp.hero.desequip(item);
					DisplayEquipment.updateEquipment("remove",  item);
					
				}
				
				
			});
		FXGL.getApp().getDisplay().showBox("tu as " + item.getNom() +a ,
				itemViewGrand.getView(), av);
	}

		
	}