package atm.bloodworkxgaming.missingremapper.remapping

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound

data class NbtPath(val path: List<String>) {
    constructor(vararg strings: String) : this(strings.toList())

    infix fun remap(remapper: INbtRemapper): Pair<NbtPath, INbtRemapper> {
        return Pair(this, remapper)
    }

    infix fun remap(function: (nbt: NBTBase, target: NBTTagCompound) -> Int): Pair<NbtPath, INbtRemapper> {
        return Pair(this, CustomNbtRemapper(function))
    }
}

interface INbtRemapper {
    fun remap(nbtIn: NBTBase, target: NBTTagCompound)
}

class CustomNbtRemapper(private val function: (nbt: NBTBase, target: NBTTagCompound) -> Int) : INbtRemapper {
    override fun remap(nbtIn: NBTBase, target: NBTTagCompound) {
        function(nbtIn, target)
    }
}

class NbtPathRemapper(private val pathTo: NbtPath, private val func: (nbtIn: NBTBase) -> NBTBase = { it }) : INbtRemapper {
    constructor(vararg strings: String): this(NbtPath(strings.toList()))

    override fun remap(nbtIn: NBTBase, target: NBTTagCompound) {
        val last = pathTo.path.size - 1
        var iter = target

        pathTo.path.forEachIndexed { index, s ->
            if (index == last) {
                iter.setTag(s, func(nbtIn))
            } else {
                iter = iter.getCompoundTag(s).also { iter.setTag(s, it) }
            }
        }
    }
}