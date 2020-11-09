package rpgapp.view;

import java.util.Map;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Etat;
import rpgapp.data.character.Monstre;
import rpgapp.data.character.Monstres;
import rpgapp.data.elementInteractifs.Coffre;
import rpgapp.system.Quest;
import rpgapp.system.Systems;
import rpgapp.data.elementInteractifs.Item;

public abstract class Display {

	public static void mode_combat2(EntityView an,Monstre a,Point2D c,int nb_tour) {
		Button[] av;
		String text;
		if (nb_tour==1) {
		text= "tu as trouve "+a.getNom()+" que veut tu faire ?";
		}else if(nb_tour==2) {
			text="Attention "+a.getNom()+" prépare une grosse attaque";
		}else {
			text=a.getNom()+" est encore vivant que veut tu faire ?";
		}
		if (nb_tour==2) {
			a.setAtk(a.getAtk()*2);
		}
		if(nb_tour==0) {
			
			text=a.getNom()+" est mort !";
			av = new Button[1];
			av[0] = new Button("ok");
			av[0].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent ActionEvent) {
					if (RPGApp.hero.getCurrentquest().verifQuest()) {
						RPGApp.notif = createNotif("Quest succeed");
						FXGL.getApp().getGameWorld().addEntity(RPGApp.notif);
					}
				}
			});
			}
		else {
			av = new Button[3];
			av[0] = new Button("attaque");
			av[1] = new Button("défense");
			av[2] = new Button("fuir");
		
		av[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				try {
					Systems.Combat(RPGApp.hero, a, "attaque");
					if (nb_tour==2) {
						a.setAtk(a.getAtk()/2);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}if (a.getEtat()==Etat.mort) {
					FXGL.getApp().getGameWorld().getEntitiesAt(c).get(0).setViewFromTexture("RatMort.png");
					Entity item = Entities.builder()
		                    .viewFromTexture("RatMortGrand.png")
							//.viewFrom
		                    .build();
					EntityView inventaireView = item.getView();
					//FXGL.getApp().getDisplay().showMessageBox(a.getNom()+" est vaincu !");
					mode_combat2(inventaireView,a,c,0);
				}else {
					mode_combat2(an,a,c,nb_tour+1);
				}
				
				
			}
		});
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				try {
					Systems.Combat(RPGApp.hero, a, "defense");
					if (nb_tour==2) {
						a.setAtk(a.getAtk()/2);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mode_combat2(an,a,c,nb_tour+1);
				
				
				
			}
		});}
		FXGL.getApp().getDisplay().showBox(text, an, av);
	}
	public static void removeEntity(Point2D a,EntityType b) {
		Entity i =trouveEntity(a,b);
		FXGL.getApp().getGameWorld().removeEntity(i);
	}
	public static void removeInventaire() {
		removeEntity(new Point2D(PlayerComponent.position.getX()+RPGApp.TILE_SIZE,PlayerComponent.position.getY()+RPGApp.TILE_SIZE),EntityType.Inventaire);
	}
	public static void seeInventaire() {
		


		    double BG_WIDTH = 64*4;
		    double BG_HEIGHT = 64*4;

		    
		        Entity inventaire = createRectangle(BG_WIDTH,BG_HEIGHT,new Point2D(PlayerComponent.position.getX()+RPGApp.TILE_SIZE,PlayerComponent.position.getY()+RPGApp.TILE_SIZE));
		        
		        inventaire.setType(EntityType.Inventaire);
				EntityView inventaireView = inventaire.getView();
				
				int x=0;
				int y=0;
				for(Item i:RPGApp.hero.getInventaire()) {
					
					if (i!=null) {
						if (x>=4) {
							x=0;
							y=y+1;
						}
					Entity item = createRectangle(64,64,new Point2D(64*x,64*y));
					Entity item3 = Entities.builder()
			        		//.at(0,64*x)
		                    .viewFromTextureWithBBox(i.getInventaireImage())
		                    .build();
					Entity item4 = item3.copy();
					item4.setViewFromTexture(i.getImage());
					item.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent MousenEvent) {
				Button[] av = new Button[2];
				av[0] = new Button("non");
				av[1] = new Button("oui");
				av[0].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent ActionEvent) {
						av[0].getText();
						//FXGL.getApp().getGameScene().addGameView(an);
						
						
					}
				});
				av[1].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent ActionEvent) {
						RPGApp.hero.equip(i);
						RPGApp.hero.removeItemInventaire(i);
						//FXGL.getApp().getGameScene().addGameView(an);
						
						
						
					}});
					FXGL.getApp().getDisplay().showBox("tu as "+i.getNom()+" dans ton inventaire veux tu l'équiper ?", item4.getView(), av);
			}});
				
					item.getView().addNode(item3.getView());
					inventaireView.addNode(item.getView());
					x++;
				}
				}
				
					
				
					FXGL.getApp().getGameWorld().addEntity(inventaire);
				
		        
}
	public static Entity trouveEntity(Point2D c, EntityType a) {
		for (Entity i:FXGL.getApp().getGameWorld().getEntitiesAt(c)) {
			if (i.isType(a)) {
				return i;
			}
		}
		return null;
	}
	public static Entity createNotif(String a) {
		double BG_WIDTH = 64*4;
	    double BG_HEIGHT = 48;

	    
	        Entity notif =createRectangle(BG_WIDTH, BG_HEIGHT,new Point2D(PlayerComponent.position.getX()-RPGApp.TILE_SIZE*3,PlayerComponent.position.getY()-RPGApp.TILE_SIZE*3));
	        Label Label = new Label(a);
	        Label.setTextFill(Color.rgb(254, 254, 254, 0.8));
	        Text text = FXGL.getUIFactory().newText(a, Color.WHITE, 30.0);
	        
	        Label.setStyle("fx-font-size: 200;");
	        

	       
	        Entity inventaire2 = Entities.builder()
                    .at(16,32)
                    .build();
	        inventaire2.setViewWithBBox(text);
	        EntityView abd =inventaire2.getView();
			EntityView inventaireView = notif.getView();
			inventaireView.addNode(abd);
			return notif;
			
			
	}
	private static  Entity createRectangle(double a,double b,Point2D c) {
	    
	        Rectangle border =new Rectangle(a,b);
	        border.setStrokeWidth (2.0);
	        border.setArcWidth (10.0);
	        border.setArcHeight (10.0);
	        border.setFill(Color.rgb(25, 25, 25, 0.8));
	        

	        Entity rectangleView = Entities.builder()
                    .at(c)
                    .build();
	        rectangleView.setViewWithBBox(border);
	        return rectangleView;
	}
	public static void dialogue(EntityView an, int i) {
		String[] text = new String[7];
		text[0] = "Pourquoi ?";
		text[1] = "oui papa";
		text[2] = "Va tuer la souris";
		text[3] = "oui papa";
		text[4] = "oui papa";
		text[5] = "Elle mange tout mon fromage dépêche toi !";
		Button[]av;
		if (text[i].equals(text[i+1])) {
			av = new Button[1];
		}else {
			av = new Button[2];
			av[1] = new Button(text[i]);
			av[1].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent ActionEvent) {
					if (av[1].getText().equals("oui papa")) {
						RPGApp.hero.setCurrentquest(new Quest("kill souris",5000,Monstres.Souris,1));
						FXGL.getApp().getDisplay().showMessageBox("elle est dans le jardin !");
					}else {
					dialogue(an, i + 3);
					}
		}});}
		
		
		av[0] = new Button(text[i+1]);
		
		av[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				if (av[0].getText().equals("oui papa")) {
				RPGApp.hero.setCurrentquest(new Quest("kill souris",5000,Monstres.Souris,1));
				FXGL.getApp().getDisplay().showMessageBox("elle est dans le jardin !");
				}
				//dialogue(an, i + 2);
			}
		});
		FXGL.getApp().getDisplay().showBox(text[i + 2], an, av);
	}
	public static void changeMap(String a, Point2D b) {
		 
		System.out.println("yo portal");
		EntityView abcd = null;
		if (PlayerComponent.position.getEntity() != null) {
			abcd = PlayerComponent.position.getEntity().getView();
			RPGApp.hero.setView(abcd);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String map = RPGApp.ListeMaps.get(a).getPortalList().get(b);
		FXGL.getApp().getGameWorld().setLevelFromMap(map);
		RPGApp.hero.setCurrentMap(map);
		PlayerComponent.position.setValue(RPGApp.ListeMaps.get(map).getPositionHero());
		if (abcd != null) {
			FXGL.getApp().getGameScene().addGameView(abcd);
		}

		if (RPGApp.ListeMaps.get(map) != null) {
			for (Map.Entry<Point2D, String> i : RPGApp.ListeMaps.get(map).getPortalList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("portal", i.getKey());

			}
			for (Map.Entry<Point2D, Monstre> i : RPGApp.ListeMaps.get(map).getMonsterList().entrySet()) {
				FXGL.getApp().getGameWorld().spawn("monstre", i.getKey());
				i.getValue().fullLife();
				i.getValue().setEtat(Etat.vivant);
			}
			for (Entry<Point2D, Coffre> i : RPGApp.ListeMaps.get(map).getCoffreList().entrySet()) {
				a="Image_coffre_";
				if (i.getValue().getContenu()==null) {a+="open.jpg";}else {a+="closed.jpg";}
				FXGL.getApp().getGameWorld().spawn("coffre",i.getKey()).setViewFromTexture(a);
			}
		}

	}
	public static void trouveCoffre(EntityView an,Coffre a,Point2D c) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		av[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				
				FXGL.getApp().getGameWorld().getEntitiesAt(c).get(0).setViewFromTexture("image_coffre_closed.jpg");
				
			}
		});
		av[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				RPGApp.hero.equip(a.getContenu());
				//FXGL.getApp().getGameScene().addGameView(an);
				FXGL.getApp().getGameWorld().getEntitiesAt(c).get(0).setViewFromTexture("image_coffre_open.jpg");
				
				a.setContenu(null);
				
			}
		});
		FXGL.getApp().getDisplay().showBox("tu as trouve "+a.getContenu().getNom()+" dans ce coffre veux tu l'équiper ?", an, av);
	}
}
