package com.example.myapp.blocks

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.model.Block
import com.example.myapp.model.SlideState

@ExperimentalAnimationApi
@Composable
fun ShiftListView(
    mainBlocksList: MutableList<Block>,
    blocksList: MutableList<Block>,
    slideStates: Map<Block, SlideState>,
    updateSlideState: (block: Block, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(blocksList.size) { index ->
            val block = blocksList.getOrNull(index)
            if (block != null) {
                key(block) {
                    mainBlocksList.add(Block(10000, ""))
                    mainBlocksList.remove(Block(10000, ""))
                    val slideState = slideStates[block] ?: SlideState.NONE
                    ShiftBlockView(
                        mainBlocks = mainBlocksList,
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


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnShiftBlockCreate(
    blocksList: MutableList<Block>,
    mainBlocksList: MutableList<Block> = mutableListOf()
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
    ShiftListView(
        mainBlocksList = mainBlocksList,
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