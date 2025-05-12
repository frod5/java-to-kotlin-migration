package com.group.libraryapp.calculator

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
    calculatorTest.divideFailTest()
}

class CalculatorTest {

    fun addTest() {
        //given
        val calculator = Calculator(1)

        //when
        calculator.add(3)

        //then
        if (calculator.number != 4) {
           throw IllegalStateException()
        }
    }

    fun minusTest() {
        //given
        val calculator = Calculator(1)

        //when
        calculator.minus(1)

        //then
        if (calculator.number != 0) {
            throw IllegalStateException()
        }
    }

    fun multiplyTest() {
        //given
        val calculator = Calculator(1)

        //when
        calculator.multiply(3)

        //then
        if (calculator.number != 3) {
            throw IllegalStateException()
        }
    }

    fun divideTest() {
        //given
        val calculator = Calculator(6)

        //when
        calculator.divide(3)

        //then
        if (calculator.number != 2) {
            throw IllegalStateException()
        }
    }

    fun divideFailTest() {
        //given
        val calculator = Calculator(6)

        //when
        try {
            calculator.divide(0)
        } catch (e : IllegalArgumentException) {
            if(e.message != "Division by zero") {
                throw IllegalStateException("메세지가 다릅니다.")
            }

            // 테스트 성공
            return
        } catch (e : Exception) {
            throw IllegalStateException()
        }

        throw IllegalStateException("기대하는 예외가 발생하지 않았습니다.")
    }

}
