package com.example.myapp

import android.os.Bundle
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.model.CodeBlock
import com.example.myapp.model.SlideState


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
    CodeBlock(0, "", 0),
    CodeBlock(1, "", 0),
    CodeBlock(2, "", 0)
)

@ExperimentalAnimationApi
@Composable
fun Home() {
    val blocksList: MutableList<CodeBlock> = remember { mutableStateListOf(*startBlocks) }
    val slideStates = remember {
        mutableStateMapOf<CodeBlock, SlideState>().apply {
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
    blocksList: MutableList<CodeBlock>,
    slideStates: Map<CodeBlock, SlideState>,
    updateSlideState: (shoesArticle: CodeBlock, slideState: SlideState) -> Unit,
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
                    CodeBlockVar(
                        block = block,
                        slideState = slideState,
                        blocksList = blocksList,
                        updateSlideState = updateSlideState,
                        updateItemPosition = updateItemPosition
                    )
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

@Composable
fun ListRow(
    block: CodeBlock,
    slideState: SlideState,
    blocksList: MutableList<CodeBlock>,
    updateSlideState: (blockList: CodeBlock, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Box() {

        }
    }
}


@Composable
fun CodeScreenButtons(blocksList: MutableList<CodeBlock>) {
    var lastId = 0
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = {
//                val newShoesArticles = mutableListOf<CodeBlock>()
//                CodeBlock.ID += 1
//                blocksList.add(CodeBlock(id=CodeBlock.ID))
                if (blocksList.size > 0) {
                    lastId = blocksList.last().id + 1
                }
                blocksList.add(CodeBlock(lastId, "", 0))
                Log.d("LOG", "Add block clicked $lastId")
            }, Modifier.padding(8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить блок")
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
