package atm.bloodworkxgaming.missingremapper.extensions

import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import net.minecraft.nbt.*
import java.util.*

operator fun NBTBase?.get(key: NbtPath): NBTBase? {
    var iter: NBTBase? = this

    for (s in key) {
        iter = when (iter) {
            null -> return null
            is NBTTagCompound -> iter.getTag(s)
            else -> return null
        }
    }

    return iter
}

operator fun NBTBase?.get(key: String): NBTBase? = this[NbtPath(key)]
operator fun NBTBase?.get(key: Int): NBTBase? {
    if (this is NBTTagList) {
        val res =  this.get(key)
        if (res !is NBTTagEnd) return res
    }

    return null
}

operator fun NBTTagCompound.set(key: NbtPath, value: NBTBase) {
    val last = key.path.size - 1
    var iter = this

    key.forEachIndexed { index, s ->
        if (index == last) {
            iter.setTag(s, value)
        } else {
            iter = iter.getCompoundTag(s).also { iter.setTag(s, it) }
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun nbt(map: Map<String, Any>): NBTTagCompound {
    val nbt = NBTTagCompound()

    map.forEach { key, value ->
        when (value) {
            is Byte -> nbt.setByte(key, value)
            is Short -> nbt.setShort(key, value)
            is Int -> nbt.setInteger(key, value)
            is Long -> nbt.setLong(key, value)
            is UUID -> nbt.setUniqueId(key, value)
            is Float -> nbt.setFloat(key, value)
            is Double -> nbt.setDouble(key, value)
            is String -> nbt.setString(key, value)
            is ByteArray -> nbt.setByteArray(key, value)
            is IntArray -> nbt.setIntArray(key, value)
            is Boolean -> nbt.setBoolean(key, value)
            is NBTTagCompound -> nbt.setTag(key, value)
            is Map<*, *> -> nbt.setTag(key, nbt(value as Map<String, Any>))
        }
    }

    return nbt
}

fun nbt(vararg pairs: Pair<String, Any>): NBTTagCompound = nbt(pairs.toMap())

infix fun String.nbtTo(s: String): NbtPath {
    return NbtPath(this, s)
}