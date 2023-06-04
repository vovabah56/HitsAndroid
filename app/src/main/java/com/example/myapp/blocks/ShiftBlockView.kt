package com.example.myapp.blocks

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapp.R
import com.example.myapp.model.dragToReorder
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

private val particlesStreamRadii = mutableListOf<Float>()
private var itemHeight = 0
private var particleRadius = 0f
private var slotItemDifference = 0f

@ExperimentalAnimationApi
@Composable
fun ShiftBlockView(
    mainBlocks: MutableList<Block>,
    block: Block,
    slideState: SlideState,
    blocksList: MutableList<Block>,
    updateSlideState: (blockList: Block, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val itemHeightDp = dimensionResource(id = R.dimen.image_size)
    with(LocalDensity.current) {
        itemHeight = itemHeightDp.toPx().toInt()
        particleRadius = dimensionResource(id = R.dimen.particle_radius).toPx()
        if (particlesStreamRadii.isEmpty())
            particlesStreamRadii.addAll(arrayOf(6.dp.toPx(), 10.dp.toPx(), 14.dp.toPx()))
        slotItemDifference = 16.dp.toPx()
    }
    val verticalTranslation by animateIntAsState(
        targetValue = when (slideState) {
            SlideState.UP -> -itemHeight
            SlideState.DOWN -> itemHeight
            else -> 0
        },
    )
    val isDragged = remember { mutableStateOf(false) }
    val zIndex = if (isDragged.value) 1.0f else 0.0f

    val currentIndex = remember { mutableStateOf(0) }
    val destinationIndex = remember { mutableStateOf(0) }

    val isPlaced = remember { mutableStateOf(false) }
    LaunchedEffect(isPlaced.value) {
        if (isPlaced.value) {
            if (currentIndex.value != destinationIndex.value) {
                updateItemPosition(currentIndex.value, destinationIndex.value)
            }
            isPlaced.value = false
        }
    }
    Box(modifier = Modifier
        .padding(horizontal = 16.dp)
        .dragToReorder(
            block,
            blocksList,
            itemHeight,
            updateSlideState,
            isDraggedAfterLongPress = true,
            { isDragged.value = true },
            { cIndex, dIndex ->
                isDragged.value = false
                isPlaced.value = true
                currentIndex.value = cIndex
                destinationIndex.value = dIndex
            }
        )
        .offset { IntOffset(0, verticalTranslation) }
        .zIndex(zIndex),
        contentAlignment = Alignment.CenterStart

    ) {
        when (block.blockType) {
            is VarBlock -> DrawVariableBlock(block = block, blocksList = blocksList, true)
            is PrintBlock -> DrawPrintBlock(block = block, blocksList = blocksList, true)
            is IfBlock -> DrawIfBlock(block = block, blocksList = blocksList, true, mainBlocks)
            is ElseBlock -> DrawElseBlock(block = block, blocksList = blocksList, true)
            is ElseIfBlock -> DrawElseIfBlock(block = block, blocksList = blocksList, true)
            is WhileBlock -> DrawWhileBlock(block = block, blocksList = blocksList, true)
            is ForBlock -> DrawForBlock(block = block, blocksList = blocksList, true)
            is InputBlock -> DrawInputBlock(block = block, blocksList = blocksList, true)
            is DoWhileBlock -> DrawDoWhileBlock(block = block, blocksList = blocksList, true)
        }
    }
}