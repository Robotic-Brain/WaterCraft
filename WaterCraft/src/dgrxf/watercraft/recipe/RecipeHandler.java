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
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/06/2013 MM/DD/YYYYY
 * 
 */

public class RecipeHandler {
    public static void init() {
        //blockRecipes();
        //itemRecipes();
        //oreDictRecipes();
    }
    
    public static void oreDictRecipes() {
        OreDictionary.registerOre("chest", ModBlocks.chest);
        OreDictionary.registerOre("chest", Block.chest);
        OreDictionary.registerOre("boat", Item.boat);
        OreDictionary.registerOre("boat", ModItems.boatSimple);
        OreDictionary.registerOre("boat", ModItems.boatVanilla);
        
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
        GameRegistry.addRecipe(new ItemStack(ModItems.boatSimple), "x x", "xxx", 'x', Block.planks);
    }
    
    public static void removeVanillaRecpies() {
        RecipeHelper.removeCraftingRecipe(new ItemStack(Item.boat));
    }
}
