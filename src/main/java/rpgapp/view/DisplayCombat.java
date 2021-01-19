package rpgapp.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.*;

import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Character;

import rpgapp.data.character.Monstre;
import rpgapp.data.character.Skill;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.eventhandler.CombatEventHandler;
import rpgapp.eventhandler.GameOverHandler;


public abstract class DisplayCombat extends DisplayBasic {
	
	public static Music musicBoss;

	public static void begin(Monstre monstre, Point2D posMonstre) {
		RPGApp.move=false;
		Entity monstreEntity = CreateEntityWithPicture(//monstre.getTypeMonstre().name()+
				"RatCombatGif.gif", 64*8 +32, 64);
		EntityView monstreview = monstreEntity.getView();
		MusicComponent.musicPlay("battle");
		
		monstreview.setAccessibleText("monstre");
		monstreview.setUserData(monstreEntity);
		Entity hero = CreateEntityWithPicture("heroCombatPoing.gif", 192, 192);
		EntityView heroview = hero.getView();
		heroview.setUserData(hero);
		heroview.setAccessibleText("hero");

		// Display.mode_combat2(monstreview,a,b,1,heroview);
		Rectangle border = createBorder(FXGL.getSettings().getWidth(),FXGL.getSettings().getHeight());
		border.setFill(Color.rgb(0, 0, 0));
		Entity viewcombat = createRectangleWithBorder(border,
				new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
						PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2));
		addbarreVie(viewcombat.getView(), RPGApp.hero, 192, 185);
		addbarreVie(viewcombat.getView(), monstre, 64 * 8 + 32, 32);
		addbarreMana(viewcombat.getView(), RPGApp.hero, 192, 185+20);
		Label label = new Label("yo");
		label.setTextFill(Color.rgb(254, 254, 254));

		//Text text2 = FXGL.getUIFactory().newText(text, Color.WHITE, 10.0);
		Entity text = CreateEntityWithNode(label, 352, 0);
		text.getView().setUserData(text);
		text.getView().setAccessibleText("text");

		EntityView afficheText = text.getView();
		Button[] av;
		av = new Button[3];
		av[0] = new Button("attaque");
		av[1] = new Button("défense");
		av[2] = new Button("fuir");
		//av[3] = new Button("skills");
		av[2].setOnAction(new CombatEventHandler("fuir",viewcombat));
		int j = 0;
		
		for (Button i : av) {
			Entity Bouton = CreateEntityWithNode(i, 192 + 64 * j, 385);
			Bouton.setProperty("bouton", i);
			Bouton.getView().setUserData(Bouton);
			Bouton.getView().setAccessibleText(i.getText());
			viewcombat.getView().addNode(Bouton.getView());
			j++;
		}
		viewcombat.getView().addNode(afficheText);
		viewcombat.getView().addNode(monstreview);
		viewcombat.getView().addNode(heroview);

		FXGL.getApp().getGameWorld().addEntity(viewcombat);
		mode_combat2(viewcombat, monstre, posMonstre, 1);
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

	public static void mode_combat2(Entity viewcombat, Monstre monstre, Point2D posMonstre, int nb_tour) {
		String text;
		System.out.println(monstre.getPv()+"/"+RPGApp.hero.getPv());
		if (nb_tour == 1) {
			text = "Tu as trouvé " + monstre.getName() + " que veut tu faire ?";
		} else if (nb_tour == 2) {
			text = "Attention " + monstre.getName() + " prépare une grosse attaque";
		} else {
			text = monstre.getName() + " est encore vivant que veut tu faire ?";
		}
		if (nb_tour == 0) {
			text = monstre.getName() + " est mort !\nBravo tu as gagné " + monstre.getGive_experience()
					+ " points d'expérience";
			RPGApp.hero.gainExp(monstre.getGive_experience());
		} 
		if(nb_tour == -1) {
			text="Tu est mort veux tu recommencer ?";
		}
		boolean barre=true;
		ComboBox comboBox = createCombobox();
		
		Entity skills = CreateEntityWithNode(comboBox, 192 + 64 * 4, 385);
		skills.getView().setUserData(comboBox);
		skills.getView().setAccessibleText("Skill");
		comboBox.setUserData(skills);
		viewcombat.getView().addNode(skills.getView());
			
		for (Node i : viewcombat.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (i.getAccessibleText().equals("text")) {
					((Label) ((Entity) i.getUserData()).getView().getNodes().get(0)).setText(text);
				} else if (i.getAccessibleText().equals("monstre")&& nb_tour==0) {			
						//((Entity) i.getUserData()).setViewFromTexture(monstre.getTypeMonstre().name()+"Mort.png");
						((Entity) i.getUserData()).setViewFromTexture("Victory.gif");
			
				}else if (i.getAccessibleText().equals("hero")) {	
					if (nb_tour==-1) {
					((Entity) i.getUserData()).setViewFromTexture("GameOver.gif");
					}else if(nb_tour==1) {
						Item arme = RPGApp.hero.getEquipement().get("Arme");
						if (arme != null) {
							((Entity) i.getUserData()).setViewFromTexture("heroCombat"+arme.getName()+".gif");
						}
						else {
							((Entity) i.getUserData()).setViewFromTexture("heroCombatPoing.gif");
						}
						
					}
				}else if (nb_tour == 0) {
					i.setVisible(false);
				}
				if (i.getAccessibleText().equals("attaque")) {
					if (nb_tour==1) {
						((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get()).setText("attaque");
					}
					if(nb_tour==-1) {
						Button retry=((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get());
						retry.setText("Retry");
						retry.setOnAction(new CombatEventHandler(monstre, nb_tour, viewcombat, posMonstre,"retry"));
					}
					else if (nb_tour == 0) {
						i.setVisible(false);
					} else {
						((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get())

								.setOnAction(new CombatEventHandler(monstre, nb_tour, viewcombat, posMonstre,"attaque"));
					}
				}

				else if (i.getAccessibleText().equals("défense")) {
					if (nb_tour==1) {
						((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get()).setText("défense");
					}
					if (nb_tour==-1) {
						Button quit=((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get());
						quit.setText("quit");
						quit.setOnAction(new GameOverHandler());
					}
					else if (nb_tour == 0) {
						i.setVisible(false);
					} else {
						((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get())

						.setOnAction(new CombatEventHandler(monstre, nb_tour, viewcombat, posMonstre,"défense"));
					}
				}

				else if (i.getAccessibleText().equals("fuir")) {
					if (nb_tour==1) {
						i.setVisible(true);
					}
					if (nb_tour==-1) {
						i.setVisible(false);
					}
					else if (nb_tour == 0) {
						i.setVisible(true);
						((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get()).setText("partir");
						((Button) ((Entity) i.getUserData()).getPropertyOptional("bouton").get())
						.setOnAction(new CombatEventHandler("partir",viewcombat));
					} 

								
					
				}
				else if(i.getAccessibleText().equals("Skill")) {
					if (nb_tour==1) {
						i.setVisible(true);
					}
					if (nb_tour==-1 || nb_tour==0) {
						i.setVisible(false);
					}
					 else {
						ComboBox combobox =((ComboBox)  i.getUserData());
					
						combobox.setOnAction(new CombatEventHandler(monstre, nb_tour, viewcombat, posMonstre,"skills"));
					}
				}

				if (i.getAccessibleText().equals("border1" + monstre.toString())) {

					DisplayCombat.updateBarreVie(monstre, ((Entity) i.getUserData()), "red");
				} else if (i.getAccessibleText().equals("border2" + monstre.toString())) {

					DisplayCombat.updateBarreVie(monstre, ((Entity) i.getUserData()), "green");
				} else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre) {
					System.out.println("yo1");
					DisplayCombat.updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "red");
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre) {
					barre=false;
					System.out.println("yo2");
					DisplayCombat.updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "green");
				}else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString()) && barre==false) {
					System.out.println("yo3");
					DisplayCombat.updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "red");
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString()) && barre==false) {
					System.out.println("yo4");
					DisplayCombat.updateBarreMana(RPGApp.hero, ((Entity) i.getUserData()), "blue");
				}

			}
		}

	}
	public static ComboBox createCombobox() {
		ComboBox<String> comboBox = new ComboBox<String>();
		ArrayList<String> disabledItems = new ArrayList<String>();
		comboBox.setValue("Skills");
		for (Skill i : RPGApp.hero.getSkills()) {
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

	 
}
