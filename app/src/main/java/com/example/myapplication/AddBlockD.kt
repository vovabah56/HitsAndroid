package com.example.myapplication

class AdditionBlock(val firstNumber: Int, val secondNumber: Int) : Block("AddBlock") {
    fun execute(): Int {
        return firstNumber + secondNumber
    }
}
