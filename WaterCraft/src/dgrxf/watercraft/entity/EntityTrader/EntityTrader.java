package dgrxf.watercraft.entity.EntityTrader;

import java.util.Collections;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class EntityTrader extends EntityVillager {
	
    private MerchantRecipeList buyingList;
    private float field_82191_bN;
	
	public EntityTrader(World par1World, int par2) {
		super(par1World, par2);
	}

	public EntityTrader(World par1World) {
		this(par1World, 0);
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
	}
	
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
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
        if (this.buyingList != null)
        {
            this.field_82191_bN = MathHelper.sqrt_float((float)this.buyingList.size()) * 0.2F;
        }
        else
        {
            this.field_82191_bN = 0.0F;
        }

        MerchantRecipeList merchantrecipelist;
        merchantrecipelist = new MerchantRecipeList();
        VillagerRegistry.manageVillagerTrades(merchantrecipelist, this, this.getProfession(), this.rand);
        int j;
        label50:

        switch (this.getProfession())
        {
            case 0:
                addMerchantItem(merchantrecipelist, Item.wheat.itemID, this.rand, this.adjustProbability(0.9F));
                addMerchantItem(merchantrecipelist, Block.cloth.blockID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.chickenRaw.itemID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.fishCooked.itemID, this.rand, this.adjustProbability(0.4F));
                addBlacksmithItem(merchantrecipelist, Item.bread.itemID, this.rand, this.adjustProbability(0.9F));
                addBlacksmithItem(merchantrecipelist, Item.melon.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.appleRed.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.cookie.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.shears.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.flintAndSteel.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.chickenCooked.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.arrow.itemID, this.rand, this.adjustProbability(0.5F));

                if (this.rand.nextFloat() < this.adjustProbability(0.5F))
                {
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Block.gravel, 10), new ItemStack(Item.emerald), new ItemStack(Item.flint.itemID, 4 + this.rand.nextInt(2), 0)));
                }

                break;
            case 1:
                addMerchantItem(merchantrecipelist, Item.paper.itemID, this.rand, this.adjustProbability(0.8F));
                addMerchantItem(merchantrecipelist, Item.book.itemID, this.rand, this.adjustProbability(0.8F));
                addMerchantItem(merchantrecipelist, Item.writtenBook.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Block.bookShelf.blockID, this.rand, this.adjustProbability(0.8F));
                addBlacksmithItem(merchantrecipelist, Block.glass.blockID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.compass.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.pocketSundial.itemID, this.rand, this.adjustProbability(0.2F));

                if (this.rand.nextFloat() < this.adjustProbability(0.07F))
                {
                    Enchantment enchantment = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
                    int k = MathHelper.getRandomIntegerInRange(this.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
                    ItemStack itemstack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, k));
                    j = 2 + this.rand.nextInt(5 + k * 10) + 3 * k;
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Item.book), new ItemStack(Item.emerald, j), itemstack));
                }

                break;
            case 2:
                addBlacksmithItem(merchantrecipelist, Item.eyeOfEnder.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.expBottle.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.redstone.itemID, this.rand, this.adjustProbability(0.4F));
                addBlacksmithItem(merchantrecipelist, Block.glowStone.blockID, this.rand, this.adjustProbability(0.3F));
                int[] aint = new int[] {Item.swordIron.itemID, Item.swordDiamond.itemID, Item.plateIron.itemID, Item.plateDiamond.itemID, Item.axeIron.itemID, Item.axeDiamond.itemID, Item.pickaxeIron.itemID, Item.pickaxeDiamond.itemID};
                int[] aint1 = aint;
                int l = aint.length;
                j = 0;

                while (true)
                {
                    if (j >= l)
                    {
                        break label50;
                    }

                    int i1 = aint1[j];

                    if (this.rand.nextFloat() < this.adjustProbability(0.05F))
                    {
                        merchantrecipelist.add(new MerchantRecipe(new ItemStack(i1, 1, 0), new ItemStack(Item.emerald, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(i1, 1, 0), 5 + this.rand.nextInt(15))));
                    }

                    ++j;
                }
            case 3:
                addMerchantItem(merchantrecipelist, Item.coal.itemID, this.rand, this.adjustProbability(0.7F));
                addMerchantItem(merchantrecipelist, Item.ingotIron.itemID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.ingotGold.itemID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.diamond.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.swordIron.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.swordDiamond.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.axeIron.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.axeDiamond.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.pickaxeIron.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.pickaxeDiamond.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.shovelIron.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.shovelDiamond.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.hoeIron.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.hoeDiamond.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.bootsIron.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.bootsDiamond.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.helmetIron.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.helmetDiamond.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.plateIron.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.plateDiamond.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.legsIron.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.legsDiamond.itemID, this.rand, this.adjustProbability(0.2F));
                addBlacksmithItem(merchantrecipelist, Item.bootsChain.itemID, this.rand, this.adjustProbability(0.1F));
                addBlacksmithItem(merchantrecipelist, Item.helmetChain.itemID, this.rand, this.adjustProbability(0.1F));
                addBlacksmithItem(merchantrecipelist, Item.plateChain.itemID, this.rand, this.adjustProbability(0.1F));
                addBlacksmithItem(merchantrecipelist, Item.legsChain.itemID, this.rand, this.adjustProbability(0.1F));
                break;
            case 4:
                addMerchantItem(merchantrecipelist, Item.coal.itemID, this.rand, this.adjustProbability(0.7F));
                addMerchantItem(merchantrecipelist, Item.porkRaw.itemID, this.rand, this.adjustProbability(0.5F));
                addMerchantItem(merchantrecipelist, Item.beefRaw.itemID, this.rand, this.adjustProbability(0.5F));
                addBlacksmithItem(merchantrecipelist, Item.saddle.itemID, this.rand, this.adjustProbability(0.1F));
                addBlacksmithItem(merchantrecipelist, Item.plateLeather.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.bootsLeather.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.helmetLeather.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.legsLeather.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.porkCooked.itemID, this.rand, this.adjustProbability(0.3F));
                addBlacksmithItem(merchantrecipelist, Item.beefCooked.itemID, this.rand, this.adjustProbability(0.3F));
        }

        if (merchantrecipelist.isEmpty())
        {
            addMerchantItem(merchantrecipelist, Item.ingotGold.itemID, this.rand, 1.0F);
        }

        Collections.shuffle(merchantrecipelist);

        if (this.buyingList == null)
        {
            this.buyingList = new MerchantRecipeList();
        }

        for (int j1 = 0; j1 < par1 && j1 < merchantrecipelist.size(); ++j1)
        {
            this.buyingList.addToListWithCheck((MerchantRecipe)merchantrecipelist.get(j1));
        }
    }
	
	@Override
	public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        applyRandomTrade(this, worldObj.rand);
        return par1EntityLivingData;
    }

	
}
