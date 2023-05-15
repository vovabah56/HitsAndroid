package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import kotlin.math.roundToInt


//https://developersbreach.com/drag-to-reorder-compose/

data class CodeBlock(
    val id: Int,
    var name: String,
    var value: Int
)

var blockList: MutableList<CodeBlock> = mutableStateListOf()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListView()
            CodeScreenButtons()
//            VerticalReorderGrid()
        }
    }
}


@Composable
fun CodeScreenButtons() {
    var lastId = 0
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = {
                if (blockList.size > 0) {
                    lastId = blockList.last().id + 1
                }
                blockList.add(CodeBlock(lastId, "", 0))
                Log.d("LOG", "Add block clicked $lastId")
            },
            Modifier.padding(8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить блок")
        }
        IconButton(onClick = {}, Modifier.padding(8.dp)) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Запустить")
        }
    }

}

@Composable
fun ListRow(model: CodeBlock) {
    val offsetX = remember {
        mutableStateOf(0f)
    }
    val offsetY = remember {
        mutableStateOf(0f)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .offset { IntOffset(x = offsetX.value.roundToInt(), y = offsetY.value.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX.value += dragAmount.x
                    offsetY.value += dragAmount.y
                    Log.d("LOG", "new positions: ${offsetX.value} ${offsetY.value}")
                }
            }
    ) {
        Box() { CodeBlockVar(model) }
    }
}


@Composable
fun ListView() {
    val state = rememberReorderableLazyListState(onMove = { from, to -> })
    val scrollState = rememberScrollState()
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Log.d("LOG", "Перерисовываю список")
        items(blockList, key = { block -> block.id }) { model ->
            ListRow(model = model)
        }
    }

}