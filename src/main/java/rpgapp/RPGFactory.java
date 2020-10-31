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
		//Cr�er un objet de type bloc 
		return Entities.builder()
				.from(data)
				.type(EntityType.BLOC)
				.build();
	}
	
	@Spawns("monstre")
	public Entity newMonstre(SpawnData data ) {
		//Cr�er un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("RatFace.png")
				.from(data)
				.type(EntityType.Monstre)
				.build();
	}
	@Spawns("coffre")
	public Entity newCoffre(SpawnData data ) {
		//Cr�er un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("Image_coffre_closed.jpg")
				.from(data)
				.type(EntityType.Coffre)
				.build();
	}
	@Spawns("portal")
	public Entity newPortal(SpawnData data ) {
		//Cr�er un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("Water_Icon.png")
				.from(data)
				.type(EntityType.Portal)
				.build();
	}
	@Spawns("pnj")
	public Entity newPNJ(SpawnData data ) {
		//Cr�er un objet de type pnj 
		return Entities.builder()
				.viewFromTexture("PnjDroite.png") //TODO image � ajouter au projet
				.from(data)
				.type(EntityType.PNJ)
				.build();
	}


}
