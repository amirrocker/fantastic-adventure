package de.amirrocker.fantasticadventure.calculator

import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject


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
        postNewValue(listOf(a))
        return this
    }

    infix fun subtract(a:Double):PolynomCalculator {
        postNewValue(
            if(a<0) listOf(a) else listOf(-a)
        )
        return this
    }

    infix fun term(term:Double):PolynomCalculator {
        postNewValue(
            solveTerm(term)
        )
        return this
    }

    // solves 2x
    // where variable = x and value = 2
    // Note: for now only once - remember we need 0..10
    fun solveTerm(term:Double) = (0 until 1).map { term }

    fun postNewValue(newInput:List<Double>) {
        termList.add(newInput[0])
        subject.onNext(this)
    }

    fun returnResult() = result

    // start with solving the string function into terms
    infix fun solve(func:String) {
        val regex = """\dx""".toRegex()
        val matchResult = regex.find(func)
        println(matchResult.value)
    }

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