package atm.bloodworkxgaming.missingremapper.block

import atm.bloodworkxgaming.missingremapper.SingleRemap
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.ResourceLocation

class InbetweenBlock(private val remap: SingleRemap) : Block(Material.BARRIER) {
    init {
        registryName = ResourceLocation(remap.toItem)
    }
}