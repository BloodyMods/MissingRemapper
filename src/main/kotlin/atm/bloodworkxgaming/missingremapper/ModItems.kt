package atm.bloodworkxgaming.missingremapper

import atm.bloodworkxgaming.bloodyLib.registry.AbstractModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object ModItems : AbstractModItems(DataRegistry) {
    val test = TestItem()
}

class TestItem : Item() {
    init {
        registryName = ResourceLocation("blub", "item2")
        creativeTab = CreativeTabs.COMBAT
        DataRegistry.ITEMS += this
    }
}