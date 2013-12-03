package dgrxf.watercraft.block.port;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.DirectionalBlock;
import dgrxf.watercraft.tileentity.port.TileEntityHolding;

/**
 * HoldingBlock
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class HoldingBlock extends DirectionalBlock implements ITileEntityProvider {
    
    public HoldingBlock(int id) {
        super(id, Material.wood);
        
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityHolding();
    }
    
}
