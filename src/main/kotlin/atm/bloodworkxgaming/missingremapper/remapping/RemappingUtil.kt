package atm.bloodworkxgaming.missingremapper.remapping

import atm.bloodworkxgaming.missingremapper.extensions.get
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound

fun remapNBT(nbtIn: NBTTagCompound, nbtRemap: Map<NbtPath, INbtRemapper>, target: NBTTagCompound = NBTTagCompound()): NBTTagCompound {
    nbtRemap.forEach { from, remapper ->
        val iter: NBTBase? = nbtIn[from]
        iter ?: return@forEach

        remapper.remap(iter, target)
    }

    return target
}