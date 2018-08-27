import atm.bloodworkxgaming.missingremapper.MetaSpecificChange
import atm.bloodworkxgaming.missingremapper.ModConfig
import atm.bloodworkxgaming.missingremapper.SingleRemap
import atm.bloodworkxgaming.missingremapper.remapping.MaintainingMetaRemapper
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.script.ScriptEngineManager

class Tests {
    @Test
    fun test1() {
        val config = ModConfig(
                SingleRemap(
                        "test:item",
                        "missingremapper:inbetween1",
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
    fun testScripts(){
        val engine = ScriptEngineManager().getEngineByExtension("kts")
        println("engine = ${engine}")
        org.jetbrains.kotlin.script.DEFAULT_SCRIPT_FILE_PATTERN
        /*with(ScriptEngineManager().getEngineByExtension("kts")) {
            eval("val x = 3")
            val res2 = eval("x + 2")
            Assertions.assertEquals(5, res2)
        }*/
    }
}