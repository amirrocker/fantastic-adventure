import de.amirrocker.fantasticadventure.kotlindl.runSomeMatrices
import de.amirrocker.fantasticadventure.thejoyof.runPolymorphicCalculatorTest
import de.amirrocker.fantasticadventure.thejoyof.testMe
import de.amirrocker.fantasticadventure.thejoyof.testPolyTuple

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
//    letsPlotALinePlot( DATASET_WOHNUNGSPREISE)

    // works
//    runTheBunch(DATASET_WOHNUNGSPREISE)

    runSomeMatrices()

    // check krangl basics

    /*
    * Summarise cases
    *
    */
//    summarizeWithFixedColumnNames()
//    summarizeAndAppendMeanValues()

    // arrow stuff
    // runLeftIdentityLaw()
//    runBlocking {
//        // runComprehensionOverCoroutinesMinimal()
//        // println(AddToEither())
//
//        println(testParallelCancellation())
//
//    }

//    testPrimes()

//    testFunctionAsVal()
//
//    functionPolymorphism()
//
//    println(allBooksPrices())
//
//    println(allBooksWeights())

    // summarizeWithFixedColumnNames()
    // summarizeAndAppendMeanValues()

    // testComposition()

    // function Composition:
    // calculatePrices()

    // partialApplication()

    // testPartialSplitter()


//    sketching around:
//    a Value Iteration algorithm description
//    with some naive sketching around
//    runSimpleValueIteration()

//    the joy of
//    runPolymorphicCalculatorTest()
    testMe()

    // test with Int & Double
    testPolyTuple(4, 4.0)
    // test with Int & Int
    testPolyTuple(3, 3)
    // test with Double & Double
    testPolyTuple(2.5, 2.0)


}