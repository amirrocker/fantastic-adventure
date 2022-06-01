package de.amirrocker.fantasticadventure.kotlindl

import de.amirrocker.fantasticadventure.math.Matrix
import jetbrains.letsPlot.GGBunch
import jetbrains.letsPlot.Stat
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.letsPlot
import krangl.DataFrame
import krangl.dataFrameOf
import krangl.gt
import krangl.le
import krangl.max
import krangl.min
import krangl.print
import krangl.readCSV
import krangl.schema
import java.awt.Dimension
import java.awt.Graphics
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame

fun createManualDataframe() {

    val df: DataFrame =
        dataFrameOf("emergency", "location", "severity", "timetoArrival", "timeToFinish", "resolved", "sentToHospital")(
            "burn", "1.0000000, 1.0000000", "SEVERE", "14", "18", "no", "yes",
            "bone", "1.0000000, 1.0000000", "SEVERE", "5", "58", "yes", "no",
            "internal", "1.0000000, 1.0000000", "SEVERE", "55", "10", "yes", "yes",
        )

    df.print(colNames = false)

}

fun readInCsv() {
    val df: DataFrame = DataFrame.readCSV("src/main/resources/datasets/wohnungspreise.csv")

    df.print(colNames = false)

    val columnQM = df["Quadratmeter"]
    val columnVP = df["Verkaufspreis"]

    val biggestApartment = columnQM.max()
    val smallestApartment = columnQM.min()

    println(biggestApartment)
    println(smallestApartment)

    columnQM.values().toList()

    df.schema()

    // similar to column
    val selected = df.select("Quadratmeter")

    println("selected: $selected")

    // filter
    val result = df.filter { it["Quadratmeter"] gt 90 }
    println(result)
    val result11 = df.filter { it["Quadratmeter"] le 11 }
    println(result11)

//    println(columnQM.length)
//    println(columnVP.length)
}

fun letsPlotALinePlot(csvFilepath: String) {
//    val df: DataFrame = DataFrame.readCSV("src/main/resources/datasets/wohnungspreise.csv")

//    val df: DataFrame = fromCsvToDataFrame( csvFilepath /*"src/main/resources/datasets/wohnungspreise.csv"*/)

//    val quadratmeter = df["Quadratmeter"].values().toList()
//    val verkaufspreis = df["Verkaufspreis"].values().toList()
//    println(quadratmeter)
//    println(verkaufspreis)
//
//    val data = mapOf(
//        "Quadratmeter" to quadratmeter,
//        "Verkaufspreis" to verkaufspreis
//    )
    //val data =

    // line plot
//    val p = letsPlot(data) +
//            geomLine { x = "Quadratmeter"; y = "Verkaufspreis" } + ggsize(500, 250)

    // scatter plot
//    val p = letsPlot(data) +
//            geomPoint(data) { x = "Quadratmeter"; y="Verkaufspreis"; color="Verkaufspreis" } + ggsize(500, 250)

//    val p = createPointPlot(data)

//    ggsave( createPointPlot(data), plotImageFilename /*"Wohnungspreise.png"*/)
//
////    visualize(File("./lets-plot-images/Wohnungspreise.png"))
//    visualize(File("$baseFolder/$plotImageFilename"))
    saveAndVisualize(
        prepareData(
            fromCsvToDataFrame(csvFilepath)
        )
    )
}

val plotImageFilename = "Wohnungspreise.png"
val plotBunchImageFilename = "Wohnungspreise.png"
val baseFolder = "./lets-plot-images"

fun saveAndVisualize(data: Map<String, List<Any?>>) {
    ggsave(createPointPlot(data), plotImageFilename)
    visualize(File("$baseFolder/$plotImageFilename"))
}

fun saveBunchAndVisualize(data: Map<String, List<Any?>>, plotConfig:(Map<String, List<Any?>>)->GGBunch? = { null }) {

    val bunchOfPlots = plotConfig(data) ?: run {
        val pA = letsPlot(data) +
                geomLine { x = "Quadratmeter"; y = "Verkaufspreis" } + ggsize(500, 250)

        // scatter plot
        val pB = letsPlot(data) +
                geomPoint(data) { x = "Quadratmeter"; y = "Verkaufspreis"; color = "Verkaufspreis" } + ggsize(500, 250)


        val bunchOfPlots = GGBunch()
            .addPlot(pA, 0, 0)
            .addPlot(pB, 0, 260)
        bunchOfPlots
    }

    ggsave(bunchOfPlots, plotBunchImageFilename)

    visualize(File("$baseFolder/$plotBunchImageFilename"))
}


fun createPointPlot(data: Map<String, List<Any?>>): Plot =
    letsPlot(data) +
            geomPoint(data) { x = "Quadratmeter"; y = "Verkaufspreis"; color = "Verkaufspreis" } + ggsize(500, 250)


fun prepareData(df: DataFrame): Map<String, List<Any?>> =
    df.cols.map {
        println("colum header: ${it.name}")
        it.name
    }.map {
        it to df[it].values().toList()
    }.toMap()

fun fromCsvToDataFrame(filePath: String): DataFrame = DataFrame.readCSV(filePath)

fun runTheBunch(csvFilepath: String) {

    saveBunchAndVisualize(
        prepareData(
            fromCsvToDataFrame(csvFilepath)
        )
    ) { data ->
       // line plot
        val pA = letsPlot(data) +
                geomLine { x = "Quadratmeter"; y = "Verkaufspreis" } + ggsize(500, 250)

        // scatter plot
        val pB = letsPlot(data) +
                geomPoint(data, stat = Stat.identity) { x = "Quadratmeter"; y = "Verkaufspreis"; color = "Verkaufspreis" } + ggsize(500, 250)

        val pC = letsPlot(data) +
                geomBar(data, stat = Stat.identity) { x = "Quadratmeter"; y = "Verkaufspreis"; color = "Verkaufspreis" } + ggsize(500, 250)

        val bunchOfPlots = GGBunch()
            .addPlot(pA, 0, 0)
            .addPlot(pB, 0, 260)
            .addPlot(pC, 0, 520)
        bunchOfPlots
    }
}


fun minimal() {
    // THROWS ERROR - NO BACKEND !
//    val rand = java.util.Random()
//    val n = 200
//    val data = mapOf<String, Any>(
//        "x" to List(n) { rand.nextGaussian() }
//    )
//    val p = letsPlot(data) +
//            geomDensity(
//                color = "dark-green",
//                fill = "green",
//                alpha = .3,
//                size = 2.0
//            ) { x = "x" }
//    p.show()

    val xs = listOf(0, 0.5, 1, 2)
    val ys = listOf(0, 0.25, 1, 4)
    val data = mapOf<String, Any>("x" to xs, "y" to ys)

    val fig = letsPlot(data) + geomPoint(
        color = "dark-green",
        size = 4.0
    ) { x = "x"; y = "y" }

    // this works since we need not draw on Context, (which is missing?) but instead draw into file.
    ggsave(fig, "plot.png")
}

private fun visualize(
    imageFile: File,
) {

    val frame = JFrame("DetectedObjects")
    @Suppress("UNCHECKED_CAST")
    frame.contentPane.add(JPanelSimpleImage(imageFile))
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isResizable = true
}

class JPanelSimpleImage(
    val image: File,
) : javax.swing.JPanel() {

    private val bufferedImage = ImageIO.read(image)

    override fun paint(graphics: Graphics) {
        super.paint(graphics)
        graphics.drawImage(bufferedImage, 0, 0, null)
    }

    override fun getPreferredSize(): Dimension = Dimension(bufferedImage.width, bufferedImage.height)
    override fun getMinimumSize(): Dimension = Dimension(bufferedImage.width, bufferedImage.height)
}


fun runSomeMatrices() {
    val data_3x1_valid = arrayOf(1.0, 2.0, 3.0)
    val matrix = Matrix.asMatrix(Pair(3, 1), data_3x1_valid)

    println("matrix: $matrix")
    println("matrix: ${matrix.asRawArray().size}")
    matrix.asRawArray().forEach {
        println("matrix: $it")
    }

    val rank_0_tensor_56 = Rank_0_Tensor(56.0)
    val rank_0_tensor_516 = Rank_0_Tensor(516.0)

    println("rank_0_tensor_516.asRawArray().first(): ${rank_0_tensor_516.asRawArray().first()}")

}

fun Rank_0_Tensor(value: Double) = Matrix.asMatrix(Pair(1, 1), doubleArrayOf(value).toTypedArray())






