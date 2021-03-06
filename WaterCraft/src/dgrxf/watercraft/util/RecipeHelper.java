package dgrxf.watercraft.util;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

/**
 * 
 * RecipeHelper
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class RecipeHelper {
    public static void removeCraftingRecipe(ItemStack toRemove) {
        List recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++) {
            ItemStack stack;
            if ((stack = ((IRecipe) recipes.get(i)).getRecipeOutput()) == null) {
                continue;
            }
            
            if (stack.itemID == toRemove.itemID) {
                recipes.remove(i);
            }
        }
    }
}
