package de.amirrocker.fantasticadventure.calculator

import kotlin.math.sqrt

class SingleTermCalculator {

    // public api
    infix fun add(term:Pair<Int,Int>) = calculate(term.first, term.second, plusOperation)
    infix fun minus(term:Pair<Int,Int>) = calculate(term.first, term.second, minusOperation)
    infix fun multiply(term:Pair<Int,Int>) = calculate(term.first, term.second, multiplyOperation)
    infix fun divide(term:Pair<Int,Int>) = calculate(term.first, term.second, divideOperation)
    infix fun exponent(term:Pair<Int,Int>) = calculate(term.first, term.second, exponentOperation)
    infix fun squareRoot(term:Double) = calculate(term, 0.0, sqrtOperation)

    // private api
    private infix fun Int.plus(b:Int) = this.plus(b)
    private infix fun Int.minus(b:Int) = this.minus(b)
    private infix fun Int.multiply(b:Int) = this.times(b)
    private infix fun Int.divide(b:Int) = this.div(b)
    private infix fun Int.exponent(b:Int) = (0 until b).fold(1) { acc: Int, i: Int -> acc * this }

    private val plusOperation = { a:Int, b:Int -> a plus b }
    private val minusOperation = { a:Int, b:Int -> a minus b }
    private val multiplyOperation = { a:Int, b:Int -> a multiply b }
    private val divideOperation = { a:Int, b:Int -> a divide b }
    private val exponentOperation = { a:Int, b:Int -> a exponent b }
    private val sqrtOperation = { a:Double, _:Double -> sqrt(a) }

    private fun <T:Number> calculate(a:T, b:T, f:(T, T)->T):T = f(a, b)
}