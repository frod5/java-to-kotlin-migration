package com.group.libraryapp.calculator

class Calculator(
    var number: Int
) {

    fun add(value: Int) {
        this.number += value
    }

    fun minus(value: Int) {
        this.number -= value
    }

    fun multiply(value: Int) {
        this.number *= value
    }

    fun divide(value: Int) {
        if(value == 0) {
            throw IllegalArgumentException("Division by zero")
        }
        this.number /= value
    }
}
