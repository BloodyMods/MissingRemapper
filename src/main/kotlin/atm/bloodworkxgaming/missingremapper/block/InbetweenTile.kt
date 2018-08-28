package atm.bloodworkxgaming.missingremapper.block

import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraftforge.fml.common.registry.GameRegistry

class InbetweenTile : TileEntity(), ITickable {
    init {
        GameRegistry.registerTileEntity()
    }
    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}