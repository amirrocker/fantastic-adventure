package de.amirrocker.fantasticadventure.math

import kotlin.math.sqrt
import kotlin.random.Random

//****************** MATRIX ****************************** //
/**
 * what I want:
 * - create a matrix same as in numpy and/or pandas:
 * - np.full([shape:Union], scalar ) - create a single matrix with shape and single scalar value
 * - np.array([shape:Union])
 * - np.zeros([shape:Union])
 * - np.ones([shape:Union])
 *
 * Also there is already an existing shot at github at this topic.
 * https://github.com/yinpeng/kotlin-matrix
 * and
 * https://github.com/Kotlin/multik <-- preferred
 *
 * But not only do we need matrix we also different vector types :
 * Vector3
 *  - magnitude -> the length of the vector
 *
 *  - normalize
 *
 * Vector4
 *
 *
 */

class InvalidShapeException(cause:String) : Exception(cause)

enum class MatrixElement {
    ROW,
    COLUMN
}

interface Matrix<T : Number> {

    val rows: Int

    val columns: Int

    fun transpose(): Matrix<T>

    fun reshape(shape: Pair<Int, Int>): Matrix<T>

    fun asShapedData(): Array<List<T>>

    fun asRawArray(): Array<T>

    operator fun get(shape: Pair<Int, Int>): Matrix<T>

    operator fun get(row: Int, col: Int): T

    operator fun get(index: Int, element: MatrixElement = MatrixElement.ROW): List<T>

    operator fun set(row: Int, col: Int, value: T): Matrix<T>

    operator fun times(a: Double): Matrix<Double>

    operator fun times(a: Float): Matrix<Float>

    operator fun times(mb: Matrix<T>): Matrix<Double>

    companion object {
        fun <T : Number> asMatrix(
            shape: Pair<Int, Int>,
            data: Array<T>,
        ): Matrix<T> {
            return ImmutableMatrix(shape, data)
        }

        fun <T : Number> asMutableMatrix(
            shape: Pair<Int, Int>,
            data: Array<T>,
        ): Matrix<T> {
            return MutableMatrix(shape, data)
        }

        fun zeros(shape: Pair<Int, Int>, makeMutable: Boolean = false): Matrix<Double> =
            if (!makeMutable) {
                val array = Array(shape.first * shape.second) { 0.0 }
                ImmutableMatrix(shape, array)
            } else {
                val array = Array(shape.first * shape.second) { 0.0 }
                MutableMatrix(shape, array)
            }


        fun ones(shape: Pair<Int, Int>, makeMutable: Boolean = false): Matrix<Double> =
            if (!makeMutable) {
                val array = Array(shape.first * shape.second) { 1.0 }
                ImmutableMatrix(shape, array)
            } else {
                val array = Array(shape.first * shape.second) { 1.0 }
                MutableMatrix(shape, array)
            }

        fun random(shape: Pair<Int, Int>, makeMutable: Boolean = false): Matrix<Double> =
            if (!makeMutable) {
                val array = Array(shape.first * shape.second) { Random.nextDouble() }
                ImmutableMatrix(shape, array)
            } else {
                val array = Array(shape.first * shape.second) { Random.nextDouble() }
                MutableMatrix(shape, array)
            }
    }
}

/**
 * shape defines a Pair representing
 * shape.first = Rows
 * shape.second = Cols
 */
abstract class AbstractMatrix<T : Number>(
    val shape: Pair<Int, Int>,
    val data: Array<T>,
) : Matrix<T> {

    protected var internalDataStructure: Array<List<T>> = emptyArray()

    init {
        internalDataStructure = shapeIt()
    }

    private fun shapeIt(): Array<List<T>> {

        require(data.size % shape.first == 0) { "data size : ${data.size} must be divisable by shape row size : ${shape.first}" }
        require(shape.first * shape.second == data.size) { "first * second == data.size is not true: first: ${shape.first} * ${shape.second} == ${data.size}" }
        require(data.size % shape.second == 0) { "data size : ${data.size} must be divisable by shape column size : ${shape.second} and result in ${shape.first} but was ${data.size % shape.second}" }

        val slices: Array<List<T>> = Array(shape.first) { List<T>(shape.second) { 0.0 as T } }
        (0 until shape.first).forEach {
            val steps = data.size / shape.first
            val from = steps * it
            val until = (from + steps) - 1
            slices[it] = data.slice((from..until))
        }
        println("Matrix rows: ${slices.size}")
        println("Matrix columns: ${slices[0].size}")

        return slices
    }

    override fun get(row: Int, col: Int): T {
        return internalDataStructure[row][col]
    }

    // TODO this needs some more thought!
    override fun get(shape: Pair<Int, Int>): AbstractMatrix<T> = this

    override fun get(index: Int, element: MatrixElement): List<T> = when (element) {
        MatrixElement.ROW -> {
            this.asShapedData()[index]
        }
        MatrixElement.COLUMN -> {
            val result = this.internalDataStructure.mapIndexed { listIndex, listItem ->
//                println("listIndex: $listIndex with list: $listItem at column: ${listItem[index]}")
                listItem[index]
            }
            result
        }
    }


    override fun asShapedData(): Array<List<T>> = this.internalDataStructure

    override fun asRawArray(): Array<T> = data

    override fun transpose(): Matrix<T> {
        // reverse pair
        val rp = Pair(shape.second, shape.first)
        val copy = Matrix.asMatrix(rp, this.data)
        return copy
    }

    override fun reshape(shape: Pair<Int, Int>): Matrix<T> {
        val copy = Matrix.asMatrix(shape, this.data)
        return copy
    }

    abstract override fun set(row: Int, col: Int, value: T): Matrix<T>

    inline fun <reified T : Number> zeros(shape: Pair<Int, Int>): Matrix<T> {
        val array = Array(shape.first * shape.second) { 0.0 as T }
        return ImmutableMatrix(shape, array)
    }

    inline fun <reified T : Number> ones(shape: Pair<Int, Int>): Matrix<T> {
        val array: Array<T> = Array(shape.first * shape.second) {
            1.0 as T
        }
        return ImmutableMatrix(shape, array)
    }

    inline fun <reified T : Number> random(shape: Pair<Int, Int>): Matrix<T> {
        val array = Array(shape.first * shape.second) { Random.nextDouble(0.0, 1.0) as T }
        return ImmutableMatrix(shape, array)
    }

}

class ImmutableMatrix<T : Number>(
    shape: Pair<Int, Int>,
    data: Array<T>,
) : AbstractMatrix<T>(shape, data) {
    override fun set(row: Int, col: Int, value: T): Matrix<T> = error("Please use a Mutable Matrix to set values.")

    override fun times(a: Double): Matrix<Double> = error("Please use a Mutable Matrix to set values.")

    override fun times(a: Float): Matrix<Float> = error("Please use a Mutable Matrix to set values.")

    override fun times(mb: Matrix<T>): Matrix<Double> = error("Please use a Mutable Matrix to set values.")

//    inline fun <reified T : Number> zeros(shape: Pair<Int, Int>): Matrix<T> {
//        val array = Array(shape.first * shape.second) { 0.0 as T }
//        return ImmutableMatrix(shape, array)
//    }
//
//    inline fun <reified T : Number> ones(shape: Pair<Int, Int>): Matrix<T> {
//        val array: Array<T> = Array(shape.first * shape.second) {
//            1.0 as T
//        }
//        return ImmutableMatrix(shape, array)
//    }
//
//    inline fun <reified T : Number> random(shape: Pair<Int, Int>): Matrix<T> {
//        val array = Array(shape.first * shape.second) { Random.nextDouble(0.0, 1.0) as T }
//        return ImmutableMatrix(shape, array)
//    }

    override val rows: Int
        get() = shape.first

    override val columns: Int
        get() = shape.second
}

class MutableMatrix<T : Number>(
    shape: Pair<Int, Int>,
    data: Array<T>,
) : AbstractMatrix<T>(shape, data) {

    override fun set(row: Int, col: Int, value: T): Matrix<T> {
        (this.internalDataStructure[row] as MutableList)[col] = value
        return Matrix.asMatrix(shape, asRawArray())
    }

    override fun times(a: Double): Matrix<Double> =
        Matrix.asMatrix(
            shape,
            data.map { d: T -> a.times(d.toDouble()) }.toTypedArray()
        )

    override fun times(a: Float): Matrix<Float> =
        Matrix.asMatrix(
            shape,
            data.map { d: T -> a.times(d.toFloat()) }.toTypedArray()
        )

    fun validateAndReturn(init: Matrix<T>.()->Matrix<Double>) = init()

    // TODO clean up the chaotic use of T / Double - I need to read up on type erasure, variance and covariance again!
    @Throws(IllegalArgumentException::class)
    override fun times(mb: Matrix<T>): Matrix<Double> =
        validateAndReturn {
            if(mb.rows != this.columns) {
                throw IllegalArgumentException("ma columns must match mb rows to be able to calculate a valid dot product.")
            }
            Matrix.asMutableMatrix(
                Pair(this.rows, mb.columns),
                internalDotProduct(mb).toTypedArray()
            )
        }

    override val rows: Int
        get() = shape.first

    override val columns: Int
        get() = shape.second

    private fun internalDotProduct(mb: Matrix<T>): List<Double> =
        (0 until this.rows).map { r ->
            val ma_row = this.get(index = r, MatrixElement.ROW)
            (0 until mb.columns).map { c ->
                val mb_col = mb.get(index = c, MatrixElement.COLUMN)
                ma_row.zip(mb_col) { t, r ->
                    t.toDouble() * r.toDouble()
                }.sum()
            }
        }.flatten()
}