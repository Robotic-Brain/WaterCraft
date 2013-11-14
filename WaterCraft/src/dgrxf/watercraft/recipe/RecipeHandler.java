package dgrxf.watercraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.util.RecipeHelper;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/06/2013 MM/DD/YYYYY
 * 
 */

public class RecipeHandler {
	public static void init(){
		blockRecipes();
		itemRecipes();
	}
	
	public static void blockRecipes(){
		
	}
	
	public static void itemRecipes(){
		 GameRegistry.addRecipe(new ItemStack(ModItems.boat), "x x", "xxx", 'x', Block.planks);
	}
	
	public static void removeVanillaRecpies(){
		RecipeHelper.removeCraftingRecipe(new ItemStack(Item.boat));
	}
}
