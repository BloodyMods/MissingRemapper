package atm.bloodworkxgaming.missingremapper.block

import atm.bloodworkxgaming.missingremapper.SingleRemap
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import atm.bloodworkxgaming.missingremapper.remapping.NbtPathRemapper
import atm.bloodworkxgaming.missingremapper.remapping.remapNBT
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumActionResult
import net.minecraft.util.ITickable

class InbetweenTile(val remap: SingleRemap, private val meta: Int) : TileEntity(), ITickable {
    init {
    }

    override fun update() {
        if (world.isRemote) return

        println("I am ticking")
        val nbtOld = serializeNBT()
        val finalItem = remap.metaRemap[meta]?.finalItem
        if (finalItem == null) {
            println("Couldn't get blockname for meta $meta for remap $remap" )
            return
        }

        val block = Block.getBlockFromName(finalItem)
        block ?: return

        val state = block.getStateFromMeta(meta)
        println("block.getStateFromMeta(meta) = $state")

        world.setBlockState(pos, state, 2 or 16)

        val tile = world.getTileEntity(pos)
        val nbtNew = tile?.serializeNBT()

        println("tileOld: $nbtOld")
        println("tile: $nbtNew")


        val remapper = mapOf(NbtPath("Items") remap NbtPathRemapper("Items"))

        if (nbtNew != null) {
            remapNBT(nbtOld, remapper, nbtNew)

            tile.readFromNBT(nbtNew)
            println("nbtRemapped: $nbtNew")

            tile.serializeNBT()
            println("nbtSerialized: $nbtNew")

        }

    }
}