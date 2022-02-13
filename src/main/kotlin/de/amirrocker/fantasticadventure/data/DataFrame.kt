package de.amirrocker.fantasticadventure.data

typealias DataFrameRow = Map<String, Any?>



abstract class DataCol(
    val name:String
) {

    abstract fun values():Array<*>

    val hasNulls by lazy { values().any { it == null } }

    abstract val length: Int

    operator fun get(index:Int) = values()[index]
}

class DoubleCol(
    name:String,
    val values: Array<Double?>
) : DataCol(name) {

    constructor(name:String, values: List<Double?>) : this(name, values.toTypedArray())

    @Suppress("UNCHECKED_CAST")
    constructor(name:String, values: DoubleArray) : this(name, values.toTypedArray() as Array<Double?>)

    override fun values(): Array<Double?>  = values

    override val length: Int = values.size

}


interface DataFrame {

    /** @return Number of rows in dataframe */
    val numberRows:Int

    /** @return Number of colums in dataframe */
    val numberColumns:Int

    /** @return Ordered List of Columnnames of Dataframe */
    val columnNames:List<String>

    val columns: List<DataCol>



}


