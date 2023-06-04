package com.example.myapp.screens

import android.annotation.SuppressLint
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.zIndex
import com.example.myapp.R
import com.example.myapp.blocks.BlockView
import com.example.myapp.interpretator.interpreter
import com.example.myapp.model.Block
import com.example.myapp.model.LogData
import com.example.myapp.model.SlideState
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun Home(
    blocksList: MutableList<Block>,
    logList: MutableList<LogData>,
    selectedIndex: MutableState<Int>
) {

    val slideStates = remember {
        mutableStateMapOf<Block, SlideState>().apply {
            blocksList.associateWith {
                SlideState.NONE
            }.also {
                putAll(it)
            }
        }
    }
    CodeScreenButtons(blocksList, logList, selectedIndex)
    ListView(
        blocksList = blocksList,
        slideStates = slideStates,
        updateSlideState = { blockList, slideState -> slideStates[blockList] = slideState },
        updateItemPosition = { currentIndex, destinationIndex ->
            val blockList = blocksList[currentIndex]
            blocksList.removeAt(currentIndex)
            blocksList.add(destinationIndex, blockList)
            slideStates.apply {
                blocksList.associateWith { SlideState.NONE }.also {
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
            .padding(top = 64.dp, bottom = 80.dp)
            .horizontalScroll(
                rememberScrollState()
            ),
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
                }
            }
        }
    }

}

@Composable
fun CodeScreenButtons(
    blocksList: MutableList<Block>,
    logList: MutableList<LogData>,
    selectedIndex: MutableState<Int>
) {
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


            androidx.compose.animation.AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Dialog(onDismissRequest = { expanded = !expanded }) {
                    (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.8f)
                    ButtonSelectionScreen(onButtonClick = { buttonType ->
                        expanded = !expanded
                        vibrator.vibrate(VibrationEffect.createOneShot(50, 10))
                        clickHandler(
                            buttonType = buttonType,
                            listOfBlocks = blocksList
                        )
                    })
                }

            }
        }

        IconButton(onClick = {
            Log.i("Start CLicked", "${logList.size}")
            if (logList.size > 1) {
                logList.add(LogData("--------------------------------------------------"))
            }
            selectedIndex.value = 1

            Thread {
                try {
                    interpreter(
                        blocksList,
                        mutableMapOf(),
                        mutableMapOf(),
                        logList
                    )
                } catch (e: Exception) {
                    logList.add(LogData(e.toString(), true))
                }
            }.start()
        }, Modifier.padding(8.dp)) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Запустить")
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BotSheet(logList: MutableList<LogData>) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    var inputValueName by remember { mutableStateOf("") }
    val inputValue = remember { mutableStateOf("") }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier.height(300.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Введите значение переменной $inputValueName",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                TextField(
                    value = inputValue.value,
                    onValueChange = { inputValue.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp, end = 32.dp, start = 32.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onSurface,
                        containerColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                Button(
                    onClick = {
                        logList.add(LogData("input $inputValueName is ${inputValue.value}"))
                        scope.launch {
                            sheetState.collapse()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 68.dp, end = 36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                ) {
                    Text("OK")
                }
            }

        },
        sheetBackgroundColor = colorResource(id = R.color.gray_200),
        sheetPeekHeight = 0.dp,
        contentColor = Color.Transparent,
        sheetContentColor = Color.Transparent,
        backgroundColor = Color.Transparent,
    ) {
        ConsoleScreen(consoleOutput = logList)
    }
    if (logList.isNotEmpty() && "getting value of " in logList.last().log) {
        inputValueName = logList.last().log.substringAfter("getting value of ")
        scope.launch {
            sheetState.expand()
        }
    }
}
