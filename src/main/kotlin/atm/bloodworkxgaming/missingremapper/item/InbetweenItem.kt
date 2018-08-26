package atm.bloodworkxgaming.missingremapper.item

import atm.bloodworkxgaming.missingremapper.SingleRemap
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class InbetweenItem(private val remap: SingleRemap) : Item() {
    init {
        this.registryName = ResourceLocation(remap.toItem)
        this.unlocalizedName = remap.toItem
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        playerIn.setHeldItem(EnumHand.MAIN_HAND, convert(playerIn.heldItemMainhand))
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    fun convert(stack: ItemStack): ItemStack {
        val meta = remap.metaRemap[stack.metadata]
        meta ?: throw RemappingException("metaspecifc is null")
        meta.finalItem ?: throw RemappingException("finalItem name is null")
        val finalItem = Item.getByNameOrId(meta.finalItem) ?: throw RemappingException("finalItem could not be found")
        val finalStack = ItemStack(finalItem, stack.count, meta.targetMeta)

        val nbtOld = stack.tagCompound ?: return finalStack // return early if there is no nbt to copy
        val tag = NBTTagCompound()



        finalStack.tagCompound = tag
        return finalStack
    }
}


class RemappingException(message: String) : Exception(message)