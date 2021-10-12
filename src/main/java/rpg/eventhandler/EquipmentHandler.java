package rpg.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import rpg.RPGApp;
import rpg.data.character.Hero;
import rpg.data.elementInteractifs.Equipment;
import rpg.view.DisplayEquipment;
import rpg.view.DisplayInventaire;


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
		Equipment item = hero.getEquipement().get(type);
		String findephrase=" dans ton �quipement veux tu la d�s�quiper ?";
		listButton[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
					item.setPosition(RPGApp.hero.getPositionVoid());
					int pos= RPGApp.hero.getPositionVoid();
					RPGApp.hero.unequip(item);
					DisplayInventaire.updateInventaire("ajout",item,pos);
					
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