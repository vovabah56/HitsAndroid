package com.example.myapp.interpretator

import com.example.myapp.model.*
import com.example.myapplication.polish.PolishString
import com.example.myapplication.polish.calculatePolishString


fun interpretator(blocks: MutableList<Block>, variables: MutableMap<String, Int>) {
    for(block in blocks){
        when(block.blockType){
            is VarBlock -> varBlock(block.blockType as VarBlock, variables)
            is IfBlock -> {ifBlock(block.blockType as IfBlock, variables)}

            is PrintBlock -> out(block.blockType as PrintBlock, variables)
        }
    }
}


fun blockConditions(condition: String, variables: MutableMap<String, Int>): Boolean {
    var res: Boolean = false
    var arr = conditionBlock(condition, variables)

    if (arr[0] == "!=") {
        if (calculatePolishString(arr[1], variables) != calculatePolishString(arr[2], variables)
        ) {
            res = true
        }
    }
    if (arr[0] == "==") {
        if (calculatePolishString(arr[1], variables) == calculatePolishString(arr[2],variables)
        ) {
            res = true
        }
    }
    if (arr[0] == ">=") {
        if (calculatePolishString(arr[1],variables) >= calculatePolishString(arr[2],variables)
        ) {
            res = true
        }
    }
    if (arr[0] == "<=") {
        if (calculatePolishString(
                arr[1],
                variables
            ) <= calculatePolishString(arr[2],variables)
        ) {
            res = true
        }
    }
    if (arr[0] == "<") {
        if (calculatePolishString(
                arr[1],
                variables
            ) < calculatePolishString(arr[2],variables)
        ) {
            res = true
        }
    }
    if (arr[0] == ">") {
        if (calculatePolishString(
                arr[1],
                variables
            ) > calculatePolishString(arr[2],variables)
        ) {
            res = true
        }
    }

    return res
}

fun ifBlock(block: IfBlock, variables: MutableMap<String, Int>) {
    if(blockConditions(block.condition,variables)){
        interpretator(block.blocks, variables)
    }
}


fun varBlock(block: VarBlock, variables: MutableMap<String, Int>) {
    variables[block.name] = calculatePolishString(PolishString(block.value, variables).expression, variables)
}


fun out(block: PrintBlock, variables: MutableMap<String, Int>){
    println(calculatePolishString(PolishString(block.value, variables).expression, variables))
}
