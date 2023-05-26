package com.example.myapp.model


data class Block(
    val id: Int,
    var blockType: Any
)

data class VarBlock(
    var name: String,
    var value: String
)

data class ArrayBlock(
    var name: String,
    var type: String,
    var size: String
)

data class PrintBlock(
    var value: String
)

data class InputBlock(
    var value: String
)

data class IfBlock(
    var condition: String,
    var blocks: MutableList<Block>
)

data class ElseIfBlock(
    var condition: String,
    var blocks: MutableList<Block>
)

data class ElseBlock(
    var blocks: MutableList<Block>
)


data class ForBlock(
    var variable: String,
    var range: String,
    var blocks: MutableList<Block>
)

data class WhileBlock(
    var condition: String,
    var blocks: MutableList<Block>
)

data class DoWhileBlock(
    var condition: String,
    var blocks: MutableList<Block>
)


data class FunctionBlock(
    var name: String,
    var parameters: MutableList<String>,
    var blocks: MutableList<Block>
)

data class ReturnBlock(
    var value: String
)
