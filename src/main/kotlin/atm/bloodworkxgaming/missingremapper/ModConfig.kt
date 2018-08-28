package atm.bloodworkxgaming.missingremapper

import atm.bloodworkxgaming.missingremapper.remapping.IMetaRemapper
import atm.bloodworkxgaming.missingremapper.remapping.INbtRemapper
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import atm.bloodworkxgaming.missingremapper.remapping.NullMetaRemapper

/*
What should this support?
simple: item -> different item directly
complex: stuff that requires a constructed inbetween item
    meta remap
 */

data class ModConfig(val remaps: List<SingleRemap>) {
    constructor(vararg remaps: SingleRemap) : this(remaps.toList())
}

enum class RemapType {
    ITEM, BLOCK
}

data class SingleRemap(
        val from: String,
        val toItem: String,
        val type: RemapType,
        val needsInBetweenIstem: Boolean = false,
        val metaRemap: IMetaRemapper = NullMetaRemapper()
)

sealed class AMetaSpecificChange {
    abstract val targetMeta: Int
    abstract val finalItem: String?
    abstract val nbtRemap: Map<NbtPath, INbtRemapper>?
}

data class MetaSpecificChangeItem(
        override val targetMeta: Int,
        override val finalItem: String?,
        override val nbtRemap: Map<NbtPath, INbtRemapper>? = null
) : AMetaSpecificChange() {
    constructor(targetMeta: Int, finalItem: String?, vararg nbtRemaps: Pair<NbtPath, INbtRemapper>)
            : this(targetMeta, finalItem, nbtRemaps.toMap())
}

data class MetaSpecificChangeBlock(
        override val targetMeta: Int,
        override val finalItem: String?,
        override val nbtRemap: Map<NbtPath, INbtRemapper>? = null
) : AMetaSpecificChange() {
    constructor(targetMeta: Int, finalItem: String?, vararg nbtRemaps: Pair<NbtPath, INbtRemapper>)
            : this(targetMeta, finalItem, nbtRemaps.toMap())
}

