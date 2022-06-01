import de.amirrocker.fantasticadventure.kotlindl.createManualDataframe
import de.amirrocker.fantasticadventure.kotlindl.letsPlotALinePlot
import de.amirrocker.fantasticadventure.kotlindl.minimal
import de.amirrocker.fantasticadventure.kotlindl.readInCsv
import de.amirrocker.fantasticadventure.kotlindl.runSomeMatrices
import de.amirrocker.fantasticadventure.kotlindl.runTheBunch

const val DATASET_WOHNUNGSPREISE = "src/main/resources/datasets/wohnungspreise.csv"


fun main() {
//    val calculator = Calculator(10, 12)
//    calculator.addNewNumbers(20, 24)
//
//    runSequence()
//
//    println("calc: $calculator")

//    val calculator = PolynomCalculator()
//    calculator.postNewValue(listOf(3.0))
//
//    println("calc: $calculator")

//    testKotlinDL()


//    detectObjectsInStillImage()

//    createManualDataframe()

//    readInCsv()

    // fails due to missing backend ?
//    minimal()

    // works
//    letsPlotALinePlot( DATASET_WOHNUNGSPREISE /*"src/main/resources/datasets/wohnungspreise.csv"*/)

    // works
    runTheBunch(DATASET_WOHNUNGSPREISE)

//    runSomeMatrices()



}