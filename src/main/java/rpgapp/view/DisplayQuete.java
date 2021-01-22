package rpgapp.view;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.system.Quest;

public class DisplayQuete extends DisplayBasic {

	
	public static void removeQuete() {
		getQuete().getView().setVisible(false);
		MusicComponent.soundPlay("open");
	}

	public static void afficheQuete() {
		getQuete().getView().setVisible(true);
		MusicComponent.soundPlay("open");
	}
	
	public static Entity getQuete() {
		Entity tabQuete = trouveEntity(new Point2D(PlayerComponent.position.getX() + 3.5*RPGApp.TILE_SIZE,
				PlayerComponent.position.getY() - 5*RPGApp.TILE_SIZE), EntityType.TableauQuete);
		return tabQuete;
	}
	
	public static void updateQuete() {
		
		Quest q = RPGApp.hero.getCurrentquest();
		
		for (Node ligne : getQuete().getView().getNodes()) {
			String nouvText = "";
			String textAT = ligne.getAccessibleText();
			if (textAT != null && q!= null) {
				
				switch (textAT) {
					case "Nom":
						nouvText = q.getName();
						break;
					case "Objectif":
						if(q.getAction() == "Parler à" ) {
							nouvText = "- " + q.getAction() + " " + q.getTypeCibles();
						}
						else {
							nouvText = "- " + q.getAction() + " " + q.getNbCibles() + " / " + q.getNbCiblesMax() + " " + q.getTypeCibles();
						}
						break;
					case "Recompense":
						nouvText = "- " + q.getReward() + " Xp";
						break;
					case "Description":
						nouvText = "- " + q.getDescription();
						break;
					
				}
				
			}
			if(textAT == "Nom" || textAT == "Description" || textAT == "Objectif" || textAT == "Recompense") {
				Entity textEntity = (Entity) ligne.getUserData();
				Text text = (Text) textEntity.getView().getNodes().get(0);
				text.setText(nouvText);
			}
			
		}
			
	}

	
	public static void createQuete() {

		double BG_WIDTH = 64 * 4;
		double BG_HEIGHT = 64 * 3;

		Entity tabQuete = createRectangle(BG_WIDTH, BG_HEIGHT,
				new Point2D(PlayerComponent.position.getX() + 3.5*RPGApp.TILE_SIZE,
						PlayerComponent.position.getY() - 5*RPGApp.TILE_SIZE));
		
		tabQuete.setType(EntityType.TableauQuete);
		tabQuete.setViewFromTexture("QueteFond.png");
		EntityView tabQueteView = tabQuete.getView();

		int x = 0;
		int y = 0;
		Quest q = RPGApp.hero.getCurrentquest();

		
		Label label = new Label("");
		Label label2 = new Label("");
		label.setTextFill(Color.rgb(254, 254, 254));
		label2.setTextFill(Color.rgb(254, 254, 254));
		
		Text nomTxt = FXGL.getUIFactory().newText(""+" ", Color.GOLD, 20.0);
		Text descTxt = FXGL.getUIFactory().newText("", Color.WHITE, 20.0);
		Text objTxt = FXGL.getUIFactory().newText("", Color.WHITE, 20.0);
		Text recTxt = FXGL.getUIFactory().newText("", Color.WHITE, 20.0);
		
		if(q!=null) {
			nomTxt.setText(q.getName());
			descTxt.setText("- "+ q.getDescription());
			if(q.getAction() == "Parler à" ) {
				objTxt.setText("-" + q.getAction() + " " + q.getTypeCibles());
			}
			else {
				objTxt.setText("- "+ q.getAction() + " " + q.getNbCibles() + " / " + q.getNbCiblesMax() + " " + q.getTypeCibles());
			}
			
			recTxt.setText("- " + q.getReward() + " Xp");
		}
		
		
		
		nomTxt.setWrappingWidth(BG_WIDTH-15);
		nomTxt.setFont(Font.font("Elephant", FontWeight.BOLD, FontPosture.REGULAR, 14));
		descTxt.setWrappingWidth(BG_WIDTH-20);
		descTxt.setFont(Font.font("Elephant", FontWeight.BLACK, FontPosture.REGULAR, 11));
		objTxt.setWrappingWidth(BG_WIDTH-20);
		objTxt.setFont(Font.font("Elephant", FontWeight.BLACK, FontPosture.REGULAR, 11));
		recTxt.setWrappingWidth(BG_WIDTH-20);
		recTxt.setFont(Font.font("Elephant", FontWeight.BLACK, FontPosture.REGULAR, 11));
		
		Entity nomQuete = CreateEntityWithNode(nomTxt, 10, 32);
		nomQuete.getView().setUserData(nomQuete);
		
		Entity descQuete = CreateEntityWithNode(descTxt, 15, 105);
		descQuete.getView().setUserData(descQuete);
		
		Entity objQuete = CreateEntityWithNode(objTxt, 15, 60);
		objQuete.getView().setUserData(objQuete);
		
		Entity recQuete = CreateEntityWithNode(recTxt, 15, 80);
		recQuete.getView().setUserData(recQuete);
		
		nomQuete.getView().setAccessibleText("Nom");
		descQuete.getView().setAccessibleText("Description");
		objQuete.getView().setAccessibleText("Objectif");
		recQuete.getView().setAccessibleText("Recompense");
		
		tabQuete.getView().addNode(nomQuete.getView());
		tabQuete.getView().addNode(descQuete.getView());
		tabQuete.getView().addNode(objQuete.getView());
		tabQuete.getView().addNode(recQuete.getView());
		
		
		tabQuete.getView().setVisible(false);
		FXGL.getApp().getGameWorld().addEntity(tabQuete);
	}
}
