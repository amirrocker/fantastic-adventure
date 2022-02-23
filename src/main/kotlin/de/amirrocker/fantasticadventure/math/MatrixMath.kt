package de.amirrocker.fantasticadventure.math


//****************** GEOMETRY ****************************** //

sealed interface Geometry {

    companion object {
        // find the vector between these two points
        fun vectorBetween(from: Point, to: Point) =
            Vector3(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z
            )
    }
}

//****************** VECTOR ****************************** //

data class Point(
    val x: Float,
    val y: Float,
    val z: Float
) {
    // simple movement along single axis
    fun translateY(dy: Float) = Point(x, y.plus(dy), z)
    fun translateX(dx: Float) = Point(x.plus(dx), y, z)
    fun translateZ(dz: Float) = Point(x, y, z.plus(dz))

    // rotation around single axis
    // cos(x) - sin(x)
    // sin(x) + cos(x)
//    fun rotate(angle:Float) =
//        Matrix.asMatrix(Pair(2,2), arrayOf(cos(x), -sin(y), sin(x), cos(y)))

}

data class Circle(
    val center: Point,
    val radius: Float
) {
    fun scale(scale: Float) = Circle(center, scale.times(radius))
}

data class Sphere(
    val center: Point,
    val radius: Float
) : Geometry {}

data class Cylinder(
    val center: Point,
    val radius: Float,
    val height: Float
) {}

data class Ray(
    val point: Point,
    val vector: Vector3
) : Geometry {}

open class Vector3(
    open val x: Float,
    open val y: Float,
    open val z: Float
) : Geometry {
    open fun toFloatArray(): FloatArray = FloatArray(3).apply {
        this[0] = x
        this[1] = y
        this[2] = z
    }

    companion object {
        fun of(x: Float, y: Float, z: Float): Vector3 = Vector3(x, y, z)
    }
}

data class Vector4(
    override val x: Float,
    override val y: Float,
    override val z: Float,
    val w: Float,
) : Vector3(x, y, z) {
    override fun toFloatArray(): FloatArray = FloatArray(4).apply {
        this[0] = x
        this[1] = y
        this[2] = z
        this[3] = w
    }

    companion object {
        fun of(x: Float, y: Float, z: Float, w: Float): Vector3 = Vector4(x, y, z, w)
    }
}


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
 *
 * But not only do we need matrix we also different vector types :
 * Vector3
 *  - magnitude
 *  - normalize
 *
 * Vector4
 *
 *
 */

interface Matrix<T> {

    fun transpose(): Matrix<T>

    fun reshape(shape: Pair<Int, Int>): Matrix<T>

    fun asShapedData(): Array<List<T>>

    fun asRawArray(): Array<T>

    operator fun get(shape: Pair<Int, Int>): Matrix<T>

    operator fun get(x: Int, y: Int): T

    operator fun set(x: Int, y: Int, value: T): Matrix<T>

    companion object {
        fun <T> asMatrix(
            shape: Pair<Int, Int>,
            data: Array<T>
        ): Matrix<T> {
            return ImmutableMatrix(shape, data)
        }

        inline fun <reified T> zeros(shape: Pair<Int, Int>): Matrix<T> {
            val array = Array(shape.first * shape.second) { 0.0 as T }
            return ImmutableMatrix(shape, array)
        }
    }

}

/**
 * shape defines a Pair representing
 * shape.first = Rows
 * shape.second = Cols
 */
abstract class AbstractMatrix<T>(
    private val shape: Pair<Int, Int>,
    private val data: Array<T>
) : Matrix<T> {

    protected var internalDataStructure: Array<List<T>> = emptyArray()

    init {
        internalDataStructure = shapeIt()
    }

    private fun shapeIt(): Array<List<T>> {

        require(data.size % shape.first == 0) { "data size : ${data.size} must be divisable by shape row size : ${shape.first}" }
        require(shape.first * shape.second == data.size) { "first * second == data.size is not true: first: ${shape.first} * ${shape.second} == ${data.size}" }
        require(data.size % shape.second == 0) { "data size : ${data.size} must be divisable by shape column size : ${shape.second} and result in ${shape.first} but was ${data.size % shape.second}" }

//        val slices:Array<List<T>> = Array(shape.first) { Array<T>(shape.second) { 0.0 as T } }

        val slices: Array<List<T>> = Array(shape.first) { List<T>(shape.second) { 0.0 as T } }
        (0 until shape.first).forEach {
//            validateInput(data, shape)
            val steps = data.size / shape.first
            val from = steps * it
            val until = (from + steps) - 1
            slices[it] = data.slice((from..until))
        }
        println("Matrix rows: ${slices.size}")
        println("Matrix columns: ${slices[0].size}")

        return slices
    }

    override fun get(rows: Int, cols: Int): T {
        return internalDataStructure[rows][cols]
    }

    // TODO this needs some more thought!
    override fun get(shape: Pair<Int, Int>): AbstractMatrix<T> = this

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

    abstract override fun set(x: Int, y: Int, value: T): Matrix<T>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractMatrix<*>

        if (shape != other.shape) return false
//        if (!data.contentEquals(other.data)) return false
//        if (!internalDataStructure.contentEquals(other.internalDataStructure)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shape.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + internalDataStructure.contentHashCode()
        return result
    }
}

class ImmutableMatrix<T>(
    private val shape: Pair<Int, Int>,
    private val data: Array<T>
) : AbstractMatrix<T>(shape, data) {
    override fun set(x: Int, y: Int, value: T): Matrix<T> = error("Please use a Mutable Matrix to set values.")
}

class MutableMatrix<T>(
    private val shape: Pair<Int, Int>,
    data: Array<T>
) : AbstractMatrix<T>(shape, data) {

    override fun set(row: Int, col: Int, value: T): Matrix<T> {
        (this.internalDataStructure[row] as MutableList)[col] = value
        return Matrix.asMatrix(shape, asRawArray())
    }

}

//****************** VECTORS ****************************** //



