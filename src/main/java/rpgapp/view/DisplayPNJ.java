package rpgapp.view;

import java.util.HashMap;

import javax.swing.JLabel;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rpgapp.RPGApp;
import rpgapp.control.MusicComponent;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Monstres;
import rpgapp.data.elementInteractifs.PNJ;
import rpgapp.eventhandler.CombatEventHandler;
import rpgapp.eventhandler.PnjEventHandler;
import rpgapp.system.Quest;

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
		//FXGL.getApp().getDisplay().showBox(chat.get("message")[0], pnjview, listButton);
		if (RPGApp.dialogBox==null) {
		Entity pnjentity = Entities.builder()
                .viewFromTexture(pnj.getImage("Cadre"))
                .at(FXGL.getSettings().getWidth()/2-64, 32)
                .build();
		EntityView pnjview = pnjentity.getView();
		RPGApp.dialogBox=initDialogBox(pnjview,pnj.getName(),listButton,chat.get("message")[0]);
		}else {
		showBox(RPGApp.dialogBox,listButton,chat.get("message")[0]);
		}
	}
	private static Entity initDialogBox(EntityView pnjview2,String pnjname,Button[] av,String text) {
		// Display.mode_combat2(monstreview,a,b,1,heroview);
		Rectangle border = createBorder(FXGL.getSettings().getWidth(),FXGL.getSettings().getHeight());
		border.setFill(Color.rgb(0, 0, 0));
		Entity dialogBox = createRectangleWithBorder(border,
				new Point2D(PlayerComponent.position.getX() - FXGL.getSettings().getWidth()/2,
						PlayerComponent.position.getY() - FXGL.getSettings().getHeight()/2));
		Label label = new Label("dialogue entre "+pnjname+" et vous");
		//label.resize(width, height);
		//label.setMinSize(400,400);
		label.setTextFill(Color.rgb(254, 254, 254));
		Label label2 = new Label(text);
		Text text2 = FXGL.getUIFactory().newText("dialogue entre "+pnjname+" et vous", Color.WHITE, 20.0);
		Text text4 = FXGL.getUIFactory().newText(text, Color.WHITE, 15.0);
		
		Entity text3 = CreateEntityWithNode(text2, FXGL.getSettings().getWidth()/2-192, 32);
		Entity dialog = CreateEntityWithNode(text4, FXGL.getSettings().getWidth()/2-192, 192+128);
		dialog.getView().setUserData(dialog);
		dialog.getView().setAccessibleText("dialog");
		dialogBox.getView().addNode(dialog.getView());
		EntityView afficheText = text3.getView();
		int j = 0;
		int a=0;
		for (Button i : av) {
			System.out.println(i.getText());
			Entity Bouton = CreateEntityWithNode(i,FXGL.getSettings().getWidth()/2- 192 + 64 * j+4*a, 310+128);
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
		//showBox(dialogBox,av,text);
	}
	private static void showBox(Entity dialogBox,Button[] av,String text) {
		int j=0;
		//System.out.println(av.length+"//*"+j);
		for (Node i : dialogBox.getView().getNodes()) {
			if (i.getAccessibleText() != null) {
				if (i.getAccessibleText().equals("dialog")) {
					System.out.println(text);
					Entity a = (Entity) i.getUserData();
					Text b = (Text) a.getView().getNodes().get(0);
					b.setText(text);
				}
				if (i.getAccessibleText().equals("bouton")) {
					Entity a = (Entity) i.getUserData();
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
