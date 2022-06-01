package de.amirrocker.fantasticadventure.math

import de.amirrocker.fantasticadventure.math.Matrix
import de.amirrocker.fantasticadventure.math.MatrixElement
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MatrixMathTest {

    val data_1x4_valid = arrayOf(0.0, 1.0, 2.0, 3.0)
    val data_1x4_invalid = arrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0)

    val data_float_2x2_valid = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
    val data_2x2_valid = arrayOf(0.0, 1.0, 2.0, 3.0)
    val data_2x2_invalid = arrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0)

    val data_3x4_valid = arrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0)
    val data_3x4_invalid = arrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 11.1)
    val data_even = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val data_odd = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)
    val data_empty = emptyArray<Int>() // arrayOf( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 )

    val shape_1x4 = Pair(1, 4)
    val shape_2x2 = Pair(2, 2)
    val shape_2x4 = Pair(2, 4)
    val shape_3x4 = Pair(3, 4)
    val shape_4x2 = Pair(4, 2)
    val shape_4x3 = Pair(4, 3)
    val shape_4x4 = Pair(4, 4)

    @Test
    internal fun `test matrix_1x4 creation`() {
        // when
        val matrix = Matrix.asMatrix(shape = shape_1x4, data_1x4_valid)

        // then
        assertTrue(matrix.asShapedData().size == 1, "Expect a 1D array with 1 item ")
        assertTrue(matrix.asShapedData()[0].size == 4, "Expect a 1D array with 4 column items ")
    }

    @Test
    internal fun `test matrix_2x2 creation`() {
        // when
        val matrix = Matrix.asMatrix(shape = shape_2x2, data_2x2_valid)

        // then
        assertTrue(matrix.asShapedData().size == 2, "Expect a 2D array with 2 items ")
        assertTrue(matrix.asShapedData()[0].size == 2, "Expect a 2D array with 2 column items ")
    }

    @Test
    internal fun `given matrix creation when invalid data count then throw exception`() {
        assertThrows<IllegalArgumentException> {
            Matrix.asMatrix(shape = shape_2x2, data_2x2_invalid)
        }
    }

    @Test
    internal fun `test matrix creator property`() {
        val matrix = Matrix.zeros(Pair(2, 2))

        assertEquals(2,
            matrix.asShapedData().size,
            "Expect a matrix with 2 rows but was ${matrix.asShapedData().size} ")
        assertEquals(2,
            matrix.asShapedData()[0].size,
            "Expect a matrix with 2 columns but was ${matrix.asShapedData()[0].size} ")
        assertEquals(0.0,
            matrix.asShapedData()[0][0],
            "Expect a matrix with 2 columns but was ${matrix.asShapedData()[0][0]} ")
        assertEquals(0.0,
            matrix.asShapedData()[0][1],
            "Expect a matrix with 2 columns but was ${matrix.asShapedData()[0][1]} ")
        assertEquals(0.0,
            matrix.asShapedData()[1][0],
            "Expect a matrix with 2 columns but was ${matrix.asShapedData()[1][0]} ")
        assertEquals(0.0,
            matrix.asShapedData()[1][1],
            "Expect a matrix with 2 columns but was ${matrix.asShapedData()[1][1]} ")
    }


    @Test
    internal fun `test transform method for matrix`() {

        val matrix = Matrix.asMatrix(Pair(4, 4), arrayOf(
            1.0, 0.5, 0.0, 0.1,
            0.1, 0.6, 1.1, 1.0,
            1.1, 1.6, 0.1, 1.1,
            0.4, 0.3, 0.2, 0.1,
        ))

        // what we expect:
        /*
            1.0 0.1 1.1 0.4
            0.5 0.6 1.6 0.3
            0.0 1.1 0.1 0.2
            0.1 1.0 1.1 0.1

        */

        val transposed = matrix.transpose()

        assertEquals(1.0, transposed.get(0, 0), "expect 0.1 but was ${transposed.get(0, 0)}")
        assertEquals(0.1, transposed.get(1, 0), "expect 0.1 but was ${transposed.get(1, 0)}")
        assertEquals(1.1, transposed.get(2, 0), "expect 0.1 but was ${transposed.get(2, 0)}")
        assertEquals(0.4, transposed.get(3, 0), "expect 0.1 but was ${transposed.get(3, 0)}")

        assertEquals(0.5, transposed.get(0, 1), "expect 0.1 but was ${transposed.get(0, 1)}")
        assertEquals(0.6, transposed.get(1, 1), "expect 0.1 but was ${transposed.get(1, 1)}")
        assertEquals(1.6, transposed.get(2, 1), "expect 0.1 but was ${transposed.get(2, 1)}")
        assertEquals(0.3, transposed.get(3, 1), "expect 0.1 but was ${transposed.get(3, 1)}")

    }

    @Test
    internal fun `test the zeros factory method`() {
        val numberColumns = 3
        val matrix = Matrix.zeros(Pair(1, numberColumns)) // a 1x3 Matrix
        matrix.asRawArray().forEach { d ->
            assertTrue(d == 0.0, "expect a value of 0.0 but was $d")
        }
    }

    @Test
    internal fun `test the ones factory method`() {
        val numberColumns = 3
        val matrix = Matrix.ones(Pair(1, numberColumns)) // a 1x3 Matrix
        matrix.asRawArray().forEach { d ->
            assertEquals(1.0, d, "expect a value of 1.0 but was $d")
        }
    }

    @Test
    internal fun `test the random factory method`() {
        val numberColumns = 3
        val matrix = Matrix.random(Pair(1, numberColumns)) // a 1x3 Matrix
        matrix.asRawArray().forEach { d ->
            assertTrue(d > 0.0 && d < 1.0, "expect a value of 0.0 to 1.0 but was $d")
        }
    }


    @Test
    internal fun `given a MxN matrix when multiplied by scalar then expect a valid scalar multiplication result`() {
        // given
        val scalar = 3.0
        val matrix = Matrix.ones(Pair(3, 3), true)

        // when
        val scalarProduct = matrix.times(scalar)

        val asserted = scalarProduct.get(0, 0)

        assertEquals(3.0, asserted, "Expect as value of 3.0 but was $asserted")
    }

    @Test
    internal fun `given Ma when calling get(index, element) with MatrixElement-ROW then expect the row at passed index`() {
        // given
        val ma = Matrix.asMatrix(Pair(2, 3), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))

        // when
        val ma_row0 = ma.get(index = 0, MatrixElement.ROW )
        val ma_row1 = ma.get(index = 1, MatrixElement.ROW )

        // then
        assertEquals(listOf(1.0, 2.0, 3.0), ma_row0, "expect the list but was $ma_row0")
        assertEquals(listOf(4.0, 5.0, 6.0), ma_row1, "expect the list but was $ma_row1")
    }

    @Test
    internal fun `given Ma when calling get(index, element) with MatrixElement-COLUMN then expect the row at passed index`() {
        // given
        val ma = Matrix.asMatrix(Pair(2, 3), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))

        // when
        val ma_column0 = ma.get(index = 0, MatrixElement.COLUMN )
        val ma_column1 = ma.get(index = 1, MatrixElement.COLUMN )
        val ma_column2 = ma.get(index = 2, MatrixElement.COLUMN )

        // then
        assertEquals(listOf(1.0, 4.0), ma_column0, "expect the list but was $ma_column0")
        assertEquals(listOf(2.0, 5.0), ma_column1, "expect the list but was $ma_column1")
        assertEquals(listOf(3.0, 6.0), ma_column2, "expect the list but was $ma_column2")
    }

    @Test
    internal fun `given a MxN matrix when multiplied with similar MxN matrix then expect a valid MxN matrix result`() {
        // given
        val ma = Matrix.asMutableMatrix(Pair(2, 3), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
        val mb = Matrix.asMatrix(Pair(3, 4), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0))

        // when
        val mc = ma.times(mb)

        // c00 = a00 * b00 + a01 * b10 + a02 * b20
        // c00 = 1 * 1 + 2 * 5 + 3 * 9 => 1 + 10 + 27

        // c01 = a00 * b01 + a01 * b11 + a02 * b21
        // c01 = 1 * 2 + 2 * 6 + 3 * 10 => 44

        // c02 = a00 * b02 + a01 * b12 + a02 * b22
        // c02 = 1 * 3 + 2 * 7 + 3 * 11 = 50

        // then
//        assertEquals(1.0, mc.get(x=0, y=0), "Expect a value of C00[${mc[0][0]}] but was " )
        assertEquals(2, mc.rows, "expect a result of 2 rows but was ${mc.rows}")
        assertEquals(4, mc.columns, "expect a result of 4 columns but was ${mc.columns}")
        assertEquals(38.0, mc[0][0], "Expect a value of 38.0 but was C00[${mc[0][0]}]" )
        assertEquals(56.0, mc[0][3], "Expect a value of 56.0 but was C00[${mc[0][3]}]" )
        assertEquals(83.0, mc[1][0], "Expect a value of 83.0 but was C00[${mc[1][0]}]" )
        assertEquals(128.0, mc[1][3], "Expect a value of 128.0 but was C00[${mc[1][3]}]" )

//        val result = ma.times(mb)

        // ma = | a11 a12 a13 |
        //      | a21 a22 a23 |
        //  x
        // mb = | b11 b12 b13 b14 |
        //      | b21 b22 b23 b24 |
        //      | b31 b32 b33 b34 |

        //  =
        // mc = | c11 = a11 * b11 + a12 * b21 + a13 * b31  c12 = a11 * b12 + a12 * b22 + a13 * b32  c13 = a11 * b13 + a12 * b23 + a13 * b33  c14 = a11 * b14 + a12 * b24 + a13 * b34 |
        //      | c21 = a21 * b11 + a22 * b21 + a23 * b31  c22 = a21 * b12 + a22 * b22 + a23 * b32  c23 = a21 * b13 + a22 * b23 + a23 * b33  c24 = a21 * b14 + a22 * b24 + a23 * b34 |


//        ma.asRawArray().forEachIndexed { indexA, a ->
//            mb.asRawArray().forEachIndexed { indexB, b ->
//                println("indexA: $indexA a: $a")
//                println("indexB: $indexB b: $b")
//            }
//        }

//        val ma_row0 = ma.get(index = 0, MatrixElement.ROW )
//        val mb_col0 = mb.get(index = 0, MatrixElement.COLUMN)
//        val mb_col1 = mb.get(index = 1, MatrixElement.COLUMN)
//        val mb_col2 = mb.get(index = 2, MatrixElement.COLUMN)
//        val mb_col3 = mb.get(index = 2, MatrixElement.COLUMN)

//        val topRow = (0 until 4).map { i ->
//            val mb_col = mb.get(index = i, MatrixElement.COLUMN)
//            val c00 = ma_row0.zip(mb_col) { t:Double, r:Double ->
//                t*r
//            }.sum()
//            println("c00: $c00")
//        }

//        val resultShape = Pair(ma.rows, mb.columns)
//        val result = (0 until resultShape.first).map { r ->
//            val ma_row = ma.get(index = r, MatrixElement.ROW )
//            val topRow = (0 until resultShape.second).map { c ->
//                val mb_col = mb.get(index = c, MatrixElement.COLUMN)
//                val c00 = ma_row.zip(mb_col) { t:Double, r:Double ->
//                    t*r
//                }.sum()
//                println("c00: $c00")
//                c00
//            }
//            topRow
//        }.flatten()
//
//        println("result: $result")


//        val ma_row1 = ma.get(index = 1, MatrixElement.ROW)

//        val result = ma.times(mb) // ma_row0.times(mb_col0)

//        val maData = ma.asShapedData()
//        val mbData = mb.asShapedData()

//        val c11 = maData[0][0] * mbData[0][0] + maData[0][1] * mbData[1][0] + maData[0][2] * mbData[2][0] // 1, 2, 3
//        val c12 = maData[0][0] * mbData[0][1] + maData[0][1] * mbData[1][1] + maData[0][2] * mbData[2][1] // 1, 2, 3
//        val c13 = maData[0][0] * mbData[0][2] + maData[0][1] * mbData[1][2] + maData[0][2] * mbData[2][2] // 1, 2, 3
//        val c14 = maData[0][0] * mbData[0][3] + maData[0][1] * mbData[1][3] + maData[0][2] * mbData[2][3] // 1, 2, 3

        // c00 = a00 * b00 + a01 * b10 + a02 * b20
        // c00 = 1 * 1 + 2 * 5 + 3 * 9 => 1 + 10 + 27

        // c01 = a00 * b01 + a01 * b11 + a02 * b21
        // c01 = 1 * 2 + 2 * 6 + 3 * 10 => 44

        // c02 = a00 * b02 + a01 * b12 + a02 * b22
        // c02 = 1 * 3 + 2 * 7 + 3 * 11 = 50

//        println("c11: $c11")
//        println("c12: $c12")
//        println("c13: $c13")
//        println("c14: $c14")

//        val hiddenVector = ma.asRawArray().mapIndexed { row:Int, d: Double ->
//            val result = mbData.foldIndexed(0.0) { col: Int, acc: Double, value: Double ->
////                acc.plus( value * tempWeightVector[col] )
//
//                val index = col
//
//                //a11 = ma.asShapedData()[0][0]
//                val a11 = ma.asRawArray()[row]
//                println(a11)
//
//
//
//                0.0
//            }
//        }


//        val mc = Matrix.asMatrix(
//            Pair((ma as AbstractMatrix).shape.second, (mb as AbstractMatrix).shape.second ),
//            result
//        )
    }

    @Test
    internal fun `given a 4x4 matrix when times a 4x3 matrix then expect a 4x3 result matrix`() {
        // given
        val ma = Matrix.asMutableMatrix(Pair(4, 4), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0))
        val mb = Matrix.asMatrix(Pair(4, 3), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0))

        // when
        val mc = ma.times(mb)

        // then
        assertEquals(4, mc.rows, "expect a result of 4 rows but was ${mc.rows}")
        assertEquals(3, mc.columns, "expect a result of 3 columns but was ${mc.columns}")
    }

    @Test
    internal fun `given a 4x4 matrix when times a 3x4 matrix then expect a IllegalArgumentException to be thrown`() {
        // given
        val ma = Matrix.asMutableMatrix(Pair(4, 4), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0))
        val mb = Matrix.asMatrix(Pair(3, 4), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0))

        // then
        assertThrows<IllegalArgumentException> {
            // when invalid mcb size
            ma.times(mb)
        }
    }

    //    fun calculateHiddenLayer(inputResultVector: List<Double>): List<Double> {
//        // the manual way - without using matrices
////        val hiddenVector = (0 until hiddenCount).map { i:Int ->
////            var internalAcc = 0.0
////            val tempWeightVector = listOf(2.0, 3.0, 4.0)
////            inputResultVector.forEachIndexed { i:Int, value:Double ->
////                internalAcc += value * tempWeightVector[i]
////            }
////            internalAcc
////        }
//
//        // a better way using fold - but still not using matrices
////        val hiddenVector = (0 until hiddenCount).map { i:Int ->
////            val tempWeightVector = listOf(2.0, 3.0, 4.0)
////            val result = inputResultVector.foldIndexed(0.0) { j:Int, acc:Double, value:Double ->
////                acc.plus( value * tempWeightVector[j] )
////            }
////            result
////        }
//
//        return hiddenVector
//    }
}