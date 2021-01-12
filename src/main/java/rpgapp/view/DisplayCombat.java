package rpgapp.view;

import java.math.BigDecimal;


import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;


import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Character;

import rpgapp.data.character.Monstre;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.eventhandler.CombatEventHandler;
import rpgapp.eventhandler.GameOverHandler;


public abstract class DisplayCombat extends DisplayBasic {
	
	public static Music musicBoss;

	public static void begin(Monstre monstre, Point2D posMonstre) {
		RPGApp.move=false;
		Entity monstreEntity = CreateEntityWithPicture(monstre.getTypeMonstre().name()+"CombatGif.gif", 64*8 +32, 64);
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
		Label label = new Label("yo");
		label.setTextFill(Color.rgb(254, 254, 254));

		//Text text2 = FXGL.getUIFactory().newText(text, Color.WHITE, 10.0);
		Entity text = CreateEntityWithNode(label, 352, 0);
		text.getView().setUserData(text);
		text.getView().setAccessibleText("text");

		EntityView afficheText = text.getView();
		Button[] av;
		av = new Button[4];
		av[0] = new Button("attaque");
		av[1] = new Button("défense");
		av[2] = new Button("fuir");
		av[3] = new Button("skills");
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

	public static void addbarreVie(EntityView i, Character character, double a, double b) {

		double pourcentage2 = ((character.getPvMax() - character.getPv()) * 100 / character.getPvMax());
		BigDecimal pourcentage3 = new BigDecimal(Double.toString(pourcentage2));
		double pourcentage = pourcentage3.doubleValue();
		Rectangle border1 = createBorder(Math.round((192 * (100 - pourcentage)) / 100), 16);
		border1.setFill(Color.rgb(0, 254, 0, 0.8));
		border1.setArcHeight(0.0);
		border1.setArcWidth(0.0);

		Rectangle border2 = createBorder(Math.round((192 * (pourcentage) / 100)), 16);
		border2.setFill(Color.rgb(254, 0, 0, 0.8));
		border2.setArcHeight(0.0);
		border2.setArcWidth(0.0);
		createRectangleWithBorder(border1, new Point2D(0, 0));
		createRectangleWithBorder(border2, new Point2D(128, 0));
		Entity RedBar = createRectangleWithBorder(border2, new Point2D(a + 192 * (100 - (pourcentage)) / 100, b));
		RedBar.setProperty("border", border2);
		RedBar.getView().setAccessibleText("border1" + character.toString());
		RedBar.getView().setUserData(RedBar);
		Entity GreenBar = createRectangleWithBorder(border1, new Point2D(a, b));
		GreenBar.setProperty("border", border1);
		GreenBar.getView().setAccessibleText("border2" + character.toString());
		GreenBar.getView().setUserData(GreenBar);
		i.addNode(RedBar.getView());
		i.addNode(GreenBar.getView());
	}

	public static void updateBarreVie(Character character, Entity i, String color) {
		double pourcentage2 = ((character.getPvMax() - character.getPv()) * 100 / character.getPvMax());
		BigDecimal pourcentage3 = new BigDecimal(Double.toString(pourcentage2));
		double pourcentage = pourcentage3.doubleValue();

		if (color.equals("red")) {
			((Rectangle) i.getPropertyOptional("border").get()).setWidth(Math.round((192 * (pourcentage)) / 100));
			if (character instanceof Monstre) {

				i.setPosition(64 * 8 + 32 + 192 * (100 - (pourcentage)) / 100, 32);
			} else {
				i.setPosition(192 + 192 * (100 - (pourcentage)) / 100, 185);
			}
		} else {
			((Rectangle) i.getPropertyOptional("border").get()).setWidth(Math.round((192 * (100 - pourcentage)) / 100));
		}
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

				if (i.getAccessibleText().equals("border1" + monstre.toString())) {

					DisplayCombat.updateBarreVie(monstre, ((Entity) i.getUserData()), "red");
				} else if (i.getAccessibleText().equals("border2" + monstre.toString())) {

					DisplayCombat.updateBarreVie(monstre, ((Entity) i.getUserData()), "green");
				} else if (i.getAccessibleText().equals("border1" + RPGApp.hero.toString())) {

					DisplayCombat.updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "red");
				} else if (i.getAccessibleText().equals("border2" + RPGApp.hero.toString())) {

					DisplayCombat.updateBarreVie(RPGApp.hero, ((Entity) i.getUserData()), "green");
				}

			}
		}

	}
}
