package atm.bloodworkxgaming.missingremapper.remapping

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound

data class NbtPath(val path: List<String>) : Iterable<String> {
    override fun iterator(): Iterator<String> = path.iterator()
    infix fun to(s: String): NbtPath {
        return this.copy(path = listOf(*path.toTypedArray(), s))
    }

    constructor(vararg strings: String) : this(strings.toList())
    constructor() : this(emptyList())

    infix fun remap(remapper: NbtPath): Pair<NbtPath, INbtRemapper> {
        return Pair(this, NbtPathRemapper(remapper))
    }

    infix fun remap(remapper: INbtRemapper): Pair<NbtPath, INbtRemapper> {
        return Pair(this, remapper)
    }

    infix fun remap(function: (nbt: NBTBase, target: NBTTagCompound) -> Unit): Pair<NbtPath, INbtRemapper> {
        return Pair(this, CustomNbtRemapper(function))
    }
}