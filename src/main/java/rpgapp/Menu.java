package rpgapp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class Menu extends FXGLMenu {

	public Menu(GameApplication app, MenuType type) {
		super(app, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Button createActionButton(String name, Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Button createActionButton(StringBinding name, Runnable action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Node createBackground(double width, double height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Node createTitleView(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Node createVersionView(String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Node createProfileView(String profileName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}