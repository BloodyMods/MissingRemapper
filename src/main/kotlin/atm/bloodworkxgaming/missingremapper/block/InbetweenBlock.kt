package atm.bloodworkxgaming.missingremapper.block

import atm.bloodworkxgaming.missingremapper.SingleRemap
import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class InbetweenBlock(private val remap: SingleRemap) : Block(Material.BARRIER), ITileEntityProvider {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? {
        return InbetweenTile(remap, meta)
    }

    init {
        registryName = ResourceLocation(remap.toItem)
    }
}