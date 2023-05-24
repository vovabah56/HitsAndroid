package com.example.myapp.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R


data class CodeBlock(
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
    var name: String
)

data class IfBlock(
    var condition: String,
    var blocks: MutableList<CodeBlock>
)

data class ElseIfBlock(
    var condition: String,
    var blocks: MutableList<CodeBlock>
)

data class ElseBlock(
    var blocks: MutableList<CodeBlock>
)


data class ForBlock(
    var variable: String,
    var range: String,
    var blocks: MutableList<CodeBlock>
)

data class WhileBlock(
    var condition: String,
    var blocks: MutableList<CodeBlock>
)

data class DoWhileBlock(
    var condition: String,
    var blocks: MutableList<CodeBlock>
)


data class FunctionBlock(
    var name: String,
    var parameters: MutableList<String>,
    var blocks: MutableList<CodeBlock>
)

data class ReturnBlock(
    var value: String
)
