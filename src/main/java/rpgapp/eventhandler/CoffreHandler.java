package rpgapp.eventhandler;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Coffre;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.view.DisplayEquipment;
import rpgapp.view.DisplayInventaire;

public class CoffreHandler implements EventHandler<ActionEvent> {

	private Entity itemViewGrand;
	private Coffre coffre;
	private Point2D c;
	public CoffreHandler(Coffre i,Point2D c){
		this.coffre=i;
		this.c = c;
	}
	public void handle(ActionEvent ActionEvent) {
				
					Item item = coffre.getContenu();
					String a="tu as trouve "+item.getNom()+" dans ce coffre veux tu l'équiper ?";
					int b =RPGApp.hero.getPositionVoid();
					if (RPGApp.hero.getEquipement().get(item.getType()) != null) {	
						DisplayInventaire.updateInventaire("ajout",RPGApp.hero.getEquipement().get(item.getType()),b);
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
					FXGL.getApp().getGameWorld().getEntitiesAt(c).get(0).setViewFromTexture("Coffre_Ouvert.png");
					
					coffre.setContenu(null);
				
				
			}
		
	}

		