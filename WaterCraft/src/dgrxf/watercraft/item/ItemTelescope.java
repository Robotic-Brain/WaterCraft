package dgrxf.watercraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.particles.CustomParticles;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;
import dgrxf.watercraft.util.Vector3;

public class ItemTelescope extends Item{

    private int tick;
    private boolean showParticles;
    private float particleSpeed = 5.0F;
    
    public ItemTelescope() {
        super(ItemInfo.TELESCOPE_ID);
        setUnlocalizedName(ItemInfo.TELESCOPE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.creativeTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            Watercraft.printToPlayer("click");
            Watercraft.printToPlayer(Integer.toString(world.getBlockId(x, y, z)));
            
            if (world.getBlockId(x, y, z) == ModBlocks.buoy.blockID) {
                Watercraft.printToPlayer("found buoy");
                TileEntity te = world.getBlockTileEntity(x, y, z);
                if (te instanceof WCTileEntityBuoy) {
                    Watercraft.printToPlayer("found working buoy");
                    WCTileEntityBuoy buoy = (WCTileEntityBuoy) te;
                    Vector3 velocity = (new Vector3(buoy.getBuoyDirection())).scalarMult(particleSpeed);
                    CustomParticles.BUOY.spawnParticle(world, x + 0.5F, y + 1, z + 0.5F, velocity.x, 0.5, velocity.z);
                    
                    if (buoy.hasNextBuoy()) {
                        //TODO spawn particle going to the next buoy
                        CustomParticles.BUOY.spawnParticle(world, x + 0.5F, y + 1, z + 0.5F, velocity.x, 0.5, velocity.z);
                        Watercraft.printToPlayer("found");
                    }
                }
            }
        }
        
        return false;
    }

}
