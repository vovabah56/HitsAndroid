package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.model.Block
import com.example.myapp.model.VarBlock

private val particlesStreamRadii = mutableListOf<Float>()
private var itemHeight = 0
private var particleRadius = 0f
private var slotItemDifference = 0f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawVariableBlock(block: Block, blocksList: MutableList<Block>) {
    val blockType = block.blockType as VarBlock
    Image(
        painter = painterResource(id = R.drawable.add_var),
        contentDescription = null,
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 10.dp)
    ) {
        var variable = rememberSaveable {
            mutableStateOf("")
        }
        if (blockType.name != "") {
            variable.value = blockType.name
        }
        Box(modifier = Modifier.size(100.dp, 50.dp)) {
            TextField(
                value = variable.value,
                onValueChange = {
                    variable.value = it
                    blockType.name = it
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
                        text = "name",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )
        }

        Text(
            text = " = ",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
        var varValue = remember {
            mutableStateOf("")
        }
        if (blockType.value != "") {
            varValue.value = blockType.value
        }
        Box(modifier = Modifier.size(100.dp, 50.dp)) {
            TextField(
                value = varValue.value,
                onValueChange = {
                    varValue.value = it
                    try {
                        blockType.value = it
                    } catch (e: NumberFormatException) {
                        blockType.value = ""
                    }
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
                        text = "value",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                    )
                },
                textStyle = TextStyle(textAlign = TextAlign.Center)

            )
        }
        Box(
            modifier = Modifier
                .padding(start = 42.dp)

        ) {
            TextButton(
                onClick = {
                    variable.value = ""
                    varValue.value = ""
                    blocksList.remove(block)
                },
            ) {
                Text("×", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

//@ExperimentalAnimationApi
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun VariableBlock(
//    block: Block,
//    slideState: SlideState,
//    blocksList: MutableList<Block>,
//    updateSlideState: (blockList: Block, slideState: SlideState) -> Unit,
//    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
//) {
//    block.blockType as VarBlock
//    val itemHeightDp = dimensionResource(id = R.dimen.image_size)
//    with(LocalDensity.current) {
//        itemHeight = itemHeightDp.toPx().toInt()
//        particleRadius = dimensionResource(id = R.dimen.particle_radius).toPx()
//        if (particlesStreamRadii.isEmpty())
//            particlesStreamRadii.addAll(arrayOf(6.dp.toPx(), 10.dp.toPx(), 14.dp.toPx()))
//        slotItemDifference = 16.dp.toPx()
//    }
//    val verticalTranslation by animateIntAsState(
//        targetValue = when (slideState) {
//            SlideState.UP -> -itemHeight
//            SlideState.DOWN -> itemHeight
//            else -> 0
//        },
//    )
//    val isDragged = remember { mutableStateOf(false) }
//    val zIndex = if (isDragged.value) 1.0f else 0.0f
//
//    val currentIndex = remember { mutableStateOf(0) }
//    val destinationIndex = remember { mutableStateOf(0) }
//
//    val isPlaced = remember { mutableStateOf(false) }
//    LaunchedEffect(isPlaced.value) {
//        if (isPlaced.value) {
//            if (currentIndex.value != destinationIndex.value) {
//                updateItemPosition(currentIndex.value, destinationIndex.value)
//            }
//            isPlaced.value = false
//        }
//    }
//    Box(modifier = Modifier
//        .padding(horizontal = 16.dp)
//        .dragToReorder(
//            block,
//            blocksList,
//            itemHeight,
//            updateSlideState,
//            isDraggedAfterLongPress = true,
//            { isDragged.value = true },
//            { cIndex, dIndex ->
//                isDragged.value = false
//                isPlaced.value = true
//                currentIndex.value = cIndex
//                destinationIndex.value = dIndex
//            }
//        )
//        .offset { IntOffset(0, verticalTranslation) }
//        .zIndex(zIndex),
//        contentAlignment = Alignment.CenterStart
//
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.add_var),
//            contentDescription = null,
//        )
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .padding(start = 16.dp, bottom = 10.dp)
//        ) {
//            var variable = rememberSaveable {
//                mutableStateOf("")
//            }
//            if (block.name != "") {
//                variable.value = block.name
//            }
//            Box(modifier = Modifier.size(100.dp, 50.dp)) {
//                TextField(
//                    value = variable.value,
//                    onValueChange = {
//                        variable.value = it
//                        block.name = it
//                    },
//                    shape = RoundedCornerShape(20.dp),
//                    colors = TextFieldDefaults.textFieldColors(
//                        textColor = Color.Black,
//                        disabledTextColor = Color.Transparent,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent
//                    ),
//                    placeholder = {
//                        Text(
//                            text = "name",
//                            modifier = Modifier.fillMaxSize(),
//                            textAlign = TextAlign.Center,
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                    textStyle = TextStyle(textAlign = TextAlign.Center)
//                )
//            }
//
//            Text(
//                text = " = ",
//                fontSize = 30.sp,
//                textAlign = TextAlign.Center,
//                color = Color.White,
//            )
//            var varValue = remember {
//                mutableStateOf("")
//            }
//            if (block.value != "") {
//                varValue.value = block.value.toString()
//            }
//            Box(modifier = Modifier.size(100.dp, 50.dp)) {
//                TextField(
//                    value = varValue.value,
//                    onValueChange = {
//                        varValue.value = it
//                        try {
//                            block.value = it
//                        } catch (e: NumberFormatException) {
//                            block.value = ""
//                        }
//                    },
//                    shape = RoundedCornerShape(20.dp),
//                    colors = TextFieldDefaults.textFieldColors(
//                        textColor = Color.Black,
//                        disabledTextColor = Color.Transparent,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent
//                    ),
//                    placeholder = {
//                        Text(
//                            text = "value",
//                            modifier = Modifier.fillMaxSize(),
//                            textAlign = TextAlign.Center,
//                        )
//                    },
//                    textStyle = TextStyle(textAlign = TextAlign.Center)
//
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .padding(start = 42.dp)
//
//            ) {
//                TextButton(
//                    onClick = {
//                        variable.value = ""
//                        varValue.value = ""
//                        blocksList.remove(block)
//                    },
//                ) {
//                    Text("×", fontSize = 20.sp, color = Color.White)
//                }
//            }
//        }
//    }
//}

