package com.example.myapp.interpretator



fun helpWithRegExp(
    arr: MutableList<String>,
    text: String,
    variables: MutableMap<String, Int>
) {
    var matchResult = "[^<=!>]+".toRegex().find(text)

    if (matchResult != null)
        expressionBlock(arr, matchResult.value.trim(), variables)
    matchResult = matchResult?.next()
    if (matchResult != null)
        expressionBlock(arr, matchResult.value.trim(), variables)
}

fun conditionBlock(text: String, variables: MutableMap<String, Int>): MutableList<String> {

    //лишние символы
    val matchResult = "^[a-zA-Z_0-9+\\-/*%()<!=>\\s]*$".toRegex().find(text)
    var arr = mutableListOf<String>()
    if (matchResult?.value == text) {
        if (text.contains("^[^<=!>]*<[^<=!>]*$".toRegex())) {
            arr.add("<")
            helpWithRegExp(arr, text, variables)
        } else if (text.contains("^[^<=!>]*>[^<=!>]*$".toRegex())) {
            arr.add(">")
            helpWithRegExp(arr, text, variables)
        } else if (text.contains("^[^<=!>]*<=[^<=!>]*$".toRegex())) {
            arr.add("<=")
            helpWithRegExp(arr, text, variables)
        } else if (text.contains("^[^<=!>]*>=[^<=!>]*$".toRegex())) {
            arr.add(">=")
            helpWithRegExp(arr, text, variables)
        } else if (text.contains("^[^<=!>]*==[^<=!>]*$".toRegex())) {
            arr.add("==")
            helpWithRegExp(arr, text, variables)
        } else if (text.contains("^[^<=!>]*!=[^<=!>]*$".toRegex())) {
            arr.add("!=")
            helpWithRegExp(arr, text, variables)
        } else
            print("#Invalid condition block")
            //printInConsole("#Invalid condition block", console, ctx)
    } else
        print("#Invalid condition block")
        //printInConsole("#Invalid condition block", console, ctx)
    return arr
}