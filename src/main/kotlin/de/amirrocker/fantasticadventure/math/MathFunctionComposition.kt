package de.amirrocker.fantasticadventure.math

val plus3 = { x: Int -> x.times(x) }

val creator =
    { fa: FloatArray, shape: Pair<Int, Int> -> Matrix.asMatrix(Pair(shape.first, shape.second), fa.toTypedArray()) }

fun <T> transform(matrix: Matrix<Float>, fn: (m: Matrix<Float>) -> Matrix<Float>) = fn(matrix)

val command = { vector: FloatArray, ma: Matrix<Float>, fn: (vector: FloatArray, ma: Matrix<Float>) -> Matrix<Float> ->
    fn(
        vector,
        ma
    )
}

/**
 * in this function a regular column vector is multiplied with a homogenous! matrix.
 * shape of the vector is checked in this function.
 *
 * cv = {2.0f, 3.0f, 1.0f, 1.0f}
 *
 * ma = {
 *  1.0f, 0.0f, 0.0f, 0.0f,
 *  0.0f, 1.0f, 0.0f, 0.0f,
 *  0.0f, 0.0f, 1.0f, 0.0f,
 *  0.0f, 0.0f, 0.0f, 1.0f,
 * }
 * ma is a simple identity matrix.
 *
 */
//val multiplyCommand = { columnVector:FloatArray, ma:Matrix<Float> -> Matrix.asMatrix() }

/**
 * Vector math:
 * we need some basic vector functions that work on two vectors.
 * starting with vector skalar multiplication:
 */
val multiplySkalarWithColumnVector4 = { skalar: Float, columnVector: Vector4 ->
    Vector4(
        columnVector.x.times(skalar),
        columnVector.y.times(skalar),
        columnVector.z.times(skalar),
        columnVector.w.times(skalar),
    )
}

val multiplySkalarWithColumnVector3 = { skalar: Float, columnVector: Vector3 ->

    if (columnVector is Vector4) {
        log("Vector4 was received where Vector3 was expected - ignoring w")
    }

    Vector3(
        columnVector.x.times(skalar),
        columnVector.y.times(skalar),
        columnVector.z.times(skalar)
    )
}

val divideColumnVector4BySkalar = { skalar: Float, columnVector: Vector4 ->
    Vector4(
        columnVector.x.times(skalar),
        columnVector.y.times(skalar),
        columnVector.z.times(skalar),
        columnVector.w.times(skalar),
    )
}

val divideColumnVector3BySkalar = { skalar: Float, columnVector: Vector3 ->
    Vector3(
        columnVector.x.div(skalar),
        columnVector.y.div(skalar),
        columnVector.z.div(skalar),
    )
}

/**
 * addition and subtraction of 2 vectors
 */
val addVector = { va: Vector3, vb: Vector3 ->
    Vector3.of(
        va.x + vb.x,
        va.y + vb.y,
        va.z + vb.z,
    )
}


val subtractVector = { va: Vector3, vb: Vector3 ->
    Vector3.of(
        va.x - vb.x,
        va.y - vb.y,
        va.z - vb.z,
    )
}

/**
 * dot- aka skalar-product of two vectors
 * returns a float skalar
 *
 * Note:
 * skalar product of two orthogonal, linear independent vectors is zero
 * skalar product of two parallel vectors equals the product of its length or value
 * skalar product of two non-parallel vectors equals the negative product of its length or value
 *
 * Note: calculating the skalar product, the angle between the two vectors should be considered as follows
 * angle of 0° -> 1
 * angle of 90° -> 0
 * angle of 180° -> -1
 *
 */
val dotProduct = { va: Vector3, vb: Vector3 -> va.x * vb.x + va.y * vb.y + va.z * vb.z }

/**
 *
 */
val magnitude = { va: Vector3 -> va.x * va.x + va.y * va.y + va.z * va.z }


private fun log(msg: String) {
    println(msg)
}