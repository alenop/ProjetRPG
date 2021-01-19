package rpgapp.view;

import java.util.HashMap;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.PlayerComponent;
import rpgapp.data.elementInteractifs.Equipment;

public class DisplayHero extends DisplayBasic {
	public static void begin() {
	Entity hero = CreateEntityWithPicture("perso.png", 32, 48);
	EntityView heroview = hero.getView();
	heroview.setUserData(hero);
	heroview.setAccessibleText("hero");

	// Display.mode_combat2(monstreview,a,b,1,heroview);
	Rectangle border = createBorder(FXGL.getSettings().getWidth()/2,FXGL.getSettings().getHeight()/2);
	border.setFill(Color.rgb(0, 0, 0));
	Entity viewstatus = createRectangleWithBorder(border,
			new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
					PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2));
	viewstatus.setType(EntityType.HeroStatus);
	DisplayCombat.addbarreVie(viewstatus.getView(), RPGApp.hero, 32, 0);
	DisplayCombat.addbarreMana(viewstatus.getView(), RPGApp.hero, 32, 20);
	Text text = FXGL.getUIFactory().newText("Equipement :", Color.WHITE, 15.0);
	Entity titre1view= CreateEntityWithNode(text, 256+16, 32);
	int j=1;
	String[] d = new String[2];
	d[0]="Arme";
	d[1]="Armure";
	for (String b : d) {
		String name="";
		if (RPGApp.hero.getEquipement().containsKey(b)) {
			name=RPGApp.hero.getEquipement().get(b).getName();
		}
		Text equip = FXGL.getUIFactory().newText(b+" : "+name, Color.WHITE, 15.0);
		Entity equipview = CreateEntityWithNode(equip, 256+16,32+28*j );
		equipview.getView().setUserData(equip);
		equipview.getView().setAccessibleText(b);
		viewstatus.getView().addNode(equipview.getView());
		j++;
	}
	Text text2 = FXGL.getUIFactory().newText("Stats :", Color.WHITE, 15.0);
	Entity titre2view= CreateEntityWithNode(text2, 256+16, 32+28*j);
	viewstatus.getView().addNode(titre2view.getView());
	j++;
	for (Entry<String, Integer> i : RPGApp.hero.getStats().entrySet()) {
		String a="";
		if(i.getKey().equals("pv")) {
			a="/"+RPGApp.hero.getPvMax();
		}
		else if(i.getKey().equals("mp")) {
			a="/"+RPGApp.hero.getMpMax();
		}
		Text stat = FXGL.getUIFactory().newText(i.getKey()+" : "+i.getValue()+a, Color.WHITE, 15.0);
		Entity statview = CreateEntityWithNode(stat, 256+16,32+28*j );
		statview.getView().setUserData(stat);
		statview.getView().setAccessibleText(i.getKey());
		viewstatus.getView().addNode(statview.getView());
		j++;
	}
	
	viewstatus.getView().addNode(titre1view.getView());
	viewstatus.getView().addNode(heroview);
	FXGL.getApp().getGameWorld().addEntity(viewstatus);
	viewstatus.getView().setVisible(false);
	}
	public static Entity getStatusWindow() {
		return trouveEntity(new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
					PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2), EntityType.HeroStatus);
	}
	public static void removeStatusWindow() {
		trouveEntity(new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
				PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2), EntityType.HeroStatus).getView().setVisible(false);
	}
	public static void afficheStatusWindow() {
		trouveEntity(new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
				PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2), EntityType.HeroStatus).getView().setVisible(true);
	}
	public static void update() {
		Entity viewstatus=getStatusWindow();
		boolean barre=true;
		for (Node i : viewstatus.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (RPGApp.hero.getStats().containsKey(i.getAccessibleText())) {
						HashMap<String, Integer> stats = RPGApp.hero.getStats();
						String a="";
						if(i.getAccessibleText().equals("pv")) {
							a="/"+RPGApp.hero.getPvMax();
						}
						else if(i.getAccessibleText().equals("mp")) {
							a="/"+RPGApp.hero.getMpMax();
						}
						((Text) i.getUserData()).setText(i.getAccessibleText()+" : "+stats.get(i.getAccessibleText())+a);
						
					
				}
				else if(RPGApp.hero.getEquipement().containsKey(i.getAccessibleText())) {
					HashMap<String, Equipment> equip=RPGApp.hero.getEquipement();
					((Text) i.getUserData()).setText(i.getAccessibleText()+" : "+equip.get(i.getAccessibleText()).getName());
				}
				else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre) {
					
				
					DisplayCombat.updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "red");
					
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre) {
					barre=false;
					
					DisplayCombat.updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "green");
				}else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre==false) {
					
					DisplayCombat.updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "red");
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre==false) {
					
					DisplayCombat.updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "blue");
				}
			}
	}}
}
