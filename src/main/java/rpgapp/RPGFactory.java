package rpgapp;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class RPGFactory implements EntityFactory{
	
	@Spawns("bloc")
	public Entity newBloc(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.from(data)
				.type(EntityType.BLOC)
				.build();
	}
	
	@Spawns("souris")
	public Entity newSouris(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("mouse_fin.png")
				.from(data)
				.type(EntityType.Monstre)
				.build();
	}

	@Spawns("pnj")
	public Entity newPNJ(SpawnData data ) {
		//Créer un objet de type pnj 
		return Entities.builder()
				.viewFromTexture("pnj.png") //TODO image à ajouter au projet
				.from(data)
				.type(EntityType.PNJ)
				.build();
	}
	
}
