package com.example.myapplication.polish


class PolishString constructor(expression: String, variables: MutableMap<String, Int>) {
    var expression: String
    var isExpressionCorrect: Boolean = true

    init {
        this.expression = expression.filter { !it.isWhitespace() }

        this.expression = turnToReversePolish(this.expression)
    }

    private fun turnToReversePolish(str: String): String {
        var reversePolish = ""
        val stack = Stack()

        val letterArray = arrayListOf<Char>()
        for (i in 48..57)
            letterArray.add(Char(i))
        for (i in 65..90)
            letterArray.add(Char(i))
        for (i in 97..122)
            letterArray.add(Char(i))
        letterArray.add(Char(95))

        for (char in str) {
            if (char in letterArray) {
                reversePolish += char
            } else if(reversePolish.isNotEmpty()){
                when (char) {
                    '+', '-', '*', '/', '%', '(',  -> {
                        if (reversePolish.last() in letterArray)
                            reversePolish += ','
                        reversePolish += stack.push(char)
                    }
                    ')' -> {
                        if (reversePolish.last() in letterArray)
                            reversePolish += ','
                        reversePolish += stack.closeBracket()
                    }

                    else -> this.isExpressionCorrect = false
                }
            }
        }
        while (!stack.isEmpty()) {

            reversePolish += ','
            reversePolish += stack.arrayOfChars.removeAt(stack.arrayOfChars.size - 1)
        }

        return "$reversePolish,"
    }
}