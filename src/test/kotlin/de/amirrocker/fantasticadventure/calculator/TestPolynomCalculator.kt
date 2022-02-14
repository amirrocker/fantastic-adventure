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
        // 0.5x + 3y + 2.5 = z
        // x=1 & y=1 => 0.5 + 3 + 2.5 = 6
        // calculator term 0.5 term 3.0 add (5.0/2.0)
        // or even alternatively:

        // calculator solve "3x+7y-4=z" with Variable(x, 0, 10) with Variable(y, 0, 10) and plot "line"
        // calculator solve "-3x-7y+4=z" with Variable(x, 0, 10) with Variable(y, 0, 10) and plot "line"
        // this would be a lot more userfriendly - also, use lets_plot for plot "line"

        /*
         * the solve command must
         * 1.a) tokenize the passed in function as terms (tokens) - term1: 3x, term2: 7y, term3: -4
         * 1.b) tokenize the passed in function as terms (tokens) - term1: -3x, term2: -7y, term3: 4
         * Note that =z can be implied so no term needed
         *
         * the
         * 3)
         *
         */
        calculator solve "3x+7y-4=z"


        val result = calculator.returnResult()
        // then
        assertEquals(-13.0, result,"expect a valid result of 13.0 but was $result" )
    }
}