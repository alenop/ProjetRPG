package rpgapp.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.util.Callback;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Character;
import rpgapp.data.character.Skill;
import rpgapp.data.character.SkillOutFight;
import rpgapp.data.elementInteractifs.Equipment;
import rpgapp.eventhandler.HeroEventHandler;

public class DisplayHero extends DisplayBasic {
	public static void begin() {
	Entity hero = CreateEntityWithPicture("perso.png", 32, 48);
	EntityView heroview = hero.getView();
	heroview.setUserData(hero);
	heroview.setAccessibleText("hero");
	int space=24;
	// Display.mode_combat2(monstreview,a,b,1,heroview);
	Rectangle border = createBorder(FXGL.getSettings().getWidth()/2,FXGL.getSettings().getHeight()/2);
	border.setFill(Color.rgb(0, 0, 0));
	Entity viewstatus = createRectangleWithBorder(border,
			new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
					PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2));
	viewstatus.setType(EntityType.HeroStatus);
	addbarreVie(viewstatus.getView(), RPGApp.hero, 32, 10);
	addbarreMana(viewstatus.getView(), RPGApp.hero, 32, 30);
	Text niveau = FXGL.getUIFactory().newText("Niveau : "+RPGApp.hero.getLv(), Color.WHITE, 15.0);
	Entity niveauview= CreateEntityWithNode(niveau, 256+16, 32);
	niveauview.getView().setAccessibleText("level");
	niveauview.getView().setUserData(niveau);
	viewstatus.getView().addNode(niveauview.getView());
	Text text = FXGL.getUIFactory().newText("Equipement :", Color.WHITE, 15.0);
	Entity titre1view= CreateEntityWithNode(text, 256+16, 32+28);
	int j=2;
	String[] d = new String[2];
	d[0]="Arme";
	d[1]="Armure";
	for (String b : d) {
		String name="";
		if (RPGApp.hero.getEquipement().containsKey(b)) {
			name=RPGApp.hero.getEquipement().get(b).getName();
		}
		Text equip = FXGL.getUIFactory().newText(b+" : "+name, Color.WHITE, 15.0);
		Entity equipview = CreateEntityWithNode(equip, 256+16,32+space*j );
		equipview.getView().setUserData(equip);
		equipview.getView().setAccessibleText(b);
		viewstatus.getView().addNode(equipview.getView());
		j++;
	}
	Text text2 = FXGL.getUIFactory().newText("Stats :", Color.WHITE, 15.0);
	Entity titre2view= CreateEntityWithNode(text2, 256+16, 32+space*j);
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
		Entity statview = CreateEntityWithNode(stat, 256+16,32+space*j );
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
		MusicComponent.soundPlay("open");
	}
	public static void afficheStatusWindow() {
		trouveEntity(new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
				PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2), EntityType.HeroStatus).getView().setVisible(true);
		MusicComponent.soundPlay("open");
	}
	public static void update() {
		Entity viewstatus=getStatusWindow();
		boolean barre=true;
		updateSkills(viewstatus);
		for (Node i : viewstatus.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (RPGApp.hero.getStats().containsKey(i.getAccessibleText())) {
					updateStats(i);	
				}
				else if(RPGApp.hero.getEquipement().containsKey(i.getAccessibleText())) {
					updateEquipment(i);
				}
				else if(i.getAccessibleText().equals("level")) {
					updateLv(i);
				}
				else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre) {
					updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "red");
					
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre) {
					barre=false;
					updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "green");
					
				}else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre==false) {
					updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "red");
					
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre==false) {
					updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "blue");
				}
			}
	}}
	public static void updateSkills(Entity viewstatus) {
		ArrayList<Skill> liste = new ArrayList<Skill>();
		
		for (Skill i : RPGApp.hero.getSkills()) {
			if(i instanceof SkillOutFight) {
				liste.add(i);
			}
		} 
		ComboBox combo = createComboboxSkill(liste);
		combo.setOnAction(new HeroEventHandler());
		Entity comboEntity = CreateEntityWithNode(combo, 256+16, 32+28*8);
		combo.setUserData(comboEntity);
		viewstatus.getView().addNode(comboEntity.getView());
	}
	public static void updateStats(Node i) {
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
	public static void updateEquipment(Node i) {
		HashMap<String, Equipment> equip=RPGApp.hero.getEquipement();
		((Text) i.getUserData()).setText(i.getAccessibleText()+" : "+equip.get(i.getAccessibleText()).getName());
	}
	public static void updateEquipment(Entity viewstatus) {
		for (Node i : viewstatus.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if(RPGApp.hero.getEquipement().containsKey(i.getAccessibleText())) {
					updateEquipment(i);
				}
			}
	}}
	public static void updateLv(Node i) {
		((Text) i.getUserData()).setText("Niveau : "+RPGApp.hero.getLv());
	}
	public static void updateLv(Entity viewstatus) {
		for (Node i : viewstatus.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (i.getAccessibleText().equals("level")) {
					updateLv(i);
				}
			}}
	}
	public static void updateStats(Entity viewstatus) {
		for (Node i : viewstatus.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (RPGApp.hero.getStats().containsKey(i.getAccessibleText())) {
					updateStats(i);	
				}
			}
	}}
	
	public static void updatebarres(Entity viewstatus) {
		Boolean barre=true;
	
		for (Node i : viewstatus.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
		if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre) {
			updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "red");
			
		} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre) {
			barre=false;
			updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "green");
		}else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre==false) {
			updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "red");
			
		} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre==false) {
			updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "blue");
		}
			
		
	}}}
	public static ComboBox createComboboxSkill(ArrayList<Skill> liste) {
		ComboBox<String> comboBox = new ComboBox<String>();
		ArrayList<String> disabledItems = new ArrayList<String>();
		comboBox.setValue("Skills");
		for (Skill i : liste) {
			comboBox.getItems().add(i.getName()+" "+i.getCost());
			if(i.getCost()>RPGApp.hero.getMp()) {
				disabledItems.add(i.getName()+" "+i.getCost());
			}
		}
		

		comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){
			  @Override
			  public ListCell<String> call(ListView<String> arg0)
			  {
			    return new ListCell<String>()
			    {
			      @Override
			      protected void updateItem(String item, boolean empty)
			      {
			        super.updateItem(item, empty);

			        if (item == null || empty)
			        {
			          setText(null);
			        }
			        else
			        {
			          setText(item);
			          
			          
			          if (disabledItems.contains(item)) {
			        	  setTextFill(Color.GRAY);
			        	  setStyle("-fx-background-color: #A0A0A0;");
			        	  setDisable(true);
			          }
			        }
			      }
			    };
			  }
			});
		return comboBox;
	}
	public static void addbarreMana(EntityView i, Character character, double a, double b) {
		double pourcentage2 = ((character.getMpMax() - character.getMp()) * 100 / character.getMpMax());
		DisplayBasic.addbarre(i, character, a, b, pourcentage2, Color.rgb(0, 0, 254, 0.8));
	}
	public static void updateBarreMana(Character character, Entity i, String color) {
		double pourcentage2 = ((character.getMpMax() - character.getMp()) * 100 / character.getMpMax());
		DisplayBasic.updateBarre(character, i, color, pourcentage2);
	}
	public static void addbarreVie(EntityView i, Character character, double a, double b) {

		double pourcentage2 = ((character.getPvMax() - character.getPv()) * 100 / character.getPvMax());
		DisplayBasic.addbarre(i, character, a, b, pourcentage2, Color.rgb(0, 254, 0, 0.8));
	}
	public static void updateBarreVie(Character character, Entity i, String color) {
		double pourcentage2 = ((character.getPvMax() - character.getPv()) * 100 / character.getPvMax());
		DisplayBasic.updateBarre(character, i, color, pourcentage2);
	}
}
