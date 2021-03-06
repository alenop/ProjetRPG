package rpgapp.factory;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.FXGLDefaultMenu;
import com.almasb.fxgl.scene.menu.MenuType;

import rpgapp.Menu;

public class MenuSceneFactory extends SceneFactory {
	//private GameApplication app;
	public MenuSceneFactory() {
		super();
	}
	 @Override
     public FXGLMenu newMainMenu(GameApplication app) {
         return new Menu(app,MenuType.MAIN_MENU);
     }

     @Override
     public FXGLMenu newGameMenu(GameApplication app) {
         return new Menu(app,MenuType.GAME_MENU);
     }
}
