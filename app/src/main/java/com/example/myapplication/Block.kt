package com.example.myapplication

open class Block(val type: String) {
    data class ActionBlock(var name: String, var value: Int = 0) : Block("action")
}
