package item;
import com.almasb.fxgl.entity.Entity;
 
public class Item_pnj<EnumArmorMaterial> extends itemArmure
{
    public Item_pnj(int id, EnumArmorMaterial armorMaterial, int type, int layer)
    {
        super();
    }
    public String getArmorTexture(itemStack stack, Entity entity, int slot, int layer)
    {
        if(stack.itemId == ModTutoriel.TutorialLeggings)
        {
            return "modtutoriel:textures/models/armor/tutorial_layer_2.png";
        }
        else
        {
            return "modtutoriel:textures/models/armor/tutorial_layer_1.png";
        }
    }
}
