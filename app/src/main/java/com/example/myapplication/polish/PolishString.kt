package com.example.myapplication.polish

import android.util.Log

class PolishString constructor(expression: String, variables: MutableMap<String, Int>) {
    var expression: String
    var isExprCorrect: Boolean = true

    init {
        this.expression = expression.filter { !it.isWhitespace() }

        this.expression = CreateReversePolish(this.expression)
    }



    private fun CreateReversePolish(str: String): String {
        var reversePolish = ""
        val stack = Stack()

        val letterArr = arrayListOf<Char>()
        for (i in 48..57)
            letterArr.add(Char(i))
        for (i in 65..90)
            letterArr.add(Char(i))
        for (i in 97..122)
            letterArr.add(Char(i))
        letterArr.add(Char(95))

        for (char in str) {
            if (char in letterArr) {
                reversePolish += char
            } else if(reversePolish.isNotEmpty()){
                when (char) {
                    '+', '-', '*', '/', '%', '(' -> {
                        if (reversePolish.last() in letterArr && reversePolish.last() != ',') {
                            reversePolish += ','
                            Log.d("rePolish", "$reversePolish")
                        }
                        reversePolish += stack.push(char)
                    }
                    ')' -> {
                        if (reversePolish.last() in letterArr && reversePolish.last() != ',')
                            reversePolish += ','
                        reversePolish += stack.closeBracket()
                    }

                    else -> this.isExprCorrect = false
                }
            }
        }

        while (!stack.isEmpty()) {
            if(reversePolish.last() != ','){
                reversePolish += ','
            }

            reversePolish += stack.arrayOfChars.removeAt(stack.arrayOfChars.size - 1)
        }
        Log.d("polish", "$reversePolish")
        return "$reversePolish"
    }
}