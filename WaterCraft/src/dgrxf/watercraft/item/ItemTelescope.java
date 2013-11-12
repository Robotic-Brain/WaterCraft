package dgrxf.watercraft.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.particles.BuoyParticle;
import dgrxf.watercraft.client.particles.CustomParticles;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector3;

public class ItemTelescope extends Item{
		
    private int tick;
    private boolean showParticles;
    //private float particleSpeed = 1.0F;
    
    public ItemTelescope() {
        super(ItemInfo.TELESCOPE_ID);
        setUnlocalizedName(ItemInfo.TELESCOPE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.creativeTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {            
            if (world.getBlockId(x, y, z) == ModBlocks.buoy.blockID) {;
                TileEntity te = world.getBlockTileEntity(x, y, z);
                if (te instanceof WCTileEntityBuoy) {
                    WCTileEntityBuoy buoy = (WCTileEntityBuoy) te;
                    
                    if (buoy.hasNextBuoy()) {
                    	Watercraft.printToPlayer("Found next buoy at " + buoy.getNextBuoyPos().toString());
                       	float distance = (new Vector3(x, y, z)).sub(buoy.getNextBuoyPos()).length();
                       	Watercraft.printToPlayer("Distance :" + Float.toString(distance));
                       	float horizontalSpeed = distance / BuoyParticle.getFlyTime();
                    	float verticalSpeed = BuoyParticle.getGravity() * BuoyParticle.getFlyTime() / 2.0F;
                       	
                       	
                       	Vector3 velocity = (new Vector3(buoy.getBlockDirection())).scalarMult(horizontalSpeed).add(new Vector3(0, verticalSpeed, 0));
                       	System.out.println(velocity.toString());
                        CustomParticles.BUOY.spawnParticle(world, x + 0.5F, y + 1, z + 0.5F, velocity.x, velocity.y, velocity.z);
                    }
                }
            }
        }
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
    	itemIcon = register.registerIcon("Watercraft:telescope");
    }

}
