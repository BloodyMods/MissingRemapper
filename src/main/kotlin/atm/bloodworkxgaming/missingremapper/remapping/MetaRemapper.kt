package atm.bloodworkxgaming.missingremapper.remapping

import atm.bloodworkxgaming.missingremapper.MetaSpecificChangeItem

interface IMetaRemapper {
    operator fun get(key: Int): MetaSpecificChangeItem?
}

class WildCardMetaRemapper(private val metaRemap: MetaSpecificChangeItem) : IMetaRemapper {
    override fun get(key: Int) = metaRemap
}

class NullMetaRemapper : IMetaRemapper {
    override fun get(key: Int): MetaSpecificChangeItem? = null
}

class MaintainingMetaRemapper(private val metaRemap: MetaSpecificChangeItem) : IMetaRemapper {
    override fun get(key: Int) = metaRemap.copy(targetMeta = key)
}

class MapMetaRemapper(private val map: Map<Int, MetaSpecificChangeItem>) : IMetaRemapper {
    override fun get(key: Int) = map[key]
}
