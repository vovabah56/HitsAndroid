package com.example.myapplication

import android.graphics.Paint.Style
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {

    private var blockList = mutableListOf<Any>(
        IntBlock("block 1"),
        IntBlock("block 2"),
        IntBlock("block 3"),
        IntBlock("block 4"),
        IntBlock("block 5"),
        IntBlock("block 6"),
        AdditionBlock(2, 4),

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AddBlockAdd(blockList) {
                }


            }
        }
    }
}


@Composable
fun BlockList(blockList: MutableList<Any>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        blockList.forEach { block ->
            when (block) {
                is AdditionBlock -> ViewAdditionBlock(block = block)
                is IntBlock -> ViewIntBlock(block = block)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBlockAdd(
    blockList: MutableList<Any>,
    onAddBlock: () -> Unit
) {
    var newBlockText = remember { mutableStateOf("") }
    var newBlockValue = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = newBlockText.value,
            onValueChange = { newBlockText.value = it },
            label = { Text("Enter block name") },
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = newBlockValue.value,
            onValueChange = { newBlockValue.value = it },
            label = { Text("Enter block type") },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {

                blockList.add(IntBlock(newBlockText.value, newBlockValue.value.toInt()))
                newBlockText.value = ""
                onAddBlock()

            },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text("Add block")
        }
    }
    var leftOperand = remember { mutableStateOf("") }
    var rightOperand = remember { mutableStateOf("") }
    var a = remember {
        mutableStateOf(0)
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = leftOperand.value,
            onValueChange = { leftOperand.value = it },
            label = { Text("Left operand") },
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = rightOperand.value,
            onValueChange = { rightOperand.value = it },
            label = { Text("Right operand") },
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {
                val left = leftOperand.value.toIntOrNull()
                val right = rightOperand.value.toIntOrNull()
                if (left != null && right != null) {
                    blockList.add(AdditionBlock(left, right))
                    leftOperand.value = ""
                    rightOperand.value = ""
                    onAddBlock()
                }
            },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text("Add block")
        }
    }
    BlockList(blockList = blockList)
}