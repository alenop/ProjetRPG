package rpgapp.view;

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
        border.setFill(Color.rgb(25, 25, 25, 0.8));
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
	protected static Entity createNotif(String a) {
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
}
