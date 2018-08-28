package atm.bloodworkxgaming.missingremapper.item

import atm.bloodworkxgaming.missingremapper.SingleRemap
import atm.bloodworkxgaming.missingremapper.remapping.remapNBT
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ITickable
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class InbetweenItem(private val remap: SingleRemap) : Item(), ITickable {
    init {
        this.registryName = ResourceLocation(remap.toItem)
        this.unlocalizedName = remap.toItem
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        playerIn.setHeldItem(EnumHand.MAIN_HAND, convert(playerIn.heldItemMainhand))
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    override fun update() {
        println("I am ticking! $registryName")
    }

    private fun convert(stack: ItemStack): ItemStack {
        val meta = remap.metaRemap[stack.metadata]
        meta ?: throw RemappingException("metaspecifc is null")
        val finalItem = Item.getByNameOrId(meta.finalItem) ?: throw RemappingException("finalItem could not be found")
        val finalStack = ItemStack(finalItem, stack.count, meta.targetMeta)

        val nbtOld = stack.tagCompound ?: return finalStack // return early if there is no nbt to copy
        meta.nbtRemap ?: return finalStack

        finalStack.tagCompound = remapNBT(nbtOld, meta.nbtRemap)
        return finalStack
    }
}


class RemappingException(message: String, exception: Exception? = null) : Exception(message, exception)