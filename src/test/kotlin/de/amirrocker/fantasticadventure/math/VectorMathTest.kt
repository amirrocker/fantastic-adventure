package de.amirrocker.fantasticadventure.math

import de.amirrocker.fantasticadventure.math.Vector3
import de.amirrocker.fantasticadventure.math.asVector3
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class VectorMathTest {

    // TODO Vector Test - separate these tests
    @Test
    internal fun `given a vector when normalized then expext a unit vector with correct direction`() {
        val vector: Vector3 = asVector3(4.0, 6.0, 3.0)
//      optional factory method
//        val vector2 = asVector3(4.0, 6.0, 3.0) { x, y, z ->
//            Vector3(x, y, z)
//        }
        // sqrt(4*4 + 6*6 + 3*3) = 7,810249675906654
        val result: Vector3 = vector.normalize()
        assertEquals(0.5121475197315839, result.x, "Expect 0.5121475197315839 but was ${result.x}")
        assertEquals(0.7682212795973759, result.y, "Expect 0.7682212795973759 but was ${result.y}")
        assertEquals(0.3841106397986879, result.z, "Expect 0.3841106397986879 but was ${result.z}")
    }

    @Test
    internal fun `given a vector when magnitude then expect a floating point result`() {
        // given
        val vector: Vector3 = asVector3(4.0, 6.0, 3.0)
//      optional factory method
//        val vector2 = asVector3(4.0, 6.0, 3.0) { x, y, z ->
//            Vector3(x, y, z)
//        }

        // sqrt(4*4 + 6*6 + 3*3) = 7,810249675906654
        // when
        val result = vector.magnitude()

        // then
        assertEquals(7.810249675906654, result, "Expect 7,810249675906654 but was $result")
    }

    // Vector with functions tests - not yet started.
    @Test
    internal fun `given two 3D vectors when scalar product then we expect a single scalar as result`() {

        // given
        val va = asVector3(3.0, 4.0, 5.0)
        val vb = asVector3(3.0, 2.0, 1.0)

        val vResult = 22.0

        // when
        val result:Double = va.times(vb)

        // then
        assertEquals(vResult, result, "expect a vector $vResult but was $result")
    }

    @Test
    internal fun `given two 3D vectors when vector product then we expect the normal vector`() {
        // given
        val va = asVector3(3.0, 4.0, 5.0)
        val vb = asVector3(3.0, 2.0, 1.0)
        val vResult = asVector3(-6.0, 12.0, -6.0)

        // when
        val result = va % vb
        // then
        // then
        assertEquals(vResult, result, "expect a vector $vResult but was $result")
    }

    @Test
    internal fun `given x and z vectors when vector product then we expect the y vector`() {
        // given
        val va = asVector3(1.0, 0.0, 0.0)
        val vb = asVector3(0.0, 0.0, 1.0)
        val vResult = asVector3(0.0, -1.0, 0.0)

        // when
        val result = va % vb

        // then
        assertEquals(vResult, result, "expect a vector $vResult but was $result")
    }

    @Test
    internal fun `need at least two words`() {
        // make an orthonormal basis:
        // - normalize the starting vector A
        // - find vector C = { A x B | Vector3,Vector3 }
        // if C = zero magnitude -> then A and B are parallel -> give up
        // normalize C
        // make A and B orthogonal (right angled) -> B = C x A

        val va = asVector3(2.0, 4.0, 8.0)
        val vb = asVector3(2.0, 4.0, 8.0)

        val normalizedA = va.normalize()
        val C = normalizedA % vb
        val magnitudeC = C.magnitude()
        if(magnitudeC == 0.0) println("parallel vectors!")
        val normalizedC = C.normalize()
        val vbOrthogonal = normalizedC % normalizedA
        

    }

    @Test
    internal fun `test calculator method`() {
//        println(calculator)
    }

}