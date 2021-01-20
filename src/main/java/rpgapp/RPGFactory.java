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
	
	@Spawns("monstre")
	public Entity newMonstre(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("RatFace.png")
				.from(data)
				.type(EntityType.Monstre)
				.build();
	}
	
	@Spawns("coffre")
	public Entity newCoffre(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.viewFromTexture("Coffre_Ferme.png")
				.from(data)
				.type(EntityType.Coffre)
				.build();
	}
	
	@Spawns("portal")
	public Entity newPortal(SpawnData data ) {
		//Créer un objet de type bloc 
		return Entities.builder()
				.from(data)
				.type(EntityType.Portal)
				.build();
	}
	
	@Spawns("pnj")
	public Entity newPNJ(SpawnData data ) {
		//Créer un objet de type pnj 
		return Entities.builder()
				.viewFromTexture("Père_Dos.png") //TODO image à ajouter au projet
				.from(data)
				.type(EntityType.PNJ)
				.build();
	}
	
	@Spawns("torche")
	public Entity newTorche(SpawnData data) {
		return Entities.builder()
				.from(data)
				.viewFromTexture("torche.gif")
				.build();
	}
	
	@Spawns("feu")
	public Entity newFeu(SpawnData data) {
		return Entities.builder()
				.from(data)
				.viewFromTexture("feu.gif")
				.build();
	}
	
	@Spawns("indice")
	public Entity newIndice(SpawnData data) {
		return Entities.builder()
				.from(data)
				.viewFromTexture("Water_Icon.png")
				.build();
	}
	
	


}
