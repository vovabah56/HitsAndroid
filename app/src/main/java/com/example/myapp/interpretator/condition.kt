package com.example.myapp.interpretator



fun helpWithRegExp(
    arr: MutableList<String>,
    text: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>
) {
    var matchResult = "[^<=!>]+".toRegex().find(text)

    if (matchResult != null)
        expressionBlock(arr, matchResult.value.trim(), variables, arrays)
    matchResult = matchResult?.next()
    if (matchResult != null)
        expressionBlock(arr, matchResult.value.trim(), variables, arrays)
}

fun conditionBlock(text: String, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>): MutableList<String> {

    //лишние символы
    var strWhithArr = text
    val regex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")
    val matches = regex.findAll(text)
    for (key in matches){
        if (arrays.containsKey(key.value)) {
            var sizeArr = arrays[key.value]!!.size
            for (i in variables.keys){
                if(variables[i]!! < sizeArr)
                    strWhithArr = "${key.value}\\[$i\\]".toRegex().replace(strWhithArr, arrays[key.value]!![variables[i]!!].toString())
            }
            for (i in 0..(sizeArr-1)){
                var ind = i.toString()


                strWhithArr = "${key.value}\\[$ind\\]".toRegex().replace(strWhithArr, arrays[key.value]!![i].toString())
            }
        }
    }
    var text = strWhithArr
    val matchResult = "^[a-zA-Z_0-9+\\-/*%()<!=>\\s]*$".toRegex().find(text)
    var arr = mutableListOf<String>()
    if (matchResult?.value == text) {
        if (text.contains("^[^<=!>]*<[^<=!>]*$".toRegex())) {
            arr.add("<")
            helpWithRegExp(arr, text, variables, arrays)
        } else if (text.contains("^[^<=!>]*>[^<=!>]*$".toRegex())) {
            arr.add(">")
            helpWithRegExp(arr, text, variables,arrays)
        } else if (text.contains("^[^<=!>]*<=[^<=!>]*$".toRegex())) {
            arr.add("<=")
            helpWithRegExp(arr, text, variables, arrays)
        } else if (text.contains("^[^<=!>]*>=[^<=!>]*$".toRegex())) {
            arr.add(">=")
            helpWithRegExp(arr, text, variables, arrays)
        } else if (text.contains("^[^<=!>]*==[^<=!>]*$".toRegex())) {
            arr.add("==")
            helpWithRegExp(arr, text, variables, arrays)
        } else if (text.contains("^[^<=!>]*!=[^<=!>]*$".toRegex())) {
            arr.add("!=")
            helpWithRegExp(arr, text, variables, arrays)
        } else
            print("#Invalid condition block")
        //printInConsole("#Invalid condition block", console, ctx)
    } else
        print("#Invalid condition block")
    //printInConsole("#Invalid condition block", console, ctx)
    return arr
}