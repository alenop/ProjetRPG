package rpgapp.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rpgapp.EntityType;
import rpgapp.RPGApp;
import rpgapp.control.PlayerComponent;
import rpgapp.data.character.Character;
import rpgapp.data.character.Monstre;

public abstract class DisplayBasic {
	public static Entity CreateEntityWithNode(Node a,double b,double c) {
		Entity affiche = Entities.builder()
				.at(b,c)
				.viewFromNode(a)
				.build();
		return affiche;
	}
	public static Entity CreateEntityWithPicture(String a,double b,double c) {
		Entity affiche = Entities.builder()
				.at(b,c)
				.viewFromTexture(a)
				.build();
		return affiche;
	}
	protected static  Rectangle createBorder(double a,double b) {
	    
        Rectangle border =new Rectangle(a,b);
        border.setStrokeWidth (2.0);
        border.setArcWidth (10.0);
        border.setArcHeight (10.0);
        border.setFill(Color.rgb(0, 0, 0,0.8));
        return border;
	}
	protected static  Entity createRectangleWithBorder(Rectangle border,Point2D c) {

        Entity rectangleView = Entities.builder()
                .at(c)
                .build();
        rectangleView.setViewWithBBox(border);
        return rectangleView;
}
	protected static  Entity createRectangleWithBorder(Rectangle border,Point2D c,EntityType type) {

        Entity rectangleView = Entities.builder()
                .at(c)
                .type(type)
                .build();
        rectangleView.setViewWithBBox(border);
        return rectangleView;
}
	protected static  Entity createRectangle(double a,double b,Point2D c) {
	    
	        Rectangle border = createBorder(a,b);
	        Entity rectangleView = createRectangleWithBorder(border,c);
	        return rectangleView;
	}
	public static Entity createNotif(String a) {
		a=retourLigne(a, 50);
		String[] li=a.split("");
		int j=1;
		for (int i=0;i<a.length();i++) {
			if (li[i].equals("\n")){
				j++;
			}}
		double BG_WIDTH = FXGL.getAppWidth();
	    double BG_HEIGHT = 50*j;

	    
	        Entity notif =createRectangle(BG_WIDTH, BG_HEIGHT,new Point2D(PlayerComponent.position.getX()-FXGL.getAppWidth()/2,PlayerComponent.position.getY()-FXGL.getAppHeight()/2));
	        Text text = FXGL.getUIFactory().newText(a, Color.WHITE, 30.0);
	        
	        Entity postext = Entities.builder()
                    .at(16,32)
                    .build();
	        postext.setViewWithBBox(text);
			EntityView notifView = notif.getView();
			notifView.addNode(postext.getView());
			return notif;
			
			
	}
	protected static void removeEntity(Point2D a,EntityType b) {
		Entity i =trouveEntity(a,b);
		FXGL.getApp().getGameWorld().removeEntity(i);
	}
	public static Entity trouveEntity(Point2D c, EntityType a) {
		for (Entity i:FXGL.getApp().getGameWorld().getEntitiesAt(c)) {
			if (i.isType(a)) {
				return i;
			}
		}
		return null;
	}
	public static String retourLigne(String a,int b) {
		ArrayList<Integer> liste=new ArrayList<Integer>();
		int e=0;
		String[] li=a.split("");
		for (int i=0;i<a.length();i++) {
			if (li[i].equals("\n")){
				return a;
			}
			if(li[i].equals(" ")|| li[i].equals("!")|| li[i].equals("?")) {
				e=i+1;
			}
			if(i%b==0 && i!=0) {
				if(li[i].equals(" ")|| li[i].equals("!")|| li[i].equals("?")) {
					if (li[i+1].equals("!")|| li[i+1].equals("?")) {
						liste.add(i+2);
						
					}
					else {
					liste.add(i+1);
					}
				}
				else {
					liste.add(e);
				}
			}
		}
		for (int i=0;i<liste.size();i++) {
			String first = a.substring(0,liste.get(i)+i);
			String second = a.substring(liste.get(i)+i); 
			a=first+"\n"+second;
		}
			return a;
	}
	public static void addbarre(EntityView i, Character character, double a, double b,double pourcentage2,Color color) {
		BigDecimal pourcentage3 = new BigDecimal(Double.toString(pourcentage2));
		double pourcentage = pourcentage3.doubleValue();
		Rectangle border1 = createBorder(Math.round((192 * (100 - pourcentage)) / 100), 16);
		border1.setFill(color);
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
		RedBar.setProperty("position",new Point2D(a,b));
		RedBar.getView().setAccessibleText("border1" + character.toString());
		RedBar.getView().setUserData(RedBar);
		Entity GreenBar = createRectangleWithBorder(border1, new Point2D(a, b));
		GreenBar.setProperty("border", border1);
		GreenBar.setProperty("position",new Point2D(a,b));
		GreenBar.getView().setAccessibleText("border2" + character.toString());
		GreenBar.getView().setUserData(GreenBar);
		i.addNode(RedBar.getView());
		i.addNode(GreenBar.getView());
	}
	
	public static void updateBarre(Character character, Entity i, String color,double pourcentage2) {
		BigDecimal pourcentage3 = new BigDecimal(Double.toString(pourcentage2));
		double pourcentage = pourcentage3.doubleValue();

		if (color.equals("red")) {
			((Rectangle) i.getPropertyOptional("border").get()).setWidth(Math.round((192 * (pourcentage)) / 100));
				i.setX(((Point2D)i.getProperties().getObject("position")).getX()+ 192 * (100 - (pourcentage)) / 100);
		} else {
			((Rectangle) i.getPropertyOptional("border").get()).setWidth(Math.round((192 * (100 - pourcentage)) / 100));
		}
	}
}
