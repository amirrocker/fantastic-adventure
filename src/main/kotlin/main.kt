import de.amirrocker.fantasticadventure.arrow.AddToEither
import de.amirrocker.fantasticadventure.arrow.runComprehensionOverCoroutinesMinimal
import de.amirrocker.fantasticadventure.arrow.runLeftIdentityLaw
import de.amirrocker.fantasticadventure.arrow.testParallelCancellation
import kotlinx.coroutines.runBlocking

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
//    runTheBunch(DATASET_WOHNUNGSPREISE)

//    runSomeMatrices()

    // check krangl basics

    /*
    * Summarise cases
    *
    */
//    summarizeWithFixedColumnNames()
//    summarizeAndAppendMeanValues()

    // arrow stuff
    // runLeftIdentityLaw()
    runBlocking {
        // runComprehensionOverCoroutinesMinimal()
        // println(AddToEither())

        println(testParallelCancellation())

    }

    // summarizeWithFixedColumnNames()
    // summarizeAndAppendMeanValues()

    // testComposition()

    // function Composition:
    // calculatePrices()

    // partialApplication()

    // testPartialSplitter()



}