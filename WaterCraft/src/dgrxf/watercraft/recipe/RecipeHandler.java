package dgrxf.watercraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.util.RecipeHelper;

/**
 * 
 * RecipeHandler
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class RecipeHandler {
    public static void init() {
        //blockRecipes();
        //itemRecipes();
        //oreDictRecipes();
    }
    
    public static void oreDictRecipes() {
        OreDictionary.registerOre("chest", ModBlocks.WC_CHEST.getBlock());
        OreDictionary.registerOre("chest", Block.chest);
        OreDictionary.registerOre("boat", Item.boat);
        OreDictionary.registerOre("boat", ModItems.VANILLA_BOAT.getItem());
        
        GameRegistry.addRecipe(new ShapelessOreRecipe(Item.minecartCrate, new Object[] {
                Item.minecartEmpty, "chest" }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(Block.chestTrapped, new Object[] {
                "chest", Block.tripWireSource }));
        GameRegistry.addRecipe(new ShapedOreRecipe(Block.hopperBlock, "A A", "ABA", " A ", Character.valueOf('A'), Item.ingotIron, Character.valueOf('B'), "chest"));
        //GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.boatChest, new Object[]{"chest", "boat"}));
    }
    
    public static void blockRecipes() {
        
    }
    
    public static void itemRecipes() {
    	//GameRegistry.addRecipe(new ItemStack(ModItems.boatSimple), "x x", "xxx", 'x', Block.planks);
    }
    
    public static void removeVanillaRecpies() {
        RecipeHelper.removeCraftingRecipe(new ItemStack(Item.boat));
    }
}
