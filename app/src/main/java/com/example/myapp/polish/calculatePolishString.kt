package com.example.myapplication.polish


import java.lang.Exception
import java.util.Stack


fun calculatePolishString(polishString: String, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>,errorConsole: MutableList<String>): Int {
    val stack = Stack<Int>()
    var localPolishString = polishString

    val regex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")
    val matches = regex.findAll(polishString)
    for (key in matches){
        if (!variables.containsKey(key.value)) {
            print("#Invalid expression: ${key.value}")
            return 0
        }
    }

    while (localPolishString.isNotEmpty()) {
        val expr = localPolishString.substringBefore(',')
        if (expr.matches(Regex("[a-zA-Z_][\\w]*"))) {
            stack.push(variables[expr] ?: 0)
        } else {
            if (expr.matches(Regex("[1-9][\\d]*|0"))) {
                try {
                    stack.push(expr.toInt())
                } catch (e: Exception) {
                    stack.push(0)
                }
            } else {
                when (expr) {
                    "+" -> {
                        if (stack.size < 2) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val second = stack.pop()
                        val first = stack.pop()
                        stack.push(first + second)
                    }
                    "-" -> {
                        if (stack.size < 2) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val second = stack.pop()
                        val first = stack.pop()
                        stack.push(first - second)
                    }
                    "*" -> {
                        if (stack.size < 2) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val second = stack.pop()
                        val first = stack.pop()
                        stack.push(first * second)
                    }
                    "/" -> {
                        if (stack.size < 2) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val second = stack.pop()
                        val first = stack.pop()
                        if (second == 0) {
                            print("#Division by 0")
                            return 0
                        }
                        stack.push(first / second)
                    }
                    "%" -> {
                        if (stack.size < 2) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val second = stack.pop()
                        val first = stack.pop()
                        if (second == 0) {
                            errorConsole.add("#Devision by 0")
                            return 0
                        }
                        stack.push(first % second)
                    }
                    "(" -> {
                        stack.push(-1)
                    }
                    ")" -> {
                        if (stack.size < 3) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val third = stack.pop()
                        if (third != -1) {
                            print("#Invalid expression: stack")
                            errorConsole.add("#Invalid expression: stack")
                            return 0
                        }
                        val result = com.example.myapp.polish.calculatePolishString(
                            localPolishString.substringAfter("(").substringBefore(")"), variables
                        )
                        stack.push(result)
                    }
                }
            }
        }
        localPolishString = localPolishString.slice(expr.length + 1 until localPolishString.length)
    }

    return stack.pop()
}