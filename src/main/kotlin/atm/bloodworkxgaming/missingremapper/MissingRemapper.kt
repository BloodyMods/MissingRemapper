package atm.bloodworkxgaming.missingremapper

import atm.bloodworkxgaming.bloodyLib.registry.AbstractDataRegistry
import atm.bloodworkxgaming.bloodyLib.registry.AbstractModBlocks
import atm.bloodworkxgaming.bloodyLib.util.AbstractCommonHandler
import atm.bloodworkxgaming.bloodyLib.util.BloodyModMain
import atm.bloodworkxgaming.missingremapper.MissingRemapper.MOD_ID
import atm.bloodworkxgaming.missingremapper.MissingRemapper.MOD_NAME
import atm.bloodworkxgaming.missingremapper.MissingRemapper.VERSION
import atm.bloodworkxgaming.missingremapper.MissingRemapper.config
import atm.bloodworkxgaming.missingremapper.block.InbetweenBlock
import atm.bloodworkxgaming.missingremapper.block.InbetweenTile
import atm.bloodworkxgaming.missingremapper.extensions.get
import atm.bloodworkxgaming.missingremapper.extensions.set
import atm.bloodworkxgaming.missingremapper.item.InbetweenItem
import atm.bloodworkxgaming.missingremapper.remapping.MaintainingMetaRemapper
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import atm.bloodworkxgaming.missingremapper.remapping.NbtPathRemapper
import atm.bloodworkxgaming.missingremapper.remapping.remapNBT
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

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
                listOf("missingremapper:chest"),
                SingleRemap(
                        "test:item",
                        "missingremapper:inbetween1",
                        RemapType.ITEM,
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "minecraft:coal",
                                NbtPath("test", "meta", "deep") remap { nbtIn, target -> 20 },
                                NbtPath("test", "blub", "deep") remap NbtPathRemapper("test", "lala")
                        ))),
                SingleRemap(
                        "minecraft:chest",
                        "minssingremapper.inbetweenblock1",
                        RemapType.BLOCK,
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "minecraft:chest",
                                NbtPath("test") remap NbtPath("Path")
                        ))
                )
        )


        val soulshards = ModConfig(
                listOf("soulshardstow:cage"),
                SingleRemap(
                        "soulshardstow:soul_shard",
                        "missingremapper:inbetweenshard",
                        RemapType.ITEM,
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "soulshardsrespawn:soul_shard",
                                NbtPath("KillCount") remap NbtPathRemapper("binding", "kills"),
                                NbtPath("Entity") remap NbtPathRemapper("binding", "bound")
                        ))),
                SingleRemap(
                        "soulshardstow:cage",
                        "missingremapper:inbetweencage",
                        RemapType.BLOCK,
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "soulshardsrespawn:soul_cage",
                                NbtPath("activeTime") remap NbtPathRemapper("activeTime"),
                                NbtPath("Items") remap { nbt, target ->
                                    if (nbt is NBTTagCompound) {
                                        val itemNbt = nbt["Items"][0] as NBTTagCompound

                                        val list = mapOf(NbtPath("KillCount") remap NbtPathRemapper("binding", "kills"),
                                                NbtPath("Entity") remap NbtPathRemapper("binding", "bound"),
                                                NbtPath("Slot") remap NbtPathRemapper("Slot"))

                                        val newItem = remapNBT(itemNbt, list)

                                        val tags = NBTTagList()

                                        tags.appendTag(newItem)

                                        target[NbtPath("inventory")] = NBTTagList()
                                    }
                                }
                        )))
        )

        config = soulshards

        println("config = $config")

        config?.remaps?.stream()?.filter { it.needsInBetweenItem }?.forEach {
            println("it = $it")
            when (it.type) {
                RemapType.ITEM -> DataRegistry.ITEMS += InbetweenItem(it)
                RemapType.BLOCK -> DataRegistry.BLOCKS += InbetweenBlock(it)
            }
        }

        config?.tileIds?.forEach { GameRegistry.registerTileEntity(InbetweenTile::class.java, ResourceLocation(it)) }

        println("DataRegistry.ITEMS = ${DataRegistry.ITEMS}")
    }
}

object DataRegistry : AbstractDataRegistry()
object ModBlock : AbstractModBlocks(DataRegistry)

object CommonHandler : AbstractCommonHandler(modItems = ModItems, modBlocks = ModBlock) {
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

    @SubscribeEvent
    fun missingMappingBlock(event: RegistryEvent.MissingMappings<Block>) {
        for (mapping in event.allMappings) {
            println("mapping = ${mapping.key}")
            val key = mapping.key.toString()
            val remap = config?.remaps?.firstOrNull { it.from == key }
            remap ?: continue

            println("remap = $remap")

            mapping.remap(Block.getBlockFromName(remap.toItem))
            println("Action of $mapping = ${mapping.action}")

            // mapping.remap(Blocks.DIAMOND_BLOCK)
        }
    }
}