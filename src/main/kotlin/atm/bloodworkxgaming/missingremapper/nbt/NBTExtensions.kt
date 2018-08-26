package atm.bloodworkxgaming.missingremapper.nbt

import net.minecraft.nbt.NBTTagCompound
import java.util.*

/*operator fun NBTTagCompound.get(key: String) {
    this.getCompoundTag("")
}*/

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
            is Map<*,*> -> nbt.setTag(key, nbt(value as Map<String, Any>))
        }
    }

    return nbt
}

fun nbt(vararg pairs: Pair<String, Any>): NBTTagCompound = nbt(pairs.toMap())