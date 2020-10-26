package rpgapp;


import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.entity.components.CollidableComponent;


public class RPGFactory implements EntityFactory{
	
	@Spawns("bloc")
	public Entity newBloc(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.from(data)
				.type(EntityType.BLOC)
				.build();
	}
	
	@Spawns("monstre")
	public Entity newMonstre(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("mouse_fin.png")
				.from(data)
				.type(EntityType.Monstre)
				.build();
	}
	@Spawns("portal")
	public Entity newPortal(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("Water_Icon.png")
				.from(data)
				.type(EntityType.Portal)
				.build();
	}
	

}
