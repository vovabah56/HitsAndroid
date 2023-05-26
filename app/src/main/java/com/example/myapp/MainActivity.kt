package com.example.myapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.blocks.BlockView
import com.example.myapp.interpretator.interpretator
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
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlin.math.log


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
//            Home()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navigationBarItems = remember { NavigationBarItems.values() }
    val blocksList: MutableList<Block> = remember { mutableStateListOf(*startBlocks) }
    val logList: MutableList<String> = remember { mutableStateListOf("") }
    var selectedIndex by remember { mutableStateOf(0) }


    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex,
                modifier = Modifier
                    .padding(16.dp)
                    .height(64.dp),
                cornerRadius = shapeCornerRadius(34.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = MaterialTheme.colorScheme.primary,
                ballColor = MaterialTheme.colorScheme.primary
            ) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable { selectedIndex = item.ordinal },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Botton Bar Icon",
                            tint = if (selectedIndex == item.ordinal) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }
            }
        }) {
        AnimatedVisibility(
            visible = selectedIndex == 0,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Home(blocksList)
        }
        AnimatedVisibility(
            visible = selectedIndex == 1,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.fillMaxWidth()
        ) {
            ConsoleScreen(logList)
        }
    }
}


enum class NavigationBarItems(val icon: Int) {
    Home(R.drawable.code1),
    Console(R.drawable.terminal_fill),
}

val startBlocks = arrayOf(
    Block(0, VarBlock("", "")),
)

@ExperimentalAnimationApi
@Composable
fun Home(blocksList: MutableList<Block>) {

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

//todo select block full screen menu with title
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
fun CodeScreenButtons(blocksList: MutableList<Block>) {
    var lastId = 0
    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    var expanded by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Box() {
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
                    (LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0.8f)
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
            for (block in blocksList) {
                Log.d("Block print", "$block")
            }
            var variables = mutableMapOf<String, Int>()
            var arrays = mutableMapOf<String, MutableList<Int>>()
            var cons = mutableListOf<String>()
            interpretator(blocksList, variables, arrays, cons)
//                             Log.d("BLOCKS PRINT", "${blocksList.forEach()}")
        }, Modifier.padding(8.dp)) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Запустить")
        }
    }

}

// сделать в основном листе с тем же id что и у иф вложенные блоки