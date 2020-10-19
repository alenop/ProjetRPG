package item;

	import javax.swing.text.html.parser.Entity;

	public class Arme <EnumArmorMaterial> extends Item {
		
		public Arme(int id, Object armorMaterial, int type, int layer) {
			super();
		}
		public String getArmorTexture(itemStack stack, Entity entity, int slot, int layer, Object ModTutoriel)
		{
		    if(itemStack.itemId == ModTutoriel)
		    {
		        return "modtutoriel:textures/models/armor/tutorial_layer_2.png"; //Armer 1, jumpers
		    }
		    else if(itemStack.itemId == ModTutoriel || itemStack.itemId == ModTutoriel)
		    {
		        return "modtutoriel:textures/models/armor/tutorial_layer_1.png"; //Armer 1, cask + plastron + bottles
		    }
		    else if(itemStack.itemId == ModTutoriel)
		    {
		        return "modtutoriel:textures/models/armor/tutorial2_layer_2.png"; //Armer 2, jumpers
		    }
		    else
		    {
		        return "modtutoriel:textures/models/armor/tutorial2_layer_2.png"; //Armer 2, cask + plastron + bottles
		    }
		}
	}


