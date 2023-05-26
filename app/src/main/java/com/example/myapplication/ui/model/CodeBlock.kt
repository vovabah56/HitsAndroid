package com.example.myapplication.model

data class CodeBlock(
    val id: Int,
    var name: String,
    var value: String
) {
    companion object {
        var ID = 0
    }
}



