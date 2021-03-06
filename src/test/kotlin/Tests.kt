import atm.bloodworkxgaming.missingremapper.MetaSpecificChange
import atm.bloodworkxgaming.missingremapper.ModConfig
import atm.bloodworkxgaming.missingremapper.RemapType
import atm.bloodworkxgaming.missingremapper.SingleRemap
import atm.bloodworkxgaming.missingremapper.extensions.get
import atm.bloodworkxgaming.missingremapper.extensions.nbt
import atm.bloodworkxgaming.missingremapper.extensions.nbtTo
import atm.bloodworkxgaming.missingremapper.remapping.MaintainingMetaRemapper
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import org.junit.jupiter.api.Test
import javax.script.ScriptEngineManager

class Tests {
    @Test
    fun test1() {
        val config = ModConfig(
                emptyList(),
                SingleRemap(
                        "test:item",
                        "missingremapper:inbetween1",
                        RemapType.ITEM,
                        true,
                        MaintainingMetaRemapper(MetaSpecificChange(
                                -1,
                                "blub:item2",
                                mapOf(
                                        NbtPath("test", "meta", "deep") remap { nbtIn, target -> 20 }
                                )
                        )))
        )

        println("config = $config")
    }

    @Test
    fun testNBT() {
        val n = nbt(
                "Test" to "potato",
                "mashed" to "lol",
                "lel" to nbt("lala" to "lel", "loops" to 2)
        )

        val test = n["lel" nbtTo "lala"]
        val test2 = n["lel" nbtTo "loops"]
        val test3 = n["lel" nbtTo "nope"]
        val test4 = n[NbtPath()]
        val test5 = n[NbtPath("lel")]

        println("test = $test")
        println("test2 = $test2")
        println("test3 = $test3")
        println("test4 = $test4")
        println("test5 = $test5")
    }
}