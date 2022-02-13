package de.amirrocker.fantasticadventure.calculator

import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject


// represents 3x ->
data class Term(
    val variable:Double,
    val value:Double,
    var representation:String = ""
)


class PolynomCalculator(
) {

    val subject : Subject<PolynomCalculator> by lazy {  PublishSubject.create() }

    private val termList:MutableList<Double> = mutableListOf()
    private var result:Double = 0.0

    init {
        subject.subscribe { calculator ->
            result = termList.reduce { acc:Double, d: Double ->
                acc + d
            }
        }
    }

    infix fun add(a:Double):PolynomCalculator {
        postNewValue(a)
        return this
    }

    infix fun subtract(a:Double):PolynomCalculator {
        postNewValue(
            if(a<0) a else -a
        )
        return this
    }

    infix fun term(term:Term):PolynomCalculator {
        postNewValue(
            solveTerm(term)
        )
        return this
    }

    // solves 2x
    // where variable = x and value = 2
    fun solveTerm(term:Term) = term.value * term.variable

    fun postNewValue(newInput:Double) {
        termList.add(newInput)
        subject.onNext(this)
    }

    fun returnResult() = result

    suspend fun handleInput() {

    }

}

// kinda working - one can add and subtract
//class PolynomCalculator(
//) {
//
//    val subject : Subject<PolynomCalculator> by lazy {  PublishSubject.create() }
//
//    private val termList:MutableList<Double> = mutableListOf()
//    private var result:Double = 0.0
//
//    init {
//        subject.subscribe { calculator ->
//            result = termList.reduce { acc:Double, d: Double ->
//                acc + d
//            }
//        }
//    }
//
//    infix fun add(a:Double):PolynomCalculator {
//        postNewValue(a)
//        return this
//    }
//
//    infix fun subtract(a:Double):PolynomCalculator {
//        postNewValue(
//            if(a<0) a else -a
//        )
//        return this
//    }
//
//    fun postNewValue(newInput:Double) {
//        termList.add(newInput)
//        subject.onNext(this)
//    }
//
//    fun returnResult() = result
//
//    suspend fun handleInput() {
//
//    }
//
//}