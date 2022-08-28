package de.amirrocker.fantasticadventure.kotlindl

//import DATASET_FLIGHTSDELAYED
//import DATASET_SEAGAL
import DATASET_WOHNUNGSPREISE
import de.amirrocker.fantasticadventure.math.Matrix
import jetbrains.letsPlot.GGBunch
import jetbrains.letsPlot.Stat
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomArea
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.letsPlot
import krangl.DataFrame
import krangl.OR
import krangl.`=`
import krangl.count
import krangl.dataFrameOf
import krangl.gt
import krangl.le
import krangl.max
import krangl.mean
import krangl.min
import krangl.print
import krangl.range
import krangl.readCSV
import krangl.schema
import java.awt.Dimension
import java.awt.Graphics
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame

// minimal lets_plot solution
// https://github.com/JetBrains/lets-plot-kotlin/blob/master/README_DEV.md
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

/**
 * with krangl DataFrames, same as in Pandas, can be used in kotlin.
 * https://github.com/holgerbrandl/krangl
 *
 */
fun createManualDataframe() {

    val df: DataFrame =
        dataFrameOf("emergency", "location", "severity", "timetoArrival", "timeToFinish", "resolved", "sentToHospital")(
            "burn", "1.0000000, 1.0000000", "SEVERE", "14", "18", "no", "yes",
            "bone", "1.0000000, 1.0000000", "SEVERE", "5", "58", "yes", "no",
            "internal", "1.0000000, 1.0000000", "SEVERE", "55", "10", "yes", "yes",
        )

    df.print(colNames = false)

}

// read in csv and play around a bit...
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
}

// a number of const values
const val plotImageFilename = "plot.png"
const val plotBunchImageFilename = "plotBunch.png"
const val baseFolder = "./lets-plot-images"

// visualize in a first version
// as a single line plot
// later examples show how to define different plot types
fun plotLinePlot(csvFilepath: String) =
    saveAndVisualize(
        prepareData(
            fromCsvToDataFrame(csvFilepath)
        )
    )

// plot a custom area plot defined as lambda
fun plotAreaPlot(csvFilepath: String) =
    saveAndVisualize(
        prepareData(
            fromCsvToDataFrame(csvFilepath)
        )
    ) { data ->
        // we assume a pair for now
        // but later we need to use layers to setup
        // a single layer per dependent variable or column
        val xLabel = data.keys.first()
        val ylabel = data.keys.last()

        val plot = letsPlot(data) +
                geomArea { x = xLabel; y = ylabel; Stat.identity } + ggsize(500, 250)
        plot
    }

/**
 * plot a number of single plot layers into a bunch and display.
 * the default plot is a line plot with a scatter plot below.
 * use lambda to customize.
 */
fun plotCustomPlots(csvFilepath: String) =
    saveBunchAndVisualize(
        prepareData(
            fromCsvToDataFrame(csvFilepath)
        )
    ) { data ->
        // line plot

        // we assume a pair for now
        // but later we need to use layers to setup
        // a single layer per dependent variable or column
        val xLabel = data.keys.first()
        val ylabel = data.keys.last()

        val pA = letsPlot(data) +
                geomLine { x = xLabel; y = ylabel } + ggsize(500, 250)

        // scatter plot
        val pB = letsPlot(data) +
                geomPoint(data, stat = Stat.identity) { x = xLabel; y = ylabel; color = ylabel } + ggsize(500, 250)

        val pC = letsPlot(data) +
                geomBar(data, stat = Stat.identity) { x = xLabel; y = ylabel; color = ylabel } + ggsize(500, 250)

        val bunchOfPlots = GGBunch()
            .addPlot(pA, 0, 0)
            .addPlot(pB, 0, 260)
            .addPlot(pC, 0, 520)
        bunchOfPlots
    }


private fun saveBunchAndVisualize(
    data: Map<String, List<Any?>>,
    plotConfig: (Map<String, List<Any?>>) -> GGBunch? = { null },
) {

    // we assume a pair for now
    // but later we need to use layers to setup
    // a single layer per dependent variable or column
    val xLabel = data.keys.first()
    val ylabel = data.keys.last()

    val bunchOfPlots = plotConfig(data) ?: run {
        val pA = letsPlot(data) +
                geomLine { x = xLabel; y = ylabel } + ggsize(500, 250)

        // scatter plot
        val pB = letsPlot(data) +
                geomPoint(data) { x = xLabel; y = ylabel; color = ylabel } + ggsize(500, 250)

        val bunchOfPlots = GGBunch()
            .addPlot(pA, 0, 0)
            .addPlot(pB, 0, 260)
        bunchOfPlots
    }

    ggsave(bunchOfPlots, plotBunchImageFilename)

    visualize(File("$baseFolder/$plotBunchImageFilename"))
}

/**
 * If no lambda is used to setup a custom plot a simple line plot is used.
 *
 */
private fun saveAndVisualize(data: Map<String, List<Any?>>, plotConfig: (Map<String, List<Any?>>) -> Plot? = { null }) {

    // we assume a pair for now
    // but later we need to use layers to setup
    // a single layer per dependent variable or column
    val xLabel = data.keys.first()
    val ylabel = data.keys.last()

    val plot = plotConfig(data) ?: run {
        letsPlot(data) +
                geomLine { x = xLabel; y = ylabel; color = ylabel } + ggsize(500, 250)
    }
    ggsave(plot, plotImageFilename)
    visualize(File("$baseFolder/$plotImageFilename"))
}

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


// Krangl
// play around with krangl and check its basic features:
// https://holgerbrandl.github.io/data_science_with_kotlin/data_science_with_kotlin.html#18

fun summarizeWithFixedColumnNames() {

    val df = DataFrame.readCSV(DATASET_WOHNUNGSPREISE)
    val result = df.count("Quadratmeter", "Verkaufspreis")
    println(result)

    val summarized = df
        .groupBy("Verkaufspreis", "Quadratmeter")
        .select({ range("Quadratmeter", "Verkaufspreis") })
        .summarize(
            "Quadratmeter_Mean" `=` {
                it["Quadratmeter"].mean(removeNA = true)
            },
            "Verkaufspreis_Mean" `=` {
                it["Verkaufspreis"].mean(removeNA = true)
            }
        )
    println(summarized)
}

fun summarizeAndAppendMeanValues() {

    val df = DataFrame.readCSV(DATASET_WOHNUNGSPREISE)
//    val df = DataFrame.readCSV(DATASET_FLIGHTSDELAYED)
//    val df = DataFrame.readCSV(DATASET_SEAGAL)
    df.schema()

    val summarized = df
        .groupBy("Quadratmeter", "Verkaufspreis" )
        .select { range("Quadratmeter", "Verkaufspreis") }
        .summarize(
            "Quadratmeter_Mean" `=` { it["Quadratmeter"].mean(removeNA = true) },
            "Verkaufspreis_Mean" `=` { it["Verkaufspreis"].mean(removeNA = true) },
        )
        .filter { (it["Quadratmeter_Mean"] gt 20) OR (it["Verkaufspreis_Mean"] gt 30) }
        .sortedBy("Verkaufspreis_Mean")
    println(summarized)
}




