package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
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

@Composable
fun ViewIntBlock(block:IntBlock) {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    Log.d("qwe","qwqe")
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(50.dp)

            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
                .background(Color.Blue)
                .border(
                    width = 2.dp,
                    color = Color.Black
                ),
            contentAlignment = Alignment.Center
        ) {
            Row{
                Text(text = block.name, color = Color.Red )
                Text(text = "Value: ${block.value.toString()}", color = Color.Red )
            }

        }
    }
}


@Composable
fun ViewAdditionBlock(block:AdditionBlock) {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    Log.d("qwe","qwqe")
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(50.dp)

            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp)
                .background(Color.Blue)
                .border(
                    width = 2.dp,
                    color = Color.Black
                ),
            contentAlignment = Alignment.Center
        ) {
            Row{
                Column() {
                    Text(text = "${block.firstNumber}" , color = Color.Red )
                    Text(text = "${block.secondNumber}" , color = Color.Red )

                }

                Text(text = "  aa  ${block.execute()}", color = Color.White)
            }

        }
    }
}
