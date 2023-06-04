package com.example.myapp.interpretator

import com.example.myapp.model.LogData


fun helpWithRegExp(
    arr: MutableList<String>,
    text: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>
) {
    var matchResult = "[^<=!>]+".toRegex().find(text)

    if (matchResult != null)
        expressionBlock(arr, matchResult.value.trim(), variables, arrays, console)
    matchResult = matchResult?.next()
    if (matchResult != null)
        expressionBlock(arr, matchResult.value.trim(), variables, arrays, console)
}

fun conditionBlock(
    text: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>
): MutableList<String> {

    //лишние символы
    var strWhithArr = text
    val regex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")

    val matches = regex.findAll(text)
    for (key in matches) {
        if (arrays.containsKey(key.value)) {
            val sizeArr = arrays[key.value]!!.size
            for (i in variables.keys) {
                if (variables[i]!! < sizeArr)
                    strWhithArr = "${key.value}\\[$i\\]".toRegex()
                        .replace(strWhithArr, arrays[key.value]!![variables[i]!!].toString())
            }
            for (i in 0 until (sizeArr - 1)) {
                val ind = i.toString()


                strWhithArr = "${key.value}\\[$ind\\]".toRegex()
                    .replace(strWhithArr, arrays[key.value]!![i].toString())
            }
        }
    }

    if (strWhithArr.contains("\\[.*\\]".toRegex())) {
        console.add(LogData("Error in the index array in condition block", true))
    }

    var text = strWhithArr
    val matchResult = "^[a-zA-Z_0-9+\\-/*%()<!=>\\s]*$".toRegex().find(text)
    var arr = mutableListOf<String>()
    if (matchResult?.value == text) {
        if (text.contains("^[^<=!>]*<[^<=!>]*$".toRegex())) {
            arr.add("<")
            helpWithRegExp(arr, text, variables, arrays, console)
        } else if (text.contains("^[^<=!>]*>[^<=!>]*$".toRegex())) {
            arr.add(">")
            helpWithRegExp(arr, text, variables, arrays, console)
        } else if (text.contains("^[^<=!>]*<=[^<=!>]*$".toRegex())) {
            arr.add("<=")
            helpWithRegExp(arr, text, variables, arrays, console)
        } else if (text.contains("^[^<=!>]*>=[^<=!>]*$".toRegex())) {
            arr.add(">=")
            helpWithRegExp(arr, text, variables, arrays, console)
        } else if (text.contains("^[^<=!>]*==[^<=!>]*$".toRegex())) {
            arr.add("==")
            helpWithRegExp(arr, text, variables, arrays, console)
        } else if (text.contains("^[^<=!>]*!=[^<=!>]*$".toRegex())) {
            arr.add("!=")
            helpWithRegExp(arr, text, variables, arrays, console)
        } else {
            console.add(LogData("#Invalid condition block", true))
        }
    } else {
        console.add(LogData("#Invalid condition block", true))
    }
    return arr
}