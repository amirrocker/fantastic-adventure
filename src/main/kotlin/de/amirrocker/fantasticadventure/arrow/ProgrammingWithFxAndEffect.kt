package de.amirrocker.fantasticadventure.arrow

import arrow.fx.coroutines.ExitCase
import arrow.fx.coroutines.guaranteeCase
import arrow.fx.coroutines.never
import arrow.fx.coroutines.parZip
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


// trying to learn arrow - using Arrow FX
// https://arrow-kt.io/docs/fx/parallel/

suspend fun testParallelCancellation() = coroutineScope {
    val fiber = async {
        parZip<Int, Int, Int, Unit>(
            fa = {
                guaranteeCase(
                    fa = {
                        delay(1000)
                        1 + 1
                    }
                ) { exitCase: ExitCase -> println("completed after delay of 1 sec : $exitCase") }
            },
            fb = {
                guaranteeCase(
                    fa={
                        never<Int>()
                    }
                ) {exitCase:ExitCase -> println("never complete: $exitCase") }
            },
            fc = {
                guaranteeCase(
                    fa = {
                        delay(500)
                        2 + 2
                    }
                ) { exitCase: ExitCase -> println("never complete : $exitCase") }
            }
        ) { i: Int, i2: Int, i3: Int -> println("I am never called !") }
    }
    delay(2000)
    fiber.cancel()
    fiber.await()
}

//data class FakeRepo(val )
//
//suspend fun testParallelHappyPath() = coroutineScope {
//    val fiber = async {
//        parZip<String, Int, Double, Unit>(
//            fa = {
//                 guaranteeCase({
//                     delay(2000)
//                    val result:String = repo.getFakeResult()
//                     println("received result from repo: $result")
//                     result
//                 })
//            },
//            fb = {},
//            fc = {},
//        )
//    }
//}


suspend fun main():Unit = coroutineScope {
    val fiber = async {
        parZip<Int, Int, Unit>({
            delay(1000)
            throw RuntimeException("Boom crash")
        }, {
            guaranteeCase({
                never()
            }) { exitCase -> System.out.println("I never complete: $exitCase") }
        }) { a, b -> System.out.println(" I am never called") }
    }
}
