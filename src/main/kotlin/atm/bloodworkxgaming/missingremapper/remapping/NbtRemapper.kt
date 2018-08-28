package atm.bloodworkxgaming.missingremapper.remapping

import atm.bloodworkxgaming.missingremapper.extensions.set
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound

interface INbtRemapper {
    fun remap(nbtIn: NBTBase, target: NBTTagCompound)
}

class CustomNbtRemapper(private val function: (nbt: NBTBase, target: NBTTagCompound) -> Int) : INbtRemapper {
    override fun remap(nbtIn: NBTBase, target: NBTTagCompound) {
        function(nbtIn, target)
    }
}

class NbtPathRemapper(private val pathTo: NbtPath, private val func: (nbtIn: NBTBase) -> NBTBase = { it }) : INbtRemapper {
    constructor(vararg strings: String) : this(NbtPath(strings.toList()))

    override fun remap(nbtIn: NBTBase, target: NBTTagCompound) {
        target[pathTo] = func(nbtIn)
    }
}