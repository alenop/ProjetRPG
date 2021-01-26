package rpgapp.eventhandler;



import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rpgapp.RPGApp;
import rpgapp.data.elementInteractifs.Item;
import rpgapp.view.DisplayBasic;

public class ItemEventHandler extends DisplayBasic implements EventHandler<MouseEvent> {
	private Item item;
	private boolean invent;
	private int positem;
	private String type;
	public ItemEventHandler(int positem) {
		this.positem=positem;
		invent=true;
	}
	public ItemEventHandler(String type) {
		this.type=type;
		invent=false;
	}
	@Override
	public void handle(MouseEvent arg0) {
		if (invent) {
			this.item=RPGApp.hero.getInventory()[positem];
		}
		else {
			this.item=RPGApp.hero.getEquipement().get(type);
		}
		int x=135;
		int y=64;
		
		Point2D center=((Entity)((EntityView)arg0.getSource()).getUserData()).getPosition();
		
		Rectangle border = new Rectangle(0,0,x,y);
		border.setFill(Color.rgb(0, 0, 0,0.8));
		Entity window = DisplayBasic.createRectangleWithBorder(border, new Point2D(center.getX()-x/2+32,center.getY()-y+32));
		Text name= FXGL.getUIFactory().newText(item.getName()+" : \n"+DisplayBasic.retourLigne(item.getEffect(),20), Color.WHITE, 15.0);
		window.getView().addNode(CreateEntityWithNode(name,0, 16).getView());
		window.getView().setMouseTransparent(true);
		((Entity)((EntityView)arg0.getSource()).getParent().getUserData()).getView().addNode(window.getView());
		
		((EntityView)arg0.getSource()).setOnMouseExited((e) -> ((Entity)((EntityView)arg0.getSource()).getParent().getUserData()).getView().removeNode(window.getView()));
	}
	
}
