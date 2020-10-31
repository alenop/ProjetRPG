package rpgapp.control;

import java.util.Map;
import java.util.Map.Entry;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.util.Consumer;
import com.almasb.fxgl.util.Predicate;

import character.Etat;
import character.Monstre;
import character.Monstres;
import item.Coffre;
import item.Item;
import item.PNJ;
import item.PNJList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import system.Quest;
import system.Systems;

public class PlayerComponent extends Component {

	private PositionComponent position;
	private String path ;//=(PlayerComponent.getClass().getResource()).toString();

	@Override
	public void onUpdate(double tpf) {
//		if (FXGL.getApp().getGameWorld().getEntitiesAt(position.getValue()).stream()
//				.anyMatch(type -> type.isType(EntityType.Coffre))) {
//			System.out.println("yo");
//		}
	}

	// Les methodes move ne fonctionnent que si "CanMove" est vérifié

	public void moveRight() {
		CheckAction(new Point2D(RPGApp.TILE_SIZE, 0));
	}

	public void moveLeft() {
		CheckAction(new Point2D(-RPGApp.TILE_SIZE, 0));

	}

	public void moveDown() {
		CheckAction(new Point2D(0, RPGApp.TILE_SIZE));

	}

	public void moveUp() {
		CheckAction(new Point2D(0, -RPGApp.TILE_SIZE));
	}

	private boolean checkEntity(Point2D direction, EntityType a) {
		// Vérifie que la case n'est pas un EntityType a
		//Point2D newPosition = position.getValue().add(direction);

//		return FXGL.getApp()
//				.getGameScene()
//				.getViewport()
//				.getVisibleArea()
//				.contains(direction)

//				&&

		return FXGL.getApp().getGameWorld().getEntitiesAt(direction).stream()
				.filter(e -> e.hasComponent(TypeComponent.class)).map(e ->
				e.getComponent(TypeComponent.class))
				.noneMatch(type -> type.isType(a));

	}

	private void CheckAction(Point2D direction) {

		Point2D newPosition = position.getValue().add(direction);
		System.out.println(newPosition);
		System.out.println(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
		if (checkEntity(newPosition, EntityType.BLOC) && checkEntity(newPosition, EntityType.Monstre)
				&& checkEntity(newPosition, EntityType.PNJ) && checkEntity(newPosition, EntityType.Coffre)) {
			try {
				if (checkEntity(new Point2D(position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE),EntityType.Inventaire)==false) {
					for (Entity i:FXGL.getApp().getGameWorld().getEntitiesAt(new Point2D(position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE))) {
						System.out.println(i);
						if (i.isType(EntityType.Inventaire)) {
							i.translate(direction);
							break;
						}
					}
				}
				position.translate(direction);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (checkEntity(newPosition, EntityType.Monstre) == false) {
			Monstre monstre = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getMonsterList().get(newPosition);
			if (monstre.getEtat() == Etat.vivant) {
				Entity monstreview = Entities.builder()
	                    .viewFromTexture("RatCombatGif.gif")
						//.viewFrom
	                    .build();
				EntityView an = monstreview.getView();
				mode_combat2(an,monstre,newPosition,1);
				
			}
			if (monstre.getEtat()==Etat.mort){
				FXGL.getApp().getGameWorld()
				.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
				//position.translate(direction);
			}
		}
		else if (checkEntity(newPosition, EntityType.PNJ) == false) {
			
			//PNJ p = PNJList.pnjList.get(position);
			int i=0;
			Entity item = Entities.builder()
                    .viewFromTexture("PnjFace.png")
                    .build();
			EntityView an = item.getView();
			dialogue(an,i);
			PNJ p =PNJList.pnjList.get(newPosition);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(" Bonjour je suis " + p.getName());
					System.out.println(p.getTexte());
					System.out.println(" quete " + p.getName());
					System.out.println(p.getTexteQueteOk());
					System.out.println(" gain " + p.getName());
					System.out.println(p.getTexteGagne());
					System.out.println(" perdu " + p.getName());
					System.out.println(p.getTextePerd());
					//FXGL.getApp().getDisplay().showMessageBox(p.getTexte());
					
				}
		else if (checkEntity(newPosition, EntityType.Coffre) == false) {
			//EntityView an = FXGL.getApp().getGameWorld().getEntitiesAt(newPosition).get(0).getView();
			Coffre a = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getCoffreList().get(newPosition);
			if (a.getContenu()!=null) {
			Entity item = Entities.builder()
                    .viewFromTexture(a.getContenu().getImage())
					//.viewFrom
                    .build();
			EntityView an = item.getView();
                    //.buildAndAttach(getGameWorld());
			trouveCoffre(an,a,newPosition);
		}
			else {
				FXGL.getApp().getDisplay().showMessageBox("tu as déja ouvert ce coffre");
				}
			}
		if (checkEntity(newPosition, EntityType.Portal) == false) {
			// PortalList.PortalList.get(RPGApp.hero.getCurrentMap()+newPosition.toString())!=null)
			// {
			changeMap(RPGApp.hero.getCurrentMap(), newPosition);
		}
	

	}

	public void changeMap(String a, Point2D b) {

		System.out.println("yo portal");
		EntityView abcd = null;
		if (position.getEntity() != null) {
			abcd = position.getEntity().getView();
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
		position.setValue(RPGApp.ListeMaps.get(map).getPositionHero());
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

	public void dialogue(EntityView an, int i) {
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
	public void trouveCoffre(EntityView an,Coffre a,Point2D c) {
		Button[] av = new Button[2];
		av[0] = new Button("non");
		av[1] = new Button("oui");
		av[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				av[0].getText();
				//FXGL.getApp().getGameScene().addGameView(an);
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
	public void mode_combat1(Monstre monstre,Point2D newPosition, Point2D direction) {
		try {
			Predicate<String> bg = (x) -> x.equals("attaque") || x.equals("defense");
			Consumer<String> c = (x) -> {
				try {
					Systems.Combat(RPGApp.hero, monstre, x);
					if (monstre.getEtat() == Etat.mort) {
						FXGL.getApp().getGameWorld()
								.removeEntities(FXGL.getApp().getGameWorld().getEntitiesAt(newPosition));
						position.translate(direction);// nécéssaire si attaque via boite de dialogue avec arrêt

					}
					// nécéssaire si attaque via le mouvement sans arrêt pour éviter une attaque constante
//					if (monstre.getEtat() == Etat.vivant) {
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//
//					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			};
			
			FXGL.getApp().getDisplay().showInputBox("Vous avez lancé un combat avec le monstre", bg, c);
		} catch (Exception e) {
		}
	}
	public void mode_combat2(EntityView an,Monstre a,Point2D c,int nb_tour) {
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
					EntityView ann = item.getView();
					//FXGL.getApp().getDisplay().showMessageBox(a.getNom()+" est vaincu !");
					mode_combat2(ann,a,c,0);
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
	public void seeInventaire(String d) {
		EntityView a =RPGApp.hero.getView();
		//a.getOnMouseClicked();
		//class InventoryView(private val player: CharacterEntity) : Parent() {

		    double BG_WIDTH = 64*4;
		    double BG_HEIGHT = 64*4;

		    //val minBtn = MinimizeButton("I", BG_WIDTH - 46.0, -22.0, 0.0, BG_HEIGHT, this)

		    // K - index, V - if free? TODO: double check

		    
		        //relocate(FXGL.getAppWidth() - 200.0, FXGL.getAppHeight() - 240.0);
//		    Entity item = Entities.builder()
//	        		//.viewFromTextureWithBBox(borderShape)
//                    .viewFromTextureWithBBox("RatMort.png")
//                    .build();
		    
		        Rectangle border =new Rectangle(BG_WIDTH, BG_HEIGHT);
		        border.setStrokeWidth (2.0);
		        border.setArcWidth (10.0);
		        border.setArcHeight (10.0);
		        border.setFill(Color.rgb(25, 25, 25, 0.8));
		        
		        
		        

		        Entity item2 = Entities.builder()
		        		//.viewFromTextureWithBBox(borderShape)
	                    //.viewFromTextureWithBBox("RatMort.png")
		        		.type(EntityType.Inventaire)
	                    .at(position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE)
	                    
						//.viewFrom
	                    .build();
		        item2.setViewWithBBox(border);
		        
		        
		        
		        //FXGL.getApp().getGameScene().getViewport().bindToEntity(item2,position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE);
				EntityView ann = item2.getView();
				
				int x=0;
				int y=0;
				for(Item i:RPGApp.hero.getInventaire()) {
					
					if (i!=null) {
						if (x>=4) {
							System.out.println("changement de ligne");
							x=0;
							y=y+1;
						}
					Rectangle border2 =new Rectangle(64, 64);
					Entity item = Entities.builder()
			        		.at(64*x,64*y)
		                    //.viewFromTextureWithBBox(i.getImage())
		                    .build();
					Entity item3 = Entities.builder()
			        		//.at(0,64*x)
		                    .viewFromTextureWithBBox(i.getInventaireImage())
		                    .build();
					Entity item4 = item3.copy();
					item4.setViewFromTexture(i.getImage());
					item.setViewWithBBox(border2);
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
					
					//String path =FXGL.getApp().getGameScene().getClass().getResource("balai.png").toString();	
					//Image image = new Image(path);
					
					//ImageView imageView = new ImageView("src/main/resources/assets/textures/"+i.getImage());
					ImageView imageView = new ImageView("...");
					imageView.setFitHeight(64);
					imageView.setFitWidth(64);
					imageView.setPreserveRatio(true);
					item.setViewWithBBox(imageView);
					item.getView().addNode(item3.getView());
					ann.addNode(item.getView());
					x++;
				}
					}
//				ann.onMouseClickedProperty(){
//					
//				}
		        //FXGL.getApp().getGameScene().addGameView(view);
				
		        
		     
				if (d.equals("off")) {
					
					for (Entity i:FXGL.getApp().getGameWorld().getEntitiesAt(new Point2D(position.getX()+RPGApp.TILE_SIZE,position.getY()+RPGApp.TILE_SIZE))) {
						System.out.println(i);
						if (i.isType(EntityType.Inventaire)) {
							FXGL.getApp().getGameWorld().removeEntity(i);
							break;
						}
					}
				}else {
					FXGL.getApp().getGameWorld().addEntity(item2);
		        //FXGL.getApp().getGameScene().addGameView(ann);//add(borderShape);
				}
		        
		        //children.addAll(borderShape, background, equipView, itemGroup, minBtn)
		        
		        Text text = FXGL.getUIFactory().newText("", Color.WHITE, 12.0);
		                //text.textProperty().bind(player.inventory.getData(item).get().quantityProperty().asString())
		                text.setStrokeWidth(1.5);
		                

		                //view.children.addAll(text);
		       
	

}}
//animationBuilder()
//.duration(Duration.seconds(0.33))
//.animate(bg.fillProperty())
//.from(Color.GOLD)
//.to(Color.BLACK)
//.buildAndPlay()
//animationBuilder()
//.duration(Duration.seconds(0.33))
//.translate(root)
//.from(if (isMinimized) Point2D(0.0, minimizedY) else Point2D(0.0, 0.0))
//.to(if (isMinimized) Point2D(0.0, 0.0) else Point2D(0.0, minimizedY))
//.buildAndPlay()
