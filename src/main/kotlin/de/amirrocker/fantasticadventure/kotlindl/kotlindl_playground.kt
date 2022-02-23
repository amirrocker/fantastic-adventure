package de.amirrocker.fantasticadventure.kotlindl

import org.jetbrains.kotlinx.dl.api.inference.loaders.ONNXModelHub
import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.api.inference.onnx.OnnxInferenceModel
import org.jetbrains.kotlinx.dl.dataset.mnist
import java.awt.*
import java.io.File
import java.net.URISyntaxException
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.abs

fun testKotlinDL() {

    // mnist dataset
    val (train, test) = mnist()

    val modelHub = ONNXModelHub(cacheDirectory = File("cache/pretrainedModels"))

    val modelType = ONNXModels.CV.Lenet

    val model = modelHub.loadModel(modelType)

    model.use {
        val prediction = it.predict(train.getX(0))
        println("predicted label: $prediction")
        println("correct label: ${train.getY(0)}")
    }
}

fun loadCustomOnnxModel() {
    val (train, test) = mnist()
    OnnxInferenceModel.load("model/pretrained_model.onnx").use {
        val prediction = it.predict(train.getX(0))
        println("predicted label: $prediction")
        println("correct label: ${train.getY(0)}")
    }
}


fun detectObjectsInStillImage(imageFileName:String = "images/image2.jpg") {

    val image = getFileFromResource(imageFileName)

    val modelHub = ONNXModelHub(cacheDirectory = File("cache/pretrainedModels"))
    val model = modelHub.loadPretrainedModel(ONNXModels.ObjectDetection.SSD)

    model.use { detectionModel ->

        val detectedObjects = detectionModel.detectObjects(
            imageFile = image,
            topK = 40
        )
        detectedObjects.forEach {
            println("found: ${it.classLabel} with ${it.probability} probility: ")
        }
        visualize(image, detectedObjects)
    }

}
@Throws(URISyntaxException::class)
fun getFileFromResource(fileName:String):File {
    val classLoader:ClassLoader = object {}.javaClass.classLoader
    val resource:URL? = classLoader.getResource(fileName)
    return resource?.let {
        File(resource.toURI())
    } ?: run { throw IllegalArgumentException("File not found! $fileName") }
}

private fun visualize(
    imageFile:File,
    detectedObjects:List<DetectedObject>
) {

    val frame = JFrame("DetectedObjects")
    @Suppress("UNCHECKED_CAST")
    frame.contentPane.add(JPanel(imageFile, detectedObjects))
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isResizable = true
}

class JPanel(
    val image: File,
    private val detectedObjects: List<DetectedObject>
) : JPanel() {

    private val bufferedImage = ImageIO.read(image)

    override fun paint(graphics: Graphics) {
        super.paint(graphics)

        graphics.drawImage(bufferedImage, 0, 0, null)

        detectedObjects.forEach {

            println("detectedObject: $it")

            val top = it.yMin * bufferedImage.height
            val bottom = it.yMax * bufferedImage.height
            val left = it.xMin * bufferedImage.width
            val right = it.xMax * bufferedImage.width
            if(abs(top-bottom) > 300 || abs(left - right) > 300 ) return@forEach

            graphics.color = Color.ORANGE
            graphics.font = Font("Courier New", 1, 16)

            when(it.classLabel) {
                PERSON -> graphics.color = Color.CYAN
                DOG -> graphics.color = Color.GREEN
                CAR -> graphics.color = Color.BLUE
                TRAFFIC_LIGHT -> graphics.color = Color.MAGENTA
                BICYCLE -> graphics.color = Color.YELLOW
                CLOCK -> graphics.color = Color.WHITE
                BUS -> graphics.color = Color.PINK
                BENCH -> graphics.color = Color.ORANGE
                HANDBAG -> graphics.color = Color.DARK_GRAY
                BACKPACK -> graphics.color = Color.LIGHT_GRAY
                else -> graphics.color = Color.RED
            }

            (graphics as Graphics2D).stroke = BasicStroke(6f)
            graphics.drawRect(left.toInt(), bottom.toInt(), (right-left).toInt(), (top - bottom).toInt())
        }
    }

    override fun getPreferredSize(): Dimension = Dimension(bufferedImage.width, bufferedImage.height)

    override fun getMinimumSize(): Dimension = Dimension(bufferedImage.width, bufferedImage.height)
}

const val DOG = "dog"
const val PERSON = "person"
const val CAR = "car"
const val BUS = "bus"
const val BENCH = "bench"
const val TRASH = "trash"
const val TRASH_BIN = "trash bin"
const val TRAFFIC_LIGHT = "traffic light"
const val CLOCK = "clock"
const val BICYCLE = "bicycle"
const val SIGN = "sign"
const val BACKPACK = "backpack"
const val HANDBAG = "handbag"







