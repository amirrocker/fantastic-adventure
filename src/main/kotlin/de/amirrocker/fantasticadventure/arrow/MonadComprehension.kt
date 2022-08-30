package de.amirrocker.fantasticadventure.arrow

// https://arrow-kt.io/docs/patterns/monad_comprehensions/

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.computations.either
import arrow.core.computations.ensureNotNull
import arrow.core.flatMap

/** model */
object NotFound
data class Name(val value:String)
data class UniversityId(val value:String)
data class University(val name:Name, val deanName: Name)
data class Student(val name:Name, val universityId: UniversityId)
data class Dean(val name:Name)

/* student db */
private val students = mapOf(
    Name("Alice") to Student(Name("Alice"), UniversityId("UCA"))
)

/* university db */
private val universities = mapOf(
    UniversityId("UCA") to University(Name("UCA"), Name("DeanDude"))
)

/* dean db */
private val deans = mapOf(
    Name("DeanDude") to Dean(Name("DeanDude"))
)

/* Repository API */
suspend fun student(name:Name): Either<NotFound, Student> =
    students[name]?.let(::Right) ?: Left(NotFound)

suspend fun university(universityId:UniversityId): Either<NotFound, University> =
    universities[universityId]?.let(::Right) ?: Left(NotFound)

suspend fun dean(name:Name): Either<NotFound, Dean> =
    deans[name]?.let(::Right) ?: Left(NotFound)

// monad test
suspend fun main(): Unit {
    val dean = student(Name("Alice"))
        .flatMap { student ->
            university(UniversityId("UCA"))
        }
        .flatMap { university ->
            dean(university.deanName)
        }
    println(dean)
}

// Comprehensions over coroutines
suspend fun runComprehensionOverCoroutinesMinimal() =
    either<String, Int> {
        val x: Int? = 1
        ensureNotNull(x) { "x is not null -> so it passes" }
        println(x)
        ensureNotNull(null) { "passed null so it fails" }
    }.let(::println)

suspend fun AddToEither() =
    either<String, Int> {
        val one = Right(1).fold(
            ifLeft = {
                    it
                },
            ifRight = {
                it
            }
        )
        1 + one
    }

// the short version
fun WithoutComprehensions() = Right(1).flatMap {
    Right(1 + it)
}
// a commented long version of the above three lines
//    val x: Either<String, Int> = Right(1)
//    val result = x.flatMap { xValue ->
//        Right(1 + xValue)
//    }
//}

// Dean fetching with comprehensions
// is a lot better to read than the flatMap variant without comprehensions.
suspend fun fetchDean():Either<NotFound, Dean> = either {
    val alice = student(Name("Alice")).bind()
    val uca = university(UniversityId("UCA")).bind()
    val dean = dean(Name("DeanDude")).bind()
    dean
}



