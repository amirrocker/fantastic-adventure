package de.amirrocker.fantasticadventure.math

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestMath_Matrix {

    val data_1x4_valid =  arrayOf( 0.0, 1.0, 2.0, 3.0 )
    val data_1x4_invalid =  arrayOf( 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 )

    val data_2x2_valid =  arrayOf( 0.0, 1.0, 2.0, 3.0 )
    val data_2x2_invalid =  arrayOf( 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 )

    val data_3x4_valid =  arrayOf( 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0 )
    val data_3x4_invalid =  arrayOf( 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 11.1 )
    val data_even =  arrayOf( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 )
    val data_odd =  arrayOf( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 )
    val data_empty =  emptyArray<Int>() // arrayOf( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 )

    val shape_1x4 = Pair(1, 4)
    val shape_2x2 = Pair(2, 2)
    val shape_2x4 = Pair(2, 4)
    val shape_3x4 = Pair(3, 4)
    val shape_4x2 = Pair(4, 2)
    val shape_4x3 = Pair(4, 3)
    val shape_4x4 = Pair(4, 4)

    @Test
    internal fun `test matrix_1x4 creation`() {
        println("test matrix creation ....")

        val matrix = Matrix.asMatrix(shape = shape_1x4, data_1x4_valid)

        assertTrue(matrix.asShapedData().size == 1, "Expect a 1D array with 1 item ")
        assertTrue(matrix.asShapedData()[0].size == 4, "Expect a 1D array with 4 column items ")

    }

    @Test
    internal fun `test matrix_2x2 creation`() {
        println("test squared matrix 2x2 creation ....")

        val matrix = Matrix.asMatrix(shape = shape_2x2, data_2x2_valid)

        assertTrue(matrix.asShapedData().size == 2, "Expect a 2D array with 2 items ")
        assertTrue(matrix.asShapedData()[0].size == 2, "Expect a 2D array with 2 column items ")

    }

    @Test
    internal fun `test matrix_2x2 creation invalid inputs`() {
        println("test squared matrix 2x2 creation ....")
        val exception = assertThrows<AssertionError> {
            val matrix = Matrix.asMatrix(shape = shape_2x2, data_2x2_invalid)
        }
    }

    @Test
    internal fun `test the creator function for a homogenous matrix`() {

        val floatArray = floatArrayOf(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f,
        )

        val matrix4x4 = creator(floatArray, Pair(4, 4))
        println("created matrix is $matrix4x4 ")

        val vM0x0 = matrix4x4.get(0, 0)
        assertEquals(vM0x0, floatArray[0], "Expect vM0x0 to be ${floatArray[0]} but was ${vM0x0}")

    }

    @Test
    internal fun `test multiply vector3 with skalar`() {

        val v1 = Vector3(1.0f, 2.0f, 0.0f)
        val s = 42.0f
        val result = multiplySkalarWithColumnVector3(s, v1)

        assertEquals(42.0f, result.x, "expect a valid x component but was ${result.x}")
        assertEquals(84.0f, result.y, "expect a valid y component but was ${result.y}")
        assertEquals(0.0f, result.z, "expect a valid z component but was ${result.z}")

    }

    @Test
    internal fun `test divide vector3 with skalar`() {
        val v1 = Vector3(1.0f, 2.0f, 0.0f)
        val s = 42.0f
        val result = divideColumnVector3BySkalar(s, v1)

        assertEquals(1.0f.div(42.0f), result.x, "expect a valid x component but was ${result.x}")
        assertEquals(2.0f/42.0f, result.y, "expect a valid y component but was ${result.y}")
        assertEquals(0.0f/42.0f, result.z, "expect a valid z component but was ${result.z}")
    }

    @Test
    internal fun `test pass vector3 where vector4 is expected prints log line`() {

        val vector4 = Vector4(1.0f, 2.0f, 0.0f, 0.0f)
        val s = 42.0f
        val result = multiplySkalarWithColumnVector3(s, vector4)

    }

    @Test
    internal fun `test divide vector3 by zero fails with grace`() {

        assertThrows<java.lang.AssertionError> {
            val v1 = Vector4(1.0f, 2.0f, 0.0f, 1.0f)
            val s = 0.0f
            val result = divideColumnVector3BySkalar(s, v1)

            assertEquals(0.0f, result.x, "expect value")

        }

    }

    
}