package atm.bloodworkxgaming.missingremapper.remapping

import atm.bloodworkxgaming.missingremapper.MetaSpecificChange

interface IMetaRemapper {
    operator fun get(key: Int): MetaSpecificChange?
}

class WildCardMetaRemapper(private val metaRemap: MetaSpecificChange) : IMetaRemapper {
    override fun get(key: Int) = metaRemap
}

class NullMetaRemapper : IMetaRemapper {
    override fun get(key: Int): MetaSpecificChange? = null
}

class MaintainingMetaRemapper(private val metaRemap: MetaSpecificChange) : IMetaRemapper {
    override fun get(key: Int) = metaRemap.copy(targetMeta = key)
}

class MapMetaRemapper(private val map: Map<Int, MetaSpecificChange>) : IMetaRemapper {
    override fun get(key: Int) = map[key]
}
