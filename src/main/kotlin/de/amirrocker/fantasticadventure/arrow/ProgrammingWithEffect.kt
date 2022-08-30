package de.amirrocker.fantasticadventure.arrow

import arrow.fx.coroutines.guaranteeCase
import arrow.fx.coroutines.never
import arrow.fx.coroutines.parZip
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


// trying to learn arrow - using Arrow FX

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
