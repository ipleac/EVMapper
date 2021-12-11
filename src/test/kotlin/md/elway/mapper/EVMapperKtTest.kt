package md.elway.mapper

import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.Test

internal class EVMapperKtTest {
    @Test
    fun testBase1() {
        data class AData(val p1: Int, val p2: Int)
        data class BData(val p1: Int, val p2: Int)

        val aData = AData(1, 2)
        val bData = aData.mapTo<BData> {}

        assertNotNull(bData)
        assertEquals(aData.p1, bData.p1)
        assertEquals(aData.p2, bData.p2)
    }

    @Test
    fun testBase2() {
        data class AData(val p1: Int, val p2: Int)
        data class BData(val p1: Int, val p2: Int, val p3: Int?)

        val aData = AData(1, 2)
        val bData = aData.mapTo<BData> {}

        assertNotNull(bData)
        assertEquals(aData.p1, bData.p1)
        assertEquals(aData.p2, bData.p2)
        assertNull(bData.p3)
    }

    @Test
    fun testBase3() {
        data class AData(val p1: Int, val p2: Int, val p3: Int)
        data class BData(val p1: Int, val p2: Int)

        val aData = AData(1, 2, 3)
        val bData = aData.mapTo<BData> {}

        assertNotNull(bData)
        assertEquals(aData.p1, bData.p1)
        assertEquals(aData.p2, bData.p2)
    }

    @Test
    fun testBinding1() {
        data class AData(val p1: Int, val p2: Int)
        data class BData(val p1: Int, val p3: Int)

        val aData = AData(1, 2)
        val bData = aData.mapTo<BData> {
            binds = listOf(
                Bind<Int, Int>("p2" to "p3")
            )
        }

        assertNotNull(bData)
        assertEquals(aData.p1, bData.p1)
        assertEquals(aData.p2, bData.p3)
    }

    @Test
    fun testBinding2() {
        data class AData(val p1: Int, val p2: Int)
        data class BData(val p1: Int, val p2: Int, val p3: Int)

        val aData = AData(1, 2)
        val bData = aData.mapTo<BData> {
            binds = listOf(
                Bind<Int, Int>("p2" to "p3")
            )
        }

        assertNotNull(bData)
        assertEquals(aData.p1, bData.p1)
        assertEquals(aData.p2, bData.p2)
        assertEquals(aData.p2, bData.p3)
    }

    @Test
    fun testBindingWithConvert1() {
        data class AData(val p1: Int, val p2: Int)
        data class BData(val p1: String, val p2: String)

        val aData = AData(1, 2)
        val bData = aData.mapTo<BData> {
            binds = listOf(
                Bind<Int, String>("p1" to "p1", Convert { it.toString() }),
                Bind<Int, String>("p2" to "p2", Convert { it.toString() })
            )
        }

        assertNotNull(bData)
    }

    @Test
    fun testBindingWithConvert2() {
        data class AData(val p1: Int, val p2: String, val p3: Int)
        data class BData(val p1: String, val p2: String, val p3: String)

        val aData = AData(1, "3", 3)
        val bData = aData.mapTo<BData> {
            binds = listOf(
                Bind<Int, String>("p1" to "p1", Convert { it.toString() }),
                Bind<Int, String>("p3" to "p3", Convert { it.toString() })
            )
        }

        assertNotNull(bData)
    }

    @Test
    fun testConvert1() {
        data class AData(val p1: Instant, val p2: Instant)
        data class BData(val p1: LocalDateTime, val p2: LocalDateTime)

        val aData = AData(Instant.now(), Instant.now())
        val bData = aData.mapTo<BData> {
            converters = listOf(
                Convert<Instant, LocalDateTime> { LocalDateTime.ofInstant(it, ZoneOffset.UTC) }
            )
        }
        assertNotNull(bData)
    }

    @Test
    fun testConvert2() {
        data class AData(val p1: String, val p2: Instant, val p3: Long)
        data class BData(val p1: String, val p2: LocalDateTime, val p3: Long)

        val aData = AData("p1", Instant.now(), 3L)
        val bData = aData.mapTo<BData> {
            converters = listOf(
                Convert<Instant, LocalDateTime> { LocalDateTime.ofInstant(it, ZoneOffset.UTC) }
            )
        }

        assertNotNull(bData)
    }

}