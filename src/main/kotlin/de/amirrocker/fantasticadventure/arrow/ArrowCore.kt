package de.amirrocker.fantasticadventure.arrow

import arrow.core.Either
import arrow.core.Ior

/** model */
object Meterreading
object Counter
object Record

// fun fetchUnsyncedMeterreadings(): Meterreading = TODO()
// fun retrieveCounters() : Counter = TODO()
// fun persistRecord() : Record = TODO()


// exceptions with Either

sealed class MeterreadingException {
    object NoConnectionPossible : MeterreadingException()
    data class InvalidMeter(val reason: String) : MeterreadingException()
}

typealias NoConnectionPossible = MeterreadingException.NoConnectionPossible
typealias InvalidMeter = MeterreadingException.InvalidMeter

fun fetchMeterreadings() : Either<NoConnectionPossible, Meterreading> = Either.Right(Meterreading)
fun failedFetchMeterreading() : Either<InvalidMeter, Meterreading> = Either.Left(InvalidMeter("No Counter defined"))



