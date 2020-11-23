package rpgapp.control;

import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.text.View;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.TypeComponent;
import com.almasb.fxgl.util.Consumer;

import elementsInteractifs.PNJ;
import elementsInteractifs.PNJList;
import javafx.geometry.Point2D;
import rpgapp.EntityType;
import rpgapp.RPGApp;

public class PlayerComponent extends Component {

	private PositionComponent position;
	private Runnable callback;

	@Override
	public void onUpdate(double tpf) {
	}

	// Les methodes move ne fonctionnent que si "CanMove" est vérifié

	public void moveRight() {
		if (canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.BLOC)
				&& canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.Monstre)
				&& canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.PNJ)) {
			try {
				position.translateX(RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else if (canMove(new Point2D(RPGApp.TILE_SIZE, 0), EntityType.PNJ) == false) {

			double xPNJ = position.getX() + RPGApp.TILE_SIZE;
			double yPNJ = position.getY();
			for (Entry<String, PNJ> pnj : PNJList.pnjList.entrySet()) {
				PNJ p = pnj.getValue();
				if (p.getPosX() == xPNJ && p.getPosY() == yPNJ) {
					
					
					FXGL.getApp().getDisplay().showMessageBox(p.getTextitems());
				
				}
			}
		}
	}
				
	
	protected Object getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveLeft() {
		if (canMove(new Point2D(-RPGApp.TILE_SIZE, 0), EntityType.BLOC)) {
			try {
				position.translateX(-RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void moveDown() {
		if (canMove(new Point2D(position.getX(), position.getY() + RPGApp.TILE_SIZE), EntityType.BLOC)) {
			try {
				position.translateY(RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void moveUp() {
		if (canMove(new Point2D(0, -RPGApp.TILE_SIZE), EntityType.BLOC)) {
			try {
				position.translateY(-RPGApp.TILE_SIZE);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		canMove(new Point2D(0, -RPGApp.TILE_SIZE), EntityType.BLOC);
	}

	private boolean canMove(Point2D direction, EntityType a) {
		// Vérifie que la case n'est pas un Bloc
		// System.out.println(position.getX()+"/"+position.getY());
		// System.out.println(position.getGridX(RPGApp.TILE_SIZE));
		Point2D newPosition = position.getValue().add(direction);

		return FXGL.getApp().getGameWorld().getEntitiesAt(newPosition).stream()
				.filter(e -> e.hasComponent(TypeComponent.class)).map(e -> e.getComponent(TypeComponent.class))
				.noneMatch(type -> type.isType(a));

	}

}
