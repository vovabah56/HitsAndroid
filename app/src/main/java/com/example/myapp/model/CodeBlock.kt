package com.example.myapp.model


data class CodeBlock(
    val id: Int,
    var name: String,
    var value: Int
) {
    companion object {
        var ID = 0
    }
}

