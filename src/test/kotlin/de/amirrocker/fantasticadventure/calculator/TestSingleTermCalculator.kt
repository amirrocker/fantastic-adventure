package de.amirrocker.fantasticadventure.calculator

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class TestSingleTermCalculator {

    private val singleTermCalculator:SingleTermCalculator by lazy { SingleTermCalculator() }

    @Test
    internal fun `given two Int values when plusOperation is used then result should be a valid sum`() {
        // given
        // when
        val result = singleTermCalculator add Pair(1, 1) // calculator.calculate(a, b, calculator.plusOperation)

        // then
        result shouldBe 2
    }

    @Test
    internal fun `given two Int values when minusOperation is used then result should be a valid difference`() {
        // given
        // when
        val result = singleTermCalculator minus Pair(-3, 4)

        // then
        result shouldBe -7
    }

    @Test
    internal fun `given two Int values when multiplyOperation is used then result should be a valid product`() {
        // given
        // when
        val result = singleTermCalculator multiply Pair(-3, 4)

        // then
        result shouldBe -12
    }

    @Test
    internal fun `given two Int values when divideOperation is used then result should be a valid fraction`() {
        // given
        // when
        val result = singleTermCalculator divide Pair(-12, 4)

        // then
        result shouldBe -3
    }

    @Test
    internal fun `given two Int values when exponentOperation is used then result should be a valid exponent`() {
        // given
        // when
        val result = singleTermCalculator exponent Pair(3, 3)

        // then
        result shouldBe 27
    }

    @Test
    internal fun `given two Int values when sqrtOperation is used then result should be a valid sqrt`() {
        // given
        // when
        val result = singleTermCalculator squareRoot 9.0

        // then
        result shouldBeEqualTo 3.0
    }
}