package de.amirrocker.fantasticadventure

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

data class Operation(
    val operation:Operation,
    val terms:Pair<Double, Double>
)

enum class Operator {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    EXPONENT,
    SQUARE_ROOT,
}

data class Calculation(
    val operators:List<Operation>,
)

class Calculator(
    a:Int,
    b:Int
) {

    val subjectCalculator : Subject<Calculator> by lazy {
        PublishSubject.create()
    }

    private var disposable : Disposable

    var nums:Pair<Int, Int> = Pair(0,0)

    init {
        nums = Pair(a, b)
        disposable = subjectCalculator.subscribe {
            with(it) {
                calculateAddition(nums.first, nums.second)
                calculateSubtract(nums.first, nums.second)
            }
        }
        subjectCalculator.onNext(this)
    }

    fun calculateAddition(a:Int, b:Int):Int {
        println("a($a)+b($b)=${a+b}")
        return a+b
    }
    fun calculateSubtract(a:Int, b:Int):Int {
        println("a($a)*b($b)=${a*b}")
        return a*b
    }

    fun addNewNumbers(a:Int, b:Int) {
        nums = Pair(a, b)
        subjectCalculator.onNext(this)
    }
}

// build a fibonaci sequence
val series = sequence<Int> {
    var a = 0
    var b = 1
    yield(a)
    yield(b)
    while(true) {
        val c = a+b
        yield(c)
        a = b
        b = c
    }
}

fun runSequence() {
    println(series.take(10).joinToString {i: Int ->  "$i, " })
}