package com.example.myapp.interpretator

import android.os.SystemClock.sleep
import android.util.Log

import com.example.myapp.model.*
import com.example.myapplication.polish.PolishString
import com.example.myapplication.polish.calculatePolishString


fun interpreter(
    blocks: MutableList<Block>,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,
) {
    val size = blocks.size
    for (i in 0 until size) {
        when (blocks[i].blockType) {
            is VarBlock -> varBlock(
                blocks[i].blockType as VarBlock, variables, arrays, console
            )

            is IfBlock -> {
                if (i < (size - 1) && blocks[i + 1].blockType is ElseBlock) {
                    ifElseBlock(
                        blocks[i].blockType as IfBlock,
                        blocks[i + 1].blockType as ElseBlock,
                        variables,
                        arrays,
                        console
                    )
                } else {
                    ifBlock(
                        blocks[i].blockType as IfBlock, variables, arrays, console
                    )
                }
            }

            is WhileBlock -> whileBlock(
                blocks[i].blockType as WhileBlock, variables, arrays, console
            )

            is DoWhileBlock -> doWhileBlock(
                blocks[i].blockType as DoWhileBlock, variables, arrays, console
            )

            is PrintBlock -> out(
                blocks[i].blockType as PrintBlock, variables, arrays, console
            )

            is ForBlock -> forBlock(
                blocks[i].blockType as ForBlock, variables, arrays, console
            )

            is InputBlock -> inputBlock(
                blocks[i].blockType as InputBlock, variables, arrays, console
            )
        }
    }
}

fun inputBlock(
    block: InputBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,

    ) {
    console.add(LogData("getting value of ${block.name}"))
    while (!console.last().log.startsWith("input ${block.name}")) {
        Thread.sleep(100)
    }
    val value = console.last().log.substringAfter("input ${block.name} is ").toInt()
    variables[block.name] = value
}

fun ifElseBlock(
    blockIf: IfBlock,
    blockElse: ElseBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,


    ) {

    if (blockConditions(blockIf.condition, variables, arrays, console)) {
        interpreter(blockIf.blocks, variables, arrays, console)
    } else {
        interpreter(blockElse.blocks, variables, arrays, console)
    }
}

fun forBlock(
    block: ForBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,

    ) {
    val range = block.range.split(",")
    if (range.size != 2) {
        console.add(LogData("#Invalid array", true))
    }
    val first = calculatePolishString(
        PolishString(range[0], variables, arrays, console).expression,
        variables,
        arrays,
        console
    )
    val second = calculatePolishString(
        PolishString(range[1], variables, arrays, console).expression,
        variables,
        arrays,
        console
    )
    var localMap = mutableMapOf<String, Int>()
    for (i in variables.keys) {
        localMap[i] = variables[i] as Int
    }

    for (i in first..second) {

        localMap[block.variable] = i
        interpreter(block.blocks, localMap, arrays, console)
    }

}


fun blockConditions(
    condition: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,


    ): Boolean {
    var res = false
    val arr = conditionBlock(condition, variables, arrays, console)

    if (arr[0] == "!=") {
        if (calculatePolishString(arr[1], variables, arrays, console) != calculatePolishString(
                arr[2], variables, arrays, console
            )
        ) {
            res = true
        }
    }
    if (arr[0] == "==") {
        if (calculatePolishString(arr[1], variables, arrays, console) == calculatePolishString(
                arr[2], variables, arrays, console
            )
        ) {
            res = true
        }
    }
    if (arr[0] == ">=") {
        if (calculatePolishString(arr[1], variables, arrays, console) >= calculatePolishString(
                arr[2], variables, arrays, console
            )
        ) {
            res = true
        }
    }
    if (arr[0] == "<=") {
        if (calculatePolishString(
                arr[1], variables, arrays, console
            ) <= calculatePolishString(
                arr[2], variables, arrays, console
            )
        ) {
            res = true
        }
    }
    if (arr[0] == "<") {
        if (calculatePolishString(
                arr[1], variables, arrays, console
            ) < calculatePolishString(
                arr[2], variables, arrays, console
            )
        ) {
            res = true
        }
    }
    if (arr[0] == ">") {
        if (calculatePolishString(
                arr[1], variables, arrays, console
            ) > calculatePolishString(arr[2], variables, arrays, console)
        ) {
            res = true
        }
    }

    return res
}

fun ifBlock(
    block: IfBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,

    ) {
    var localMap = mutableMapOf<String, Int>()
    for (i in variables.keys) {
        localMap[i] = variables[i] as Int
    }
    if (blockConditions(block.condition, variables, arrays, console)) {
        interpreter(block.blocks, localMap, arrays, console)
    }
}

fun whileBlock(
    block: WhileBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,

    ) {
    var localMap = mutableMapOf<String, Int>()
    for (i in variables.keys) {
        localMap[i] = variables[i] as Int
    }
    while (blockConditions(block.condition, variables, arrays, console)) {
        interpreter(block.blocks, localMap, arrays, console)
    }
}


fun doWhileBlock(
    block: DoWhileBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,


    ) {
    interpreter(block.blocks, variables, arrays, console)
    while (blockConditions(block.condition, variables, arrays, console)) {
        interpreter(block.blocks, variables, arrays, console)
    }
}

fun varBlock(
    block: VarBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,
) {
    if ("," in block.value && "[" in block.value && "]" in block.value) {
        val str = block.value.replace("[", "").replace("]", "")


        val d = str.split(",")
        val arrInt = mutableListOf<Int>()
        for (i in d) {
            arrInt.add(i.toInt())
        }
        arrays[block.name] = arrInt
    } else if ("[" in block.name && "]" in block.name) {

        val str = block.name.split("[")

        val name = str[0]
        val index = str[1].replace("]", "")
        val ind = calculatePolishString(
            PolishString(index, variables, arrays, console).expression,
            variables,
            arrays,
            console
        )
        arrays[name]?.set(
            ind, calculatePolishString(
                PolishString(block.value, variables, arrays, console).expression,
                variables,
                arrays,
                console
            )
        )
    } else {
        variables[block.name] = calculatePolishString(
            PolishString(block.value, variables, arrays, console).expression,
            variables,
            arrays,
            console
        )
    }
}


fun out(
    block: PrintBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<LogData>,

    ) {
    console.add(
        LogData(
            calculatePolishString(
                PolishString(block.value, variables, arrays, console).expression,
                variables,
                arrays, console
            ).toString()
        )
    )
//    Log.d(
//        "asas", calculatePolishString(
//            PolishString(block.value, variables, arrays, console).expression,
//            variables,
//            arrays,
//            console
//        ).toString()
//    )
}
