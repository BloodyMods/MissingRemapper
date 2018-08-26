import atm.bloodworkxgaming.missingremapper.MetaSpecificChange
import atm.bloodworkxgaming.missingremapper.ModConfig
import atm.bloodworkxgaming.missingremapper.SingleRemap
import atm.bloodworkxgaming.missingremapper.remapping.MaintainingMetaRemapper
import atm.bloodworkxgaming.missingremapper.remapping.NbtPath
import org.junit.jupiter.api.Test

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

    }
}