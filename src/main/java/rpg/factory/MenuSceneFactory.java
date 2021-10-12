package rpg.factory;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;

import rpg.Menu;

public class MenuSceneFactory extends SceneFactory {
	
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
