package atm.bloodworkxgaming.missingremapper

import atm.bloodworkxgaming.bloodyLib.registry.AbstractModItems
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import atm.bloodworkxgaming.missingremapper.remapping.NbtPathRemapper
import atm.bloodworkxgaming.missingremapper.remapping.remapNBT
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object ModItems : AbstractModItems(DataRegistry) {
    val test = TestItem()
}

class TestItem : Item() {
    init {
        registryName = ResourceLocation("blub", "item2")
        creativeTab = CreativeTabs.COMBAT
        DataRegistry.ITEMS += this
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (worldIn.isRemote) return EnumActionResult.SUCCESS

        val nbtOld = worldIn.getTileEntity(pos)?.serializeNBT()
        worldIn.removeTileEntity(pos)
        worldIn.setBlockState(pos, Blocks.CHEST.getStateFromMeta(0), 2 or 16)

        val tile = worldIn.getTileEntity(pos)
        val nbtNew = tile?.serializeNBT()

        println("tileOld: $nbtOld")
        println("tile: $nbtNew")


        val remapper = mapOf(NbtPath("Items") remap NbtPathRemapper("Items"))

        if (nbtOld != null && nbtNew != null) {
            remapNBT(nbtOld, remapper, nbtNew)

            tile.readFromNBT(nbtNew)
            println("nbtRemapped: $nbtNew")

            tile.serializeNBT()
            println("nbtSerialized: $nbtNew")

        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }
}