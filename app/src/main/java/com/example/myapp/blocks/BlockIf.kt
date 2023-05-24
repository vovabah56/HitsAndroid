package com.example.myapp.blocks


import com.example.myapp.R
import com.example.myapp.dragToReorder

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.myapp.model.CodeBlock
import com.example.myapp.model.IfBlock
import com.example.myapp.model.PrintBlock
import com.example.myapp.model.SlideState


private val particlesStreamRadii = mutableListOf<Float>()
private var itemHeight = 0
private var particleRadius = 0f
private var slotItemDifference = 0f


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockIf(
    block: CodeBlock,
    slideState: SlideState,
    blocksList: MutableList<CodeBlock>,
    updateSlideState: (blockList: CodeBlock, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    block.blockType as PrintBlock
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
        Image(
            painter = painterResource(id = R.drawable.print),
            contentDescription = null,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 10.dp)
        ) {
            var outputValue = remember {
                mutableStateOf("")
            }
            if ((block.blockType as PrintBlock).value != "") {
                outputValue.value = (block.blockType as PrintBlock).value
            }
            Box(modifier = Modifier.size(231.dp, 52.dp)) {
                TextField(
                    value = outputValue.value,
                    onValueChange = {
                        outputValue.value = it
                        (block.blockType as PrintBlock).value = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "output in console",
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 42.dp)

            ) {
                TextButton(
                    onClick = {
                        blocksList.remove(block)
                    },
                ) {
                    Text("×", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun pre() {
    BlockIf(
        block = CodeBlock(0, IfBlock("", mutableListOf())),
        slideState = SlideState.NONE,
        blocksList = mutableListOf(),
        updateSlideState = { _, _ -> },
        updateItemPosition = { _, _ -> }
    )
}