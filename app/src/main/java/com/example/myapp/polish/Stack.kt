package com.example.myapplication.polish

class Stack {
    var arrayOfChars = mutableListOf<Char>()
    private var arrayOfInts = mutableListOf<Int>()
    private var operatorPriority =
        mutableMapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '%' to 2, '(' to -1)

    fun isEmpty(): Boolean {
        return arrayOfChars.isEmpty()
    }
    fun isEmpty2(): Boolean {
        return arrayOfInts.isEmpty()
    }

    fun ordinaryPush(element: Int?) {
        if (element != null) {
            arrayOfInts.add(element)
        }
    }

    fun ordinaryPop(): Int {
        return arrayOfInts.removeAt(arrayOfInts.size - 1)
    }

    fun push(element: Char): String {
        var output = ""
        if (element == '(') {
            arrayOfChars.add(element)
            return output
        }

        while (arrayOfChars.size > 0) {
            if (operatorPriority[element]!! <= operatorPriority[arrayOfChars.last()]!!) {
                output += arrayOfChars.removeAt(arrayOfChars.size - 1)
                output += ","
            } else {
                break
            }
        }

        arrayOfChars.add(element)
        return output
    }

    fun closeBracket(): String {
        var output = ""
        while (arrayOfChars.isNotEmpty() && arrayOfChars.last() != '(') {
            output += arrayOfChars.removeAt(arrayOfChars.size - 1)
        }
        arrayOfChars.removeAt(arrayOfChars.size - 1)
        output += ','
        return output
    }
}