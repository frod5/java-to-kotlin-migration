package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorJunitTest {

    @Test
    fun addTest() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.add(3)

        //then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.minus(1)

        //then
        assertThat(calculator.number).isEqualTo(4)
    }

    @Test
    fun multiplyTest() {
        //given
        val calculator = Calculator(5)

        //when
        calculator.multiply(3)

        //then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTest() {
        //given
        val calculator = Calculator(6)

        //when
        calculator.divide(3)

        //then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideFailTest() {
        //given
        val calculator = Calculator(6)

        //when & then
        val message = assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.message

        assertThat(message).isEqualTo("Division by zero")
    }

}
