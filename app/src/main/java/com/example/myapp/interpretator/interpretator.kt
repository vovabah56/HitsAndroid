package com.example.myapp.interpretator

import android.util.Log
import com.example.myapp.model.*
import com.example.myapplication.polish.PolishString
import com.example.myapplication.polish.calculatePolishString


fun interpretator(
    blocks: MutableList<Block>,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>
) {
    var size = blocks.size
    for (i in 0..(size - 1)) {
        when (blocks[i].blockType) {
            is VarBlock -> varBlock(blocks[i].blockType as VarBlock, variables, arrays,console)
            is IfBlock -> {
                if (i < (size - 1) && blocks[i + 1].blockType is ElseBlock) {
                    ifElseBlock(blocks[i].blockType as IfBlock, blocks[i + 1].blockType as ElseBlock, variables, arrays,console)
                } else {
                    ifBlock(blocks[i].blockType as IfBlock, variables, arrays,console)
                }
            }

            is WhileBlock -> whileBlock(blocks[i].blockType as WhileBlock, variables, arrays,console)
            is DoWhileBlock -> doWhileBlock(blocks[i].blockType as DoWhileBlock, variables, arrays,console)
            is PrintBlock -> out(blocks[i].blockType as PrintBlock, variables, arrays,console)
            is ForBlock -> forBlock(blocks[i].blockType as ForBlock, variables, arrays,console)
        }
    }
}

fun ifElseBlock(
    blockIf: IfBlock,
    blockElse: ElseBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>

) {
    if (blockConditions(blockIf.condition, variables, arrays,console)) {
        interpretator(blockIf.blocks, variables, arrays, console)
    } else {
        interpretator(blockElse.blocks, variables, arrays,console)
    }
}

fun forBlock(block: ForBlock, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>,    console: MutableList<String>
) {
    var range = block.range.split(",")
    var first = calculatePolishString(PolishString(range[0], variables, arrays).expression, variables, arrays)
    var second = calculatePolishString(PolishString(range[1], variables, arrays).expression, variables, arrays)
    var localMap = variables


    for (i in first..second) {

        localMap[block.variable] = i
        interpretator(block.blocks, localMap, arrays,console)
    }

}


fun blockConditions(
    condition: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>

): Boolean {
    var res: Boolean = false
    var arr = conditionBlock(condition, variables, arrays)

    if (arr[0] == "!=") {
        if (calculatePolishString(arr[1], variables, arrays) != calculatePolishString(arr[2], variables, arrays)
        ) {
            res = true
        }
    }
    if (arr[0] == "==") {
        if (calculatePolishString(arr[1], variables, arrays) == calculatePolishString(arr[2], variables, arrays)
        ) {
            res = true
        }
    }
    if (arr[0] == ">=") {
        if (calculatePolishString(arr[1], variables, arrays) >= calculatePolishString(arr[2], variables, arrays)
        ) {
            res = true
        }
    }
    if (arr[0] == "<=") {
        if (calculatePolishString(
                arr[1],
                variables, arrays
            ) <= calculatePolishString(arr[2], variables, arrays)
        ) {
            res = true
        }
    }
    if (arr[0] == "<") {
        if (calculatePolishString(
                arr[1],
                variables, arrays
            ) < calculatePolishString(arr[2], variables, arrays)
        ) {
            res = true
        }
    }
    if (arr[0] == ">") {
        if (calculatePolishString(
                arr[1],
                variables, arrays
            ) > calculatePolishString(arr[2], variables, arrays)
        ) {
            res = true
        }
    }

    return res
}

fun ifBlock(block: IfBlock, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>,    console: MutableList<String>
) {
    if (blockConditions(block.condition, variables, arrays,console)) {
        interpretator(block.blocks, variables, arrays,console)
    }
}

fun whileBlock(block: WhileBlock, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>,    console: MutableList<String>
) {
    while (blockConditions(block.condition, variables, arrays,console)) {
        interpretator(block.blocks, variables, arrays,console)
    }
}


fun doWhileBlock(
    block: DoWhileBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,    console: MutableList<String>

) {
    interpretator(block.blocks, variables, arrays,console)
    while (blockConditions(block.condition, variables, arrays,console)) {
        interpretator(block.blocks, variables, arrays,console)
    }
}

fun varBlock(block: VarBlock, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>,    console: MutableList<String>
) {
    if ("," in block.value && "[" in block.value && "]" in block.value) {
        var str = block.value.replace("[", "").replace("]", "")


        var d = str.split(",")
        var arrInt = mutableListOf<Int>()
        for (i in d) {
            arrInt.add(i.toInt())
        }
        arrays[block.name] = arrInt
    } else if ("[" in block.name && "]" in block.name) {

        var str = block.name.split("[")
        var name = str[0]
        var index = str[1].replace("]", "")
        var ind = calculatePolishString(PolishString(index, variables, arrays).expression, variables, arrays)
        arrays[name]?.set(
            ind,
            calculatePolishString(PolishString(block.value, variables, arrays).expression, variables, arrays)
        )
    } else {
        variables[block.name] =
            calculatePolishString(PolishString(block.value, variables, arrays).expression, variables, arrays)
    }
}


fun out(block: PrintBlock, variables: MutableMap<String, Int>, arrays: MutableMap<String, MutableList<Int>>, console: MutableList<String>
) {
    Log.d("asas", calculatePolishString(PolishString(block.value, variables, arrays).expression, variables, arrays).toString())
}
