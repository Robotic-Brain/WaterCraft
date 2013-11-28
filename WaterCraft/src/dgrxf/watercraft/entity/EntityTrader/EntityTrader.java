package dgrxf.watercraft.entity.EntityTrader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class EntityTrader extends EntityVillager {
	
    private MerchantRecipeList traderBuyingList;
    private float field_82191_bN;
	
    public static final Map traderStockList = new HashMap();
    public static final Map tradersmithSellingList = new HashMap();
    
	public EntityTrader(World par1World, int par2) {
		super(par1World, par2);
	}

	public EntityTrader(World par1World) {
		this(par1World, 0);
	}	
	
	private float adjustProbability(float par1)
    {
        float f1 = par1 + this.field_82191_bN;
        return f1 > 0.9F ? 0.9F - (f1 - 0.9F) : f1;
    }
	
	public void applyRandomTrade(EntityVillager villager, Random rand)
    {
        int trade = 1;
        villager.setProfession(trade);
    }
	
	private void addDefaultEquipmentAndRecipies(int par1)
    {
        if (this.traderBuyingList != null)
        {
            this.field_82191_bN = MathHelper.sqrt_float((float)this.traderBuyingList.size()) * 0.2F;
        }
        else
        {
            this.field_82191_bN = 0.0F;
        }

        MerchantRecipeList merchantrecipelist;
        merchantrecipelist = new MerchantRecipeList();
        //VillagerRegistry.manageVillagerTrades(merchantrecipelist, this, this.getProfession(), this.rand);
        int j;
        switch (this.getProfession())
        {
            case 0:
                addMerchantItem(merchantrecipelist, Item.fishRaw.itemID, this.rand, this.adjustProbability(0.9F));
                addMerchantItem(merchantrecipelist, Block.cloth.blockID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.dyePowder.itemID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.fishCooked.itemID, this.rand, this.adjustProbability(0.4F));
                addBlacksmithItem(merchantrecipelist, Item.fishRaw.itemID, this.rand, this.adjustProbability(0.9F));
                addBlacksmithItem(merchantrecipelist, Block.cloth.blockID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.dyePowder.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.fishCooked.itemID, this.rand, this.adjustProbability(0.4F));

                if (this.rand.nextFloat() < this.adjustProbability(0.5F))
                {
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Block.gravel, 10), new ItemStack(Item.emerald), new ItemStack(Item.flint.itemID, 4 + this.rand.nextInt(2), 0)));
                }

                break;
        }

        if (merchantrecipelist.isEmpty())
        {
            addMerchantItem(merchantrecipelist, Item.ingotGold.itemID, this.rand, 1.0F);
        }

        Collections.shuffle(merchantrecipelist);

        if (this.traderBuyingList == null)
        {
            this.traderBuyingList = new MerchantRecipeList();
        }

        for (int j1 = 0; j1 < par1 && j1 < merchantrecipelist.size(); ++j1)
        {
            this.traderBuyingList.addToListWithCheck((MerchantRecipe)merchantrecipelist.get(j1));
        }
    }
	
	private static int getRandomCountForBlacksmithItem(int par0, Random par1Random)
    {
        Tuple tuple = (Tuple)tradersmithSellingList.get(Integer.valueOf(par0));
        return tuple == null ? 1 : (((Integer)tuple.getFirst()).intValue() >= ((Integer)tuple.getSecond()).intValue() ? ((Integer)tuple.getFirst()).intValue() : ((Integer)tuple.getFirst()).intValue() + par1Random.nextInt(((Integer)tuple.getSecond()).intValue() - ((Integer)tuple.getFirst()).intValue()));
    }
	
	private static int getRandomCountForItem(int par0, Random par1Random)
    {
        Tuple tuple = (Tuple)traderStockList.get(Integer.valueOf(par0));
        return tuple == null ? 1 : (((Integer)tuple.getFirst()).intValue() >= ((Integer)tuple.getSecond()).intValue() ? ((Integer)tuple.getFirst()).intValue() : ((Integer)tuple.getFirst()).intValue() + par1Random.nextInt(((Integer)tuple.getSecond()).intValue() - ((Integer)tuple.getFirst()).intValue()));
    }
	
	@Override
	public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        applyRandomTrade(this, worldObj.rand);
        return par1EntityLivingData;
    }
	
	static
    {
        traderStockList.put(Integer.valueOf(Item.fishRaw.itemID), new Tuple(Integer.valueOf(1), Integer.valueOf(12)));
        traderStockList.put(Integer.valueOf(Item.dyePowder.itemID), new Tuple(Integer.valueOf(3), Integer.valueOf(10)));
        traderStockList.put(Integer.valueOf(dgrxf.watercraft.block.ModBlocks.TANK.getId()), new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
        traderStockList.put(Integer.valueOf(Item.diamond.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        tradersmithSellingList.put(Integer.valueOf(Item.shears.itemID), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        tradersmithSellingList.put(Integer.valueOf(Item.shears.itemID), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        tradersmithSellingList.put(Integer.valueOf(Item.shears.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        tradersmithSellingList.put(Integer.valueOf(Item.shears.itemID), new Tuple(Integer.valueOf(12), Integer.valueOf(14)));

    }
	
}
