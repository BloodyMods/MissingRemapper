package atm.bloodworkxgaming.missingremapper

import atm.bloodworkxgaming.bloodyLib.registry.AbstractDataRegistry
import atm.bloodworkxgaming.bloodyLib.util.AbstractCommonHandler
import atm.bloodworkxgaming.bloodyLib.util.BloodyModMain
import atm.bloodworkxgaming.missingremapper.MissingRemapper.MOD_ID
import atm.bloodworkxgaming.missingremapper.MissingRemapper.MOD_NAME
import atm.bloodworkxgaming.missingremapper.MissingRemapper.VERSION
import atm.bloodworkxgaming.missingremapper.MissingRemapper.config
import atm.bloodworkxgaming.missingremapper.item.InbetweenItem
import atm.bloodworkxgaming.missingremapper.remapping.MaintainingMetaRemapper
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import atm.bloodworkxgaming.missingremapper.remapping.NbtPathRemapper
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod(modid = MOD_ID, name = MOD_NAME, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", version = VERSION)
object MissingRemapper : BloodyModMain(CommonHandler) {
    const val MOD_ID = "missingremapper"
    const val MOD_NAME = "MissingRemapper"
    const val VERSION = "0.1"

    var config: ModConfig? = null

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        println("MOD_ID = $MOD_ID")

        config = ModConfig(
                SingleRemap(
                        "test:item",
                        "missingremapper:inbetween1",
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "minecraft:coal",
                                NbtPath("test", "meta", "deep") remap { nbtIn, target -> 20 },
                                NbtPath("test", "blub", "deep") remap NbtPathRemapper("test", "lala")
                        )))
        )


        val soulshards = ModConfig(
                SingleRemap(
                        "soulshardstow:soul_shard",
                        "missingremapper:inbetweenshard",
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "soulshardsrespawn:soul_shard",
                                NbtPath("KillCount") remap NbtPathRemapper("binding", "kills"),
                                NbtPath("Entity") remap NbtPathRemapper("binding", "bound")
                        )))
        )

        config = soulshards

        println("config = $config")

        config?.remaps?.stream()?.filter { it.needsInBetweenItem }?.forEach {
            println("it = ${it}")

            DataRegistry.ITEMS += InbetweenItem(it)
        }

        println("DataRegistry.ITEMS = ${DataRegistry.ITEMS}")
    }
}

object DataRegistry : AbstractDataRegistry()

object CommonHandler : AbstractCommonHandler(modItems = ModItems) {
    @SubscribeEvent
    fun missingMapping(event: RegistryEvent.MissingMappings<Item>) {
        for (mapping in event.allMappings) {
            println("mapping = ${mapping.key}")
            val key = mapping.key.toString()
            val remap = config?.remaps?.firstOrNull { it.from == key }
            remap ?: continue

            println("remap = $remap")

            mapping.remap(Item.getByNameOrId(remap.toItem))
        }
    }
}