package com.example.myapp

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapp.model.Block
import com.example.myapp.model.DoWhileBlock
import com.example.myapp.model.ElseBlock
import com.example.myapp.model.ElseIfBlock
import com.example.myapp.model.ForBlock
import com.example.myapp.model.IfBlock
import com.example.myapp.model.InputBlock
import com.example.myapp.model.PrintBlock
import com.example.myapp.model.SlideState
import com.example.myapp.model.VarBlock
import com.example.myapp.model.WhileBlock


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Home()
        }
    }
}


val startBlocks = arrayOf(
    Block(0, VarBlock("", "")),
//    Block(2, VarBlock("", "")),
//    Block(3, VarBlock("", "")),
//    Block(0, PrintBlock("")),
//    Block(4, PrintBlock("")),
//    CodeBlock(2, PrintBlock(""))
)

@ExperimentalAnimationApi
@Composable
fun Home() {
    val blocksList: MutableList<Block> = remember { mutableStateListOf(*startBlocks) }
    val slideStates = remember {
        mutableStateMapOf<Block, SlideState>().apply {
            blocksList.map { blockList ->
                blockList to SlideState.NONE
            }.toMap().also {
                putAll(it)
            }
        }
    }
    CodeScreenButtons(blocksList)
    ListView(
        blocksList = blocksList,
        slideStates = slideStates,
        updateSlideState = { blockList, slideState -> slideStates[blockList] = slideState },
        updateItemPosition = { currentIndex, destinationIndex ->
            val blockList = blocksList[currentIndex]
            blocksList.removeAt(currentIndex)
            blocksList.add(destinationIndex, blockList)
            slideStates.apply {
                blocksList.map { blockList -> blockList to SlideState.NONE }.toMap().also {
                    putAll(it)
                }
            }
        }

    )

}

@ExperimentalAnimationApi
@Composable
fun ListView(
    blocksList: MutableList<Block>,
    slideStates: Map<Block, SlideState>,
    updateSlideState: (shoesArticle: Block, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
    ) {
        items(blocksList.size) { index ->
            val block = blocksList.getOrNull(index)
            if (block != null) {
                key(block) {
                    val slideState = slideStates[block] ?: SlideState.NONE
                    BlockView(
                        block = block,
                        slideState = slideState,
                        blocksList = blocksList,
                        updateSlideState = updateSlideState,
                        updateItemPosition = updateItemPosition
                    )


//                    }

//                    ListRow(
//                        block = block,
//                        slideState = slideState,
//                        blocksList = blocksList,
//                        updateSlideState = updateSlideState,
//                        updateItemPosition = updateItemPosition
//                    )
                }
            }
        }
    }

}

// todo fix add block with lastId ???A?DA?D?
@Composable
fun CodeScreenButtons(blocksList: MutableList<Block>) {
    var lastId = 0
    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    var expanded by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Box {
            IconButton(
                onClick = {
                    expanded = !expanded
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                }, Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить блок")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Variable") },
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                        if (blocksList.size > 0) {
                            lastId = blocksList.last().id + 1
                        }
                        blocksList.add(Block(lastId, VarBlock("", "")))
                    }
                )
                DropdownMenuItem(
                    text = { Text("Print") },
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                        if (blocksList.size > 0) {
                            lastId = blocksList.last().id + 1
                        }
                        blocksList.add(Block(lastId, PrintBlock("")))
                    }
                )
                DropdownMenuItem(text = { Text("Input") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, InputBlock("")))
                })
                DropdownMenuItem(text = { Text("If block") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, IfBlock("", mutableListOf())))
                })
                DropdownMenuItem(text = { Text("Elif block") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, ElseIfBlock("", mutableListOf())))
                })
                DropdownMenuItem(text = { Text("Else block") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, ElseBlock(mutableListOf())))
                })
                DropdownMenuItem(text = { Text("While") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, WhileBlock("", mutableListOf())))
                })
                DropdownMenuItem(text = { Text("Do While") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, DoWhileBlock("", mutableListOf())))
                })
                DropdownMenuItem(text = { Text("For") }, onClick = {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                    if (blocksList.size > 0) {
                        lastId = blocksList.last().id + 1
                    }
                    blocksList.add(Block(lastId, ForBlock("", "", mutableListOf())))
                })
            }
        }

        IconButton(onClick = {
            for (block in blocksList) {
                Log.d("Block print", "$block")
            }
//                             Log.d("BLOCKS PRINT", "${blocksList.forEach()}")
        }, Modifier.padding(8.dp)) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Запустить")
        }
    }

}
