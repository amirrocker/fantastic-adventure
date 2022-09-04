package de.amirrocker.fantasticadventure.effectivekotlin

val primes = sequence<Int> {
    var numbers = generateSequence(2) {
        it + 1
    }
    while (true) {
        val prime = numbers.first()
        yield(prime)
        numbers = numbers.drop(1).filter { it % prime != 0 }
    }
}

fun testPrimes() = println(primes.asIterable().forEach { println("it: $it") } )