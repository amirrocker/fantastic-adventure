package de.amirrocker.fantasticadventure.arrow

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.left
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.Test

class ArrowCoreTest {

    @Test
    internal fun `given prepareEither when invalid value then fail either`() {

        runBlocking {
            val result = prepareEither()
            println(result)
            result.tap {
                it shouldBeInstanceOf Meterreading::class.java

            }
        }

    }

    // this seems to be some short-circuit mechanism.
    suspend fun prepareEither(): Either<MeterreadingException, Meterreading> =
        either {
            val meterreading = fetchMeterreadings().bind()
            val failed = failedFetchMeterreading().bind()
            failed
        }


}