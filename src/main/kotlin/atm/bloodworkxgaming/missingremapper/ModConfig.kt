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

data class SingleRemap(
        val from: String,
        val toItem: String,
        val needsInBetweenItem: Boolean = false,
        val metaRemap: IMetaRemapper = NullMetaRemapper()
)

data class MetaSpecificChange(
        val targetMeta: Int,
        val finalItem: String?,
        val nbtRemap: Map<NbtPath, INbtRemapper>? = null
) {
    constructor(targetMeta: Int,
                finalItem: String?,
                vararg nbtRemaps: Pair<NbtPath, INbtRemapper>) : this(targetMeta, finalItem, nbtRemaps.toMap())
}