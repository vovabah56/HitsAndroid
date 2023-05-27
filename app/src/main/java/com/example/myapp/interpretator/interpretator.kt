package com.example.myapp.interpretator

import android.util.Log
import com.example.myapp.model.*
import com.example.myapplication.polish.PolishString
import com.example.myapplication.polish.calculatePolishString


fun interpreter(
    blocks: MutableList<Block>,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>
) {
    val size = blocks.size
    for (i in 0 until size) {
        when (blocks[i].blockType) {
            is VarBlock -> varBlock(
                blocks[i].blockType as VarBlock, variables, arrays, console, errorConsole
            )

            is IfBlock -> {
                if (i < (size - 1) && blocks[i + 1].blockType is ElseBlock) {
                    ifElseBlock(
                        blocks[i].blockType as IfBlock,
                        blocks[i + 1].blockType as ElseBlock,
                        variables,
                        arrays,
                        console,
                        errorConsole
                    )
                } else {
                    ifBlock(
                        blocks[i].blockType as IfBlock, variables, arrays, console, errorConsole
                    )
                }
            }

            is WhileBlock -> whileBlock(
                blocks[i].blockType as WhileBlock, variables, arrays, console, errorConsole
            )

            is DoWhileBlock -> doWhileBlock(
                blocks[i].blockType as DoWhileBlock, variables, arrays, console, errorConsole
            )

            is PrintBlock -> out(
                blocks[i].blockType as PrintBlock, variables, arrays, console, errorConsole
            )

            is ForBlock -> forBlock(
                blocks[i].blockType as ForBlock, variables, arrays, console, errorConsole
            )

            is InputBlock -> inputBlock(
                blocks[i].blockType as InputBlock, variables, arrays, console, errorConsole
            )
        }
    }
}

fun inputBlock(
    block: InputBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>
) {
    variables[block.name] = zaglushka()
}

fun zaglushka(): Int {
    return 0
}

fun ifElseBlock(
    blockIf: IfBlock,
    blockElse: ElseBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>

) {
    if (blockConditions(blockIf.condition, variables, arrays, console, errorConsole)) {
        interpreter(blockIf.blocks, variables, arrays, console, errorConsole)
    } else {
        interpreter(blockElse.blocks, variables, arrays, console, errorConsole)
    }
}

fun forBlock(
    block: ForBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>
) {
    val range = block.range.split(",")
    val first = calculatePolishString(
        PolishString(range[0], variables, arrays, errorConsole).expression,
        variables,
        arrays,
        errorConsole
    )
    val second = calculatePolishString(
        PolishString(range[1], variables, arrays, errorConsole).expression,
        variables,
        arrays,
        errorConsole
    )
    var localMap = variables


    for (i in first..second) {

        localMap[block.variable] = i
        interpreter(block.blocks, localMap, arrays, console, errorConsole)
    }

}


fun blockConditions(
    condition: String,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>

): Boolean {
    var res = false
    val arr = conditionBlock(condition, variables, arrays, errorConsole)

    if (arr[0] == "!=") {
        if (calculatePolishString(arr[1], variables, arrays, errorConsole) != calculatePolishString(
                arr[2], variables, arrays, errorConsole
            )
        ) {
            res = true
        }
    }
    if (arr[0] == "==") {
        if (calculatePolishString(arr[1], variables, arrays, errorConsole) == calculatePolishString(
                arr[2], variables, arrays, errorConsole
            )
        ) {
            res = true
        }
    }
    if (arr[0] == ">=") {
        if (calculatePolishString(arr[1], variables, arrays, errorConsole) >= calculatePolishString(
                arr[2], variables, arrays, errorConsole
            )
        ) {
            res = true
        }
    }
    if (arr[0] == "<=") {
        if (calculatePolishString(
                arr[1], variables, arrays, errorConsole
            ) <= calculatePolishString(
                arr[2], variables, arrays, errorConsole
            )
        ) {
            res = true
        }
    }
    if (arr[0] == "<") {
        if (calculatePolishString(
                arr[1], variables, arrays, errorConsole
            ) < calculatePolishString(
                arr[2], variables, arrays, errorConsole
            )
        ) {
            res = true
        }
    }
    if (arr[0] == ">") {
        if (calculatePolishString(
                arr[1], variables, arrays, errorConsole
            ) > calculatePolishString(arr[2], variables, arrays, errorConsole)
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
    console: MutableList<String>,
    errorConsole: MutableList<String>
) {
    if (blockConditions(block.condition, variables, arrays, console, errorConsole)) {
        interpreter(block.blocks, variables, arrays, console, errorConsole)
    }
}

fun whileBlock(
    block: WhileBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>
) {
    while (blockConditions(block.condition, variables, arrays, console, errorConsole)) {
        interpreter(block.blocks, variables, arrays, console, errorConsole)
    }
}


fun doWhileBlock(
    block: DoWhileBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>

) {
    interpreter(block.blocks, variables, arrays, console, errorConsole)
    while (blockConditions(block.condition, variables, arrays, console, errorConsole)) {
        interpreter(block.blocks, variables, arrays, console, errorConsole)
    }
}

fun varBlock(
    block: VarBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>
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
            PolishString(index, variables, arrays, errorConsole).expression,
            variables,
            arrays,
            errorConsole
        )
        arrays[name]?.set(
            ind, calculatePolishString(
                PolishString(block.value, variables, arrays, errorConsole).expression,
                variables,
                arrays,
                errorConsole
            )
        )
    } else {
        variables[block.name] = calculatePolishString(
            PolishString(block.value, variables, arrays, errorConsole).expression,
            variables,
            arrays,
            errorConsole
        )
    }
}


fun out(
    block: PrintBlock,
    variables: MutableMap<String, Int>,
    arrays: MutableMap<String, MutableList<Int>>,
    console: MutableList<String>,
    errorConsole: MutableList<String>
) {
    console.add(
        calculatePolishString(
            PolishString(block.value, variables, arrays, errorConsole).expression,
            variables,
            arrays,
            errorConsole
        ).toString()
    )
    Log.d(
        "asas", calculatePolishString(
            PolishString(block.value, variables, arrays, errorConsole).expression,
            variables,
            arrays,
            errorConsole
        ).toString()
    )
}
