package de.amirrocker.fantasticadventure.calculator

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestPolynomCalculator {

    @Test
    internal fun `given a number of Ints when add multiple times then expect a valid sum`() {
        // given
        val calculator = PolynomCalculator()

        // when
        calculator add 6.0 add 7.0
        val result = calculator.returnResult()

        // then
        assertEquals(result, 13.0,  "expect a valid result of 13.0 but was $result" )
    }

    @Test
    internal fun `given a number of negative Ints when subtract multiple times then expect a valid difference`() {
        // given
        val calculator = PolynomCalculator()

        // when
        calculator subtract -6.0 subtract -7.0
        val result = calculator.returnResult()

        // then
        assertEquals(-13.0, result,"expect a valid result of 13.0 but was $result" )
    }

    @Test
    internal fun `given a number of Terms when add multiple times then expect a valid sum`() {
        // given
        val calculator = PolynomCalculator()

        // when
        // y = ae2 + 2ab + be2
        // y = 3x + 2y + 4/3
        // what I want : calculator term "2x" term "3y" add (5/2)
        //calculator term "2x" term "3y" add (5/2)
        val result = calculator.returnResult()

        // then
        assertEquals(-13.0, result,"expect a valid result of 13.0 but was $result" )
    }
}