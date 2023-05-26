package com.example.myapp.interpretator


import com.example.myapplication.polish.PolishString

fun expressionBlock(
    tree: MutableList<String>,
    text: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>
) {

    var textExpression = text

    var strWhithArr = text
    val regex = Regex("[a-zA-Z_][a-zA-Z_0-9]*")
    val matches = regex.findAll(text)
    for (key in matches) {
        if (arrays.containsKey(key.value)) {
            var sizeArr = arrays[key.value]!!.size
            for (i in variables.keys) {
                if (variables[i]!! < sizeArr)
                    strWhithArr = "${key.value}\\[$i\\]".toRegex()
                        .replace(strWhithArr, arrays[key.value]!![variables[i]!!].toString())
            }
            for (i in 0..(sizeArr - 1)) {
                var ind = i.toString()


                strWhithArr = "${key.value}\\[$ind\\]".toRegex()
                    .replace(strWhithArr, arrays[key.value]!![i].toString())
            }
        }
    }

    textExpression = strWhithArr

    //проверка по скобкам
    var checkBrackets = 0
    var normBrackets = true
    for (i in textExpression) {
        if (i == '(')
            checkBrackets++
        else if (i == ')')
            checkBrackets--
        if (checkBrackets < 0)
            normBrackets = false
    }
    if (checkBrackets != 0)
        normBrackets = false

    //лишние символы
    val matchResult2 = "^[a-zA-Z_0-9+\\-/*%()\\s]*$".toRegex().find(textExpression)

    if (matchResult2?.value.toString() == textExpression
        && normBrackets
        && !textExpression.contains("[+\\-*/%]\\s*[+\\-*/%)]".toRegex())
        && !textExpression.contains("\\(\\s*\\*".toRegex())
        && !textExpression.contains("\\(\\s*/".toRegex())
        && !textExpression.contains("[^0-9a-zA-Z_]0[0-9]".toRegex())
        && !textExpression.contains("[a-zA-Z0-9_]\\s+[a-zA-Z0-9_]".toRegex())
        && !textExpression.contains("[^a-zA-Z_]+[0-9]+[a-zA-Z_]".toRegex())
    ) {

        textExpression = "^-".toRegex().replace(textExpression, "0-")

        textExpression = "^\\+".toRegex().replace(textExpression, "0+")

        textExpression = "\\(\\s*-".toRegex().replace(textExpression, "(0-")

        textExpression = "\\(\\s*\\+".toRegex().replace(textExpression, "(0+")

        textExpression = "(?<=[0-9a-zA-Z_])\\(".toRegex().replace(textExpression, "*(")

        textExpression = "\\)(?<=[0-9a-zA-Z_])".toRegex().replace(textExpression, ")*")

        //прогон по польской строке
        val polString = PolishString(textExpression, variables, arrays)

        if (polString.isExpressionCorrect) {
            tree.add(",+".toRegex().replace(polString.expression, ","))

        } else
            print("#Invalid expression block")

    } else
        print("#Invalid expression block")
}