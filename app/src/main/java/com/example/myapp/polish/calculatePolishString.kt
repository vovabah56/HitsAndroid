package com.example.myapplication.polish


import java.lang.Exception


fun calculatePolishString(polishString: String, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>,errorConsole: MutableList<String>): Int {
    val stack = Stack()
    var localPolishString = polishString


    val regex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")
    val matches = regex.findAll(polishString)
    for (key in matches){
        if (!variables.containsKey(key.value)) {
            errorConsole.add("#Invalid expression: ${key.value}")

            print("#Invalid expression: ${key.value}")
            return 0
        }
    }


    while (localPolishString.isNotEmpty()) {
        val expr = localPolishString.substringBefore(',')
        if (expr.matches(Regex("[a-zA-Z_][\\w]*"))) {
            stack.ordinaryPush(variables[expr])
        } else {
            if (expr.matches(Regex("[1-9][\\d]*|0"))) {
                try {
                    stack.ordinaryPush(expr.toInt())
                } catch (e: Exception) {
                    stack.ordinaryPush(0)
                }
            } else {
                if(stack.isEmpty2()){
                    print("#Invalid expression: stack")
                    errorConsole.add("#Invalid expression: stack")
                    return 0
                }
                val second = stack.ordinaryPop()
                if(stack.isEmpty2()){
                    print("#Invalid expression: stack")
                    errorConsole.add("#Invalid expression: stack")
                    return 0
                }
                val first = stack.ordinaryPop()
                when (expr) {
                    "+" -> stack.ordinaryPush(first + second)
                    "-" -> stack.ordinaryPush(first - second)
                    "*" -> stack.ordinaryPush(first * second)
                    "/" -> {
                        if(second == 0) {
                            errorConsole.add("#Devision by 0")
                            return 0
                        }
                        stack.ordinaryPush(first / second)
                    }
                    "%" -> {
                        if(second == 0) {
                            errorConsole.add("#Devision by 0")
                            return 0
                        }
                        stack.ordinaryPush(first % second)
                    }
                }
            }
        }
        localPolishString = localPolishString.slice(expr.length + 1 until localPolishString.length)
    }

    return stack.ordinaryPop()
}