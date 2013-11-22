package dgrxf.watercraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.TEMultiblockPhantom;

public class MultiblockPhantom extends Block {

    public MultiblockPhantom(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.miscTab);
        setUnlocalizedName(BlockInfo.PHANTOM_MULTIBLOCK_UNLOCALIZED_NAME);
    }
    
    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEMultiblockPhantom();
    }
    
    /**
     * Returns The fake block id
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @return fake block id
     */
    private int getImpostorBlockId(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te instanceof TEMultiblockPhantom) {
            return ((TEMultiblockPhantom)te).getImpostorBlockId();
        } else {
            return TEMultiblockPhantom.INVALID_ID;
        }
    }
    
    /**
     * Returns the fake metadata
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @return fake metadata
     */
    private int getImpostorBlockMeta(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te instanceof TEMultiblockPhantom) {
            return ((TEMultiblockPhantom)te).getImpostorMetadata();
        } else {
            return TEMultiblockPhantom.INVALID_META;
        }
    }
    
    
    /*************************** Vanilla overwrites *************************************/
    
    /*@Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }*/
    
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getBlockTexture(world, x, y, z, side);
        } else {
            return Block.blocksList[fakeID].getBlockTextureFromSide(side);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        // TODO Auto-generated method stub
        return super.renderAsNormalBlock();
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
        return false;
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getBlockHardness(world, x, y, z);
        } else {
            return Block.blocksList[fakeID].blockHardness;
        }
    }

    @Override
    public boolean getTickRandomly() {
        return false;
    }

    @Override
    public float getBlockBrightness(IBlockAccess world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getBlockBrightness(world, x, y, z);
        } else {
            return Block.blocksList[fakeID].getBlockBrightness(world, x, y, z);
        }
    }

    @Override
    public int getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getMixedBrightnessForBlock(world, x, y, z);
        } else {
            return Block.blocksList[fakeID].getMixedBrightnessForBlock(world, x, y, z);
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.shouldSideBeRendered(world, x, y, z, side);
        } else {
            return Block.blocksList[fakeID].shouldSideBeRendered(world, x, y, z, side);
        }
    }

    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.isBlockSolid(world, x, y, z, side);
        } else {
            return Block.blocksList[fakeID].isBlockSolid(world, x, y, z, side);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
        } else {
            Block.blocksList[fakeID].addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getSelectedBoundingBoxFromPool(world, x, y, z);
        } else {
            return Block.blocksList[fakeID].getSelectedBoundingBoxFromPool(world, x, y, z);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        } else {
            return Block.blocksList[fakeID].getCollisionBoundingBoxFromPool(world, x, y, z);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        // TODO Auto-generated method stub
        return super.isOpaqueCube();
    }

    @Override
    public boolean canCollideCheck(int par1, boolean par2) {
        // TODO Auto-generated method stub
        return super.canCollideCheck(par1, par2);
    }

    @Override
    public boolean isCollidable() {
        // TODO Auto-generated method stub
        return super.isCollidable();
    }

    /*@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        // TODO Auto-generated method stub
        super.updateTick(par1World, par2, par3, par4, par5Random);
    }*/

    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        // TODO Auto-generated method stub
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }

    /*@Override
    public int tickRate(World par1World) {
        // TODO Auto-generated method stub
        return super.tickRate(par1World);
    }*/

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            super.onBlockAdded(world, x, y, z);
        } else {
            Block.blocksList[fakeID].onBlockAdded(world, x, y, z);
        }
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        // TODO Auto-generated method stub
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public int quantityDropped(Random par1Random) {
        // TODO Auto-generated method stub
        return super.quantityDropped(par1Random);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        // TODO Auto-generated method stub
        return super.idDropped(par1, par2Random, par3);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.getPlayerRelativeBlockHardness(par1EntityPlayer, world, x, y, z);
        } else {
            return Block.blocksList[fakeID].getPlayerRelativeBlockHardness(par1EntityPlayer, world, x, y, z);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        int fakeMeta = getImpostorBlockMeta(world, x, y, z);
        
        if (fakeID < 0) {
            super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
        } else {
            Block.blocksList[fakeID].dropBlockAsItemWithChance(world, x, y, z, fakeMeta, chance, fortune);
        }
    }

    /*@Override
    protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack) {
        // TODO Auto-generated method stub
        super.dropBlockAsItem_do(par1World, par2, par3, par4, par5ItemStack);
    }*/

    /*@Override
    protected void dropXpOnBlockBreak(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        super.dropXpOnBlockBreak(par1World, par2, par3, par4, par5);
    }*/

    @Override
    public int damageDropped(int par1) {
        // TODO Auto-generated method stub
        return super.damageDropped(par1);
    }

    @Override
    public float getExplosionResistance(Entity par1Entity) {
        // TODO Auto-generated method stub
        return super.getExplosionResistance(par1Entity);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3) {
        // TODO Auto-generated method stub
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }

    @Override
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
        // TODO Auto-generated method stub
        super.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
    }

    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5, ItemStack par6ItemStack) {
        // TODO Auto-generated method stub
        return super.canPlaceBlockOnSide(par1World, par2, par3, par4, par5, par6ItemStack);
    }

    @Override
    public int getRenderBlockPass() {
        // TODO Auto-generated method stub
        return super.getRenderBlockPass();
    }

    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        return super.canPlaceBlockOnSide(par1World, par2, par3, par4, par5);
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return super.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        // TODO Auto-generated method stub
        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }

    @Override
    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        // TODO Auto-generated method stub
        super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }

    @Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
        // TODO Auto-generated method stub
        return super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
    }

    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        // TODO Auto-generated method stub
        super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }

    @Override
    public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {
        // TODO Auto-generated method stub
        super.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        super.setBlockBoundsBasedOnState(par1iBlockAccess, par2, par3, par4);
    }

    @Override
    public int getBlockColor() {
        // TODO Auto-generated method stub
        return super.getBlockColor();
    }

    @Override
    public int getRenderColor(int par1) {
        // TODO Auto-generated method stub
        return super.getRenderColor(par1);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        return super.isProvidingWeakPower(par1iBlockAccess, par2, par3, par4, par5);
    }

    @Override
    public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return super.colorMultiplier(par1iBlockAccess, par2, par3, par4);
    }

    @Override
    public boolean canProvidePower() {
        // TODO Auto-generated method stub
        return super.canProvidePower();
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        // TODO Auto-generated method stub
        super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        return super.isProvidingStrongPower(par1iBlockAccess, par2, par3, par4, par5);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        // TODO Auto-generated method stub
        super.setBlockBoundsForItemRender();
    }

    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
        // TODO Auto-generated method stub
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }

    @Override
    protected boolean canSilkHarvest() {
        // TODO Auto-generated method stub
        return super.canSilkHarvest();
    }

    @Override
    protected ItemStack createStackedBlock(int par1) {
        // TODO Auto-generated method stub
        return super.createStackedBlock(par1);
    }

    @Override
    public int quantityDroppedWithBonus(int par1, Random par2Random) {
        // TODO Auto-generated method stub
        return super.quantityDroppedWithBonus(par1, par2Random);
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return super.canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        // TODO Auto-generated method stub
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
    }

    @Override
    public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    public String getLocalizedName() {
        // TODO Auto-generated method stub
        return super.getLocalizedName();
    }

    @Override
    public String getUnlocalizedName() {
        // TODO Auto-generated method stub
        return super.getUnlocalizedName();
    }

    @Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        // TODO Auto-generated method stub
        return super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
    }

    @Override
    public boolean getEnableStats() {
        // TODO Auto-generated method stub
        return super.getEnableStats();
    }

    @Override
    protected Block disableStats() {
        // TODO Auto-generated method stub
        return super.disableStats();
    }

    @Override
    public int getMobilityFlag() {
        // TODO Auto-generated method stub
        return super.getMobilityFlag();
    }

    @Override
    public float getAmbientOcclusionLightValue(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return super.getAmbientOcclusionLightValue(par1iBlockAccess, par2, par3, par4);
    }

    @Override
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6) {
        // TODO Auto-generated method stub
        super.onFallenUpon(par1World, par2, par3, par4, par5Entity, par6);
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {
        int fakeID = getImpostorBlockId(world, x, y, z);
        
        if (fakeID < 0) {
            return super.idPicked(world, x, y, z);
        } else {
            return Block.blocksList[fakeID].idPicked(world, x, y, z);
        }
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        return super.getDamageValue(par1World, par2, par3, par4);
    }

    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        // TODO Auto-generated method stub
        super.getSubBlocks(par1, par2CreativeTabs, par3List);
    }

    @Override
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
        // TODO Auto-generated method stub
        super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
    }

    @Override
    public void onBlockPreDestroy(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        super.onBlockPreDestroy(par1World, par2, par3, par4, par5);
    }

    @Override
    public void fillWithRain(World par1World, int par2, int par3, int par4) {
        // TODO Auto-generated method stub
        super.fillWithRain(par1World, par2, par3, par4);
    }

    @Override
    public boolean isFlowerPot() {
        // TODO Auto-generated method stub
        return super.isFlowerPot();
    }

    @Override
    public boolean func_82506_l() {
        // TODO Auto-generated method stub
        return super.func_82506_l();
    }

    @Override
    public boolean canDropFromExplosion(Explosion par1Explosion) {
        // TODO Auto-generated method stub
        return super.canDropFromExplosion(par1Explosion);
    }

    @Override
    public boolean isAssociatedBlockID(int par1) {
        // TODO Auto-generated method stub
        return super.isAssociatedBlockID(par1);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        // TODO Auto-generated method stub
        return super.hasComparatorInputOverride();
    }

    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
        // TODO Auto-generated method stub
        return super.getComparatorInputOverride(par1World, par2, par3, par4, par5);
    }

    @Override
    public Block setTextureName(String par1Str) {
        // TODO Auto-generated method stub
        return super.setTextureName(par1Str);
    }

    @Override
    protected String getTextureName() {
        // TODO Auto-generated method stub
        return super.getTextureName();
    }

    @Override
    public String getItemIconName() {
        // TODO Auto-generated method stub
        return super.getItemIconName();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.getLightValue(world, x, y, z);
    }

    @Override
    public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity) {
        // TODO Auto-generated method stub
        return super.isLadder(world, x, y, z, entity);
    }

    @Override
    public boolean isBlockNormalCube(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isBlockNormalCube(world, x, y, z);
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        // TODO Auto-generated method stub
        return super.isBlockSolidOnSide(world, x, y, z, side);
    }

    @Override
    public boolean isBlockReplaceable(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isBlockReplaceable(world, x, y, z);
    }

    @Override
    public boolean isBlockBurning(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isBlockBurning(world, x, y, z);
    }

    @Override
    public boolean isAirBlock(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isAirBlock(world, x, y, z);
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        // TODO Auto-generated method stub
        return super.canHarvestBlock(player, meta);
    }

    @Override
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.removeBlockByPlayer(world, player, x, y, z);
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
        // TODO Auto-generated method stub
        return super.getFlammability(world, x, y, z, metadata, face);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
        // TODO Auto-generated method stub
        return super.isFlammable(world, x, y, z, metadata, face);
    }

    @Override
    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face) {
        // TODO Auto-generated method stub
        return super.getFireSpreadSpeed(world, x, y, z, metadata, face);
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, int metadata, ForgeDirection side) {
        // TODO Auto-generated method stub
        return super.isFireSource(world, x, y, z, metadata, side);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        // TODO Auto-generated method stub
        return super.quantityDropped(meta, fortune, random);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        // TODO Auto-generated method stub
        return super.getBlockDropped(world, x, y, z, metadata, fortune);
    }

    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
        // TODO Auto-generated method stub
        return super.canSilkHarvest(world, player, x, y, z, metadata);
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.canCreatureSpawn(type, world, x, y, z);
    }

    @Override
    public boolean isBed(World world, int x, int y, int z, EntityLivingBase player) {
        // TODO Auto-generated method stub
        return super.isBed(world, x, y, z, player);
    }

    @Override
    public ChunkCoordinates getBedSpawnPosition(World world, int x, int y, int z, EntityPlayer player) {
        // TODO Auto-generated method stub
        return super.getBedSpawnPosition(world, x, y, z, player);
    }

    @Override
    public void setBedOccupied(World world, int x, int y, int z, EntityPlayer player, boolean occupied) {
        // TODO Auto-generated method stub
        super.setBedOccupied(world, x, y, z, player, occupied);
    }

    @Override
    public int getBedDirection(IBlockAccess world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.getBedDirection(world, x, y, z);
    }

    @Override
    public boolean isBedFoot(IBlockAccess world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isBedFoot(world, x, y, z);
    }

    @Override
    public void beginLeavesDecay(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        super.beginLeavesDecay(world, x, y, z);
    }

    @Override
    public boolean canSustainLeaves(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.canSustainLeaves(world, x, y, z);
    }

    @Override
    public boolean isLeaves(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isLeaves(world, x, y, z);
    }

    @Override
    public boolean canBeReplacedByLeaves(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.canBeReplacedByLeaves(world, x, y, z);
    }

    @Override
    public boolean isWood(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isWood(world, x, y, z);
    }

    @Override
    public boolean isGenMineableReplaceable(World world, int x, int y, int z, int target) {
        // TODO Auto-generated method stub
        return super.isGenMineableReplaceable(world, x, y, z, target);
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        // TODO Auto-generated method stub
        return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
        // TODO Auto-generated method stub
        super.onBlockExploded(world, x, y, z, explosion);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
        // TODO Auto-generated method stub
        return super.canConnectRedstone(world, x, y, z, side);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.canPlaceTorchOnTop(world, x, y, z);
    }

    @Override
    public boolean canRenderInPass(int pass) {
        // TODO Auto-generated method stub
        return super.canRenderInPass(pass);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.getPickBlock(target, world, x, y, z);
    }

    @Override
    public boolean isBlockFoliage(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isBlockFoliage(world, x, y, z);
    }

    @Override
    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        // TODO Auto-generated method stub
        return super.addBlockHitEffects(worldObj, target, effectRenderer);
    }

    @Override
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        // TODO Auto-generated method stub
        return super.addBlockDestroyEffects(world, x, y, z, meta, effectRenderer);
    }

    @Override
    public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
        // TODO Auto-generated method stub
        return super.canSustainPlant(world, x, y, z, direction, plant);
    }

    @Override
    public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
        // TODO Auto-generated method stub
        super.onPlantGrow(world, x, y, z, sourceX, sourceY, sourceZ);
    }

    @Override
    public boolean isFertile(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.isFertile(world, x, y, z);
    }

    @Override
    public int getLightOpacity(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.getLightOpacity(world, x, y, z);
    }

    @Override
    public boolean canEntityDestroy(World world, int x, int y, int z, Entity entity) {
        // TODO Auto-generated method stub
        return super.canEntityDestroy(world, x, y, z, entity);
    }

    @Override
    public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        // TODO Auto-generated method stub
        return super.isBeaconBase(worldObj, x, y, z, beaconX, beaconY, beaconZ);
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
        // TODO Auto-generated method stub
        return super.rotateBlock(worldObj, x, y, z, axis);
    }

    @Override
    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.getValidRotations(worldObj, x, y, z);
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return super.getEnchantPowerBonus(world, x, y, z);
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        // TODO Auto-generated method stub
        return super.recolourBlock(world, x, y, z, side, colour);
    }

    @Override
    public void onNeighborTileChange(World world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        // TODO Auto-generated method stub
        super.onNeighborTileChange(world, x, y, z, tileX, tileY, tileZ);
    }

    @Override
    public boolean weakTileChanges() {
        // TODO Auto-generated method stub
        return super.weakTileChanges();
    }

    @Override
    public boolean shouldCheckWeakPower(World world, int x, int y, int z, int side) {
        // TODO Auto-generated method stub
        return super.shouldCheckWeakPower(world, x, y, z, side);
    }
    
}
