package rpg.view;

import java.util.HashMap;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rpg.RPGApp;
import rpg.control.PlayerComponent;
import rpg.data.elementInteractifs.PNJ;
import rpg.eventhandler.PnjEventHandler;

public class DisplayPNJ extends DisplayBasic {
	private static Entity dialogBox;
	public static void init(PNJ pnj) {
		RPGApp.move=false;
		if (RPGApp.hero.getFinishQuests().contains(pnj.getQuest().getName())) {
			DisplayPNJ.dialogue2( pnj,"finish");
			
		}else if(RPGApp.hero.getCurrentquest()!=null && RPGApp.hero.getCurrentquest().getName().equals(pnj.getQuest().getName() )) {
			DisplayPNJ.dialogue2(pnj,"en cours");
			
			}
		else {
			DisplayPNJ.dialogue2(pnj,"begin");
			
		}
	}
	public static void dialogue2(PNJ pnj,String étape) {
		Button[]listButton;
		HashMap<String, String[]> chat =pnj.getListChat().get(étape);
		String[] textButton =chat.get("answers");
		listButton = new Button[textButton.length];
			
			for (int i=0;i<textButton.length;i++) {
				listButton[i] =  new Button(textButton[i]);
				listButton[i].setOnAction(new PnjEventHandler(listButton[i],pnj));
			}
		if (RPGApp.dialogBox==null) {
		Entity pnjentity = Entities.builder()
                .viewFromTexture(pnj.getImage())
                .at(0,0)
                .build();
		EntityView pnjview = pnjentity.getView();
		RPGApp.dialogBox=initDialogBox(pnjview,pnj.getName(),listButton,chat.get("message")[0]);
		}else {
		showBox(RPGApp.dialogBox,listButton,chat.get("message")[0]);
		}}
	public static void tourner(Point2D posPNJ, String angle) {
		String nouvAngle = "";
		String nom = RPGApp.ListeMaps.get(RPGApp.hero.getCurrentMap()).getPNJList().get(posPNJ).getName();
		switch(angle) {
			case "Dos":
				nouvAngle = "Face";
				break;
			case "Face":
				nouvAngle = "Dos";
				break;
			case "Gauche":
				nouvAngle = "Droite";
				break;
			case "Droite":
				nouvAngle = "Gauche";
				break;
		}
		FXGL.getApp().getGameWorld().getEntitiesAt(posPNJ).get(0).setViewFromTexture(nom + "_" + nouvAngle + ".png");
		
	}
	
	private static Entity initDialogBox(EntityView pnjview2,String pnjname,Button[] av,String text) {
		Rectangle border = createBorder(FXGL.getSettings().getWidth(),192);
		border.setFill(Color.rgb(0, 0, 0));
		Entity dialogBox = createRectangleWithBorder(border,
				new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
						PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2));
		Label label = new Label("Dialogue entre "+pnjname+" et vous");
		label.setTextFill(Color.rgb(254, 254, 254));
		Label label2 = new Label(text);
		Text text2 = FXGL.getUIFactory().newText(pnjname+" :", Color.WHITE, 20.0);
		Text text4 = FXGL.getUIFactory().newText(text, Color.WHITE, 15.0);
		
		Entity text3 = CreateEntityWithNode(text2, FXGL.getSettings().getWidth()/2-192, 32);
		Entity dialog = CreateEntityWithNode(text4, FXGL.getSettings().getWidth()/2-192,64);
		dialog.getView().setUserData(dialog);
		dialog.getView().setAccessibleText("dialog");
		dialogBox.getView().addNode(dialog.getView());
		EntityView afficheText = text3.getView();
		int j = 0;
		int a=0;
		String[] li=text.split("");
		int g=1;
		for (int h=0;h<text.length();h++) {
			if (li[h].equals("\n")){
				g++;
			}}
		for (Button i : av) {
			Entity Bouton = CreateEntityWithNode(i,FXGL.getSettings().getWidth()/2- 192 + 64 * j+4*a,20*g+60);
			a=i.getText().length();
			Bouton.setProperty("bouton", i);
			Bouton.getView().setUserData(Bouton);
			Bouton.getView().setAccessibleText("bouton");
			dialogBox.getView().addNode(Bouton.getView());
			j++;
		}

		dialogBox.getView().addNode(afficheText);
		
		dialogBox.getView().addNode(pnjview2);
		FXGL.getApp().getGameWorld().addEntity(dialogBox);
		return dialogBox;
	}
	private static void showBox(Entity dialogBox,Button[] av,String text) {
		int j=0;
		for (Node i : dialogBox.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (i.getAccessibleText().equals("dialog")) {
					Entity a = (Entity) i.getUserData();
					Text b = (Text) a.getView().getNodes().get(0);
					b.setText(text);
				}
				if (i.getAccessibleText().equals("bouton")) {
					Entity a = (Entity) i.getUserData();
					String[] li=text.split("");
					int g=1;
					for (int h=0;h<text.length();h++) {
						if (li[h].equals("\n")){
							g++;
						}}
					a.setY(g*20+60);
					if (j<av.length) {
						if (a.getView().isVisible()==false) {
							a.getView().setVisible(true);
						}
						a.setView(av[j]);
					}else {
						if(a.getView().isVisible()) {
						a.getView().setVisible(false);
						}
					}
					j++;}
			}
	}
}}
