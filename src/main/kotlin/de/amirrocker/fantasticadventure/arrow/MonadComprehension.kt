package de.amirrocker.fantasticadventure.arrow

// https://arrow-kt.io/docs/patterns/monad_comprehensions/

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
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
