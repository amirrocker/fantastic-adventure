package de.amirrocker.fantasticadventure.scratchpad

import javafx.application.Application
import javafx.application.Platform
import javafx.embed.swing.SwingNode
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import jetbrains.datalore.plot.MonolithicCommon
import jetbrains.datalore.vis.swing.jfx.DefaultPlotPanelJfx
import jetbrains.letsPlot.geom.geomDensity
import jetbrains.letsPlot.geom.geomDensity2D
import jetbrains.letsPlot.geom.geomHistogram
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.intern.toSpec
import jetbrains.letsPlot.letsPlot
import krangl.DataFrame
import krangl.asDataFrame
import krangl.builder
import krangl.toMap
import org.apache.commons.math3.distribution.MultivariateNormalDistribution
import java.awt.Dimension
import javax.swing.JPanel

class LetsPlotScratchpad : Application() {

    /**
     * Layer is responsible for creating objects 'painted' on the 'canvas' containing the following elements:
     * - data -> the set of data specified either
     *         - once for all layers or
     *         - on a per layer basis
     *
     * One plot can contain multiple differen datasets - one per layer!
     *
     * Aesthetic mapping -> describes how variables in the dataset are mapped to the visual properties of
     * the layer, such as color, shape, size or position.
     * - mapping
     * With mappings, you can define how variables in dataset are mapped to the visual elements of the chart. Add the {x=< >; y=< >; ...} closure to geom, where:
     *
     * x: the dataframe column to map to the x axis.
     * y: the dataframe column to map to the y axis.
     * ...: other visual properties of the chart, such as color, shape, size, or position.
     * geom_point() {x = "cty"; y = "hwy"; color="cyl"}
     *
     * A typical code fragment that plots a chart looks like:
     *
     * * these are the packages that need to be imported:
     * import org.jetbrains.letsPlot.*
     * import org.jetbrains.letsPlot.geom.*
     * import org.jetbrains.letsPlot.stat.*
     */
    val random = java.util.Random()
    val df = mapOf(
        "x" to (-100 .. 100).toList(),
        "y" to (-100 .. 100).map { random.nextDouble(0.0, 1.0) }
    )
    val showPlot = letsPlot(data = df) {
        x = "x"
        y = "y"
    }
    val resultPlot = showPlot + geomPoint(size = 1)

    /**
     * Side stepping into the geom Reference.
     * Lets try out the density2D geoms
     *
     */
    val cov0 = arrayOf(
        doubleArrayOf(1.0, -.8),
        doubleArrayOf(-.8, 1.0),
    )

    val cov1 = arrayOf(
        doubleArrayOf(1.0, .8),
        doubleArrayOf(.8, 1.0),
    )

    val cov2 = arrayOf(
        doubleArrayOf(10.0, .1),
        doubleArrayOf(.1, .1),
    )

    val sampleSize = 400

    val means0 = doubleArrayOf(-2.0, 0.0)
    val means1 = doubleArrayOf(2.0, 0.0)
    val means2 = doubleArrayOf(0.0, 1.0)

    val xy0 = MultivariateNormalDistribution(means0, cov0).sample(sampleSize)
    val xy1 = MultivariateNormalDistribution(means1, cov1).sample(sampleSize)
    val xy2 = MultivariateNormalDistribution(means2, cov2).sample(sampleSize)

    val data = mapOf(
        "x" to (xy0.map { it[0] } + xy1.map { it[0] } + xy2.map { it[0] }).toList(),
        "y" to (xy0.map { it[1] } + xy1.map { it[1] } + xy2.map { it[1] }).toList()
    )

    val distributionPlot = letsPlot(data) {
        x = "x"
        y = "y"
    } /* + ggsize(400, 300) */ + geomDensity2D(color = "red") // geomPoint(color = "black", alpha = .9)

    /**
     * Lets look at one more:
     * geomHistogram()
     */
    val rnd = java.util.Random()
    val n = 200
    val histogram = mapOf(
        "cond" to List(n) { "A" } + List(n) { "B" },
        "rating" to List(n) { rnd.nextGaussian() } + List(n) { rnd.nextGaussian() * 1.5 + 1.5 }
    )
    // basic histogram
    val histogramPlot = letsPlot(histogram) {
        x = "rating"
    } + ggsize(400, 300) + geomHistogram(binWidth = 0.5)

    val histogramPlotWithDensityCurve = letsPlot(histogram) {
        x = "rating"
    } + geomHistogram(binWidth = 0.5, color = "black", fill = "white") { y  = "..density.."} + geomDensity(alpha = 0.2, fill=0xFF6666)



    /*
        the first plot on pane.
     */
    private val pointsPlot = {
        val rnd = java.util.Random()
        val data = mapOf(
            "x" to (-15..14).toList(),
            "y" to (0..29).map { rnd.nextDouble(1.0, 5.0) },
            "sugar" to (150..179).toList()
        )

        val plot = letsPlot(
            data
        ) {
            x = "x"
            y = "y"
        } + geomPoint(size = 2)
        plot
    }

    private val gaussianStdDistributionPlot = {
        val random = java.util.Random()
        val n = 200
        val data = mapOf("x" to List(n) { random.nextGaussian() } )
        val plot = letsPlot(data = data) + geomDensity(
            color = "dark-green",
            fill = "red",
            alpha = .8,
            size = 1.0
        ) { x = "x"}
        plot
    }

    private val prepareForJfxLetsPlotPanel = { plot: Plot ->
        val rawSpec = plot.toSpec()
        val processedSpec = MonolithicCommon.processRawSpecs(rawSpec, frontendOnly = false)
        processedSpec
    }

    override fun start(primaryStage: Stage?) {
        primaryStage?.title = "Lets plot with JavaFx"

        val pointsPlotPanel = createPlotPanel {
            prepareForJfxLetsPlotPanel(pointsPlot())
        }

        val plotPanel = createPlotPanel {
            prepareForJfxLetsPlotPanel(gaussianStdDistributionPlot())
        }

        val histogramPanel = createPlotPanel {
            prepareForJfxLetsPlotPanel(gaussianStdDistributionPlot())
        }

        val resultPlotPanel = createPlotPanel {
            prepareForJfxLetsPlotPanel(resultPlot)
        }

        val distributionPlotPanel = createPlotPanel {
            prepareForJfxLetsPlotPanel(distributionPlot)
        }

        val histogramPlotPanel = createPlotPanel {
//            prepareForJfxLetsPlotPanel(histogramPlot)
            prepareForJfxLetsPlotPanel(histogramPlotWithDensityCurve)
        }

        val root = FlowPane()

        root.padding = Insets(15.0, 12.0, 14.0, 12.0)
        root.style = "-fx-backgroud-color: #cccccc;"
        root.vgap = 10.0
        root.hgap = 10.0

        root.prefWrapLength = 850.0

//        val button = Button("current")
//        button.setPrefSize(200.0, 20.0)
//        button.setOnAction {
//            println("clicked")
//        }
//        root.children.add(button)

        val swingNode = SwingNode()
        swingNode.content = plotPanel
        swingNode.content.preferredSize = Dimension(400, 300)
        root.children.add(swingNode)

        val histogramNode = SwingNode()
        histogramNode.content = histogramPanel
        histogramNode.content.preferredSize = Dimension(400, 300)
        root.children.add(histogramNode)

        val pointsPlotNode = SwingNode()
        pointsPlotNode.content = pointsPlotPanel
        pointsPlotNode.content.preferredSize = Dimension(400, 300)
        root.children.add(pointsPlotNode)

        val resultPlotNode = SwingNode()
        resultPlotNode.content = resultPlotPanel
        resultPlotNode.content.preferredSize = Dimension(400, 300)
        root.children.add(resultPlotNode)

        val distributionPlotNode = SwingNode()
        distributionPlotNode.content = distributionPlotPanel
        distributionPlotNode.content.preferredSize = Dimension(400, 300)
        root.children.add(distributionPlotNode)

        val histogramPlotNode = SwingNode()
        histogramPlotNode.content = histogramPlotPanel
        histogramPlotNode.content.preferredSize = Dimension(400, 300)
        root.children.add(histogramPlotNode)

        primaryStage?.scene = Scene(root, 1024.0, 800.0)
        primaryStage?.show()
    }



    companion object {
        fun createPlotPanel(plotFunc: () -> MutableMap<String, Any>) : JPanel {
            Platform.setImplicitExit(true)

            return DefaultPlotPanelJfx(
                processedSpec = plotFunc(), // processedSpec,
                preserveAspectRatio = false,
                preferredSizeFromPlot = false,
                repaintDelay = 10, // 10 ms
            ) { messages ->
                for(message in messages) {
                    println("message: $message")
                }
            }
        }

//         backupo
//        fun createPlotPanel(plotFunc: () -> MutableMap<String, Any>) : JPanel {
//
//            Platform.setImplicitExit(true)
//
//            val random = java.util.Random()
//
//            val n = 200
//
//
//
//            val data = mapOf("x" to List(n) { random.nextGaussian() } )
//
//            val plot = letsPlot(data = data) + geomDensity(
//                color = "dark-green",
//                fill = "red",
//                alpha = .8,
//                size = 1.0
//            ) { x = "x"}
//
//            val rawSpec = plot.toSpec()
//            val processedSpec = MonolithicCommon.processRawSpecs(rawSpec, frontendOnly = false)
//
//            return DefaultPlotPanelJfx(
//                processedSpec = processedSpec,
//                preserveAspectRatio = false,
//                preferredSizeFromPlot = false,
//                repaintDelay = 10, // 10 ms
//            ) { messages ->
//                for(message in messages) {
//                    println("message: $message")
//                }
//            }
//        }

    }

//    val xs = listOf(0, 0.5, 1, 2)
//    val ys = listOf(0, 0.25, 1, 4)
//    val data = mapOf<String, Any>("x" to xs, "y" to ys)
//
//    val fig = letsPlot(data) + geomPoint(
//        color = "dark-green",
//        size = 4.0
//    ) { x = "x"; y = "y" }
}

