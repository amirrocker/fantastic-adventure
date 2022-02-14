import de.amirrocker.fantasticadventure.calculator.PolynomCalculator

fun main() {
//    val calculator = Calculator(10, 12)
//    calculator.addNewNumbers(20, 24)
//
//    runSequence()
//
//    println("calc: $calculator")

    val calculator = PolynomCalculator()
    calculator.postNewValue(listOf(3.0))

    println("calc: $calculator")

}