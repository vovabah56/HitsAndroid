package com.example.myapp.blocks


import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.example.myapp.screens.ButtonSelectionScreen
import com.example.myapp.R
import com.example.myapp.screens.clickHandler
import com.example.myapp.model.Block
import com.example.myapp.model.DoWhileBlock
import com.example.myapp.model.ElseBlock
import com.example.myapp.model.ElseIfBlock
import com.example.myapp.model.ForBlock
import com.example.myapp.model.FunctionBlock
import com.example.myapp.model.IfBlock
import com.example.myapp.model.WhileBlock


fun getNestedBlockCount(block: Block): Int {
    var count = 0

    when (block.blockType) {
        is IfBlock -> {
            val ifBlock = block.blockType as IfBlock
            count += ifBlock.blocks.size
            for (innerBlock in ifBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }

        is ElseIfBlock -> {
            val elseIfBlock = block.blockType as ElseIfBlock
            count += elseIfBlock.blocks.size
            for (innerBlock in elseIfBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }

        is ElseBlock -> {
            val elseBlock = block.blockType as ElseBlock
            count += elseBlock.blocks.size
            for (innerBlock in elseBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }

        is ForBlock -> {
            val forBlock = block.blockType as ForBlock
            count += forBlock.blocks.size
            for (innerBlock in forBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }

        is WhileBlock -> {
            val whileBlock = block.blockType as WhileBlock
            count += whileBlock.blocks.size
            for (innerBlock in whileBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }

        is DoWhileBlock -> {
            val doWhileBlock = block.blockType as DoWhileBlock
            count += doWhileBlock.blocks.size
            for (innerBlock in doWhileBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }

        is FunctionBlock -> {
            val functionBlock = block.blockType as FunctionBlock
            count += functionBlock.blocks.size
            for (innerBlock in functionBlock.blocks) {
                count += getNestedBlockCount(innerBlock)
            }
        }
    }
    Log.i("totalBlocks", count.toString())
    return count
}

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawIfBlock(
    block: Block,
    blocksList: MutableList<Block>,
    shiftBlock: Boolean,
    mainBLocks: MutableList<Block> = mutableListOf()
) {
    val blockId = if (!shiftBlock) {
        R.drawable.block
    } else {
        R.drawable.under_block
    }
    val blockType = block.blockType as IfBlock
    val newBlocksList: MutableList<Block> = blockType.blocks
    Box(
        Modifier
            .offset(y = 110.dp)
            .height(105.dp + 110.dp * newBlocksList.size)
            .padding(start = 26.dp)
    ) { OnShiftBlockCreate(newBlocksList, blocksList) }

    Box(contentAlignment = Alignment.CenterStart) {

        Image(
            painter = painterResource(id = blockId),
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .padding(start = 50.dp, bottom = 90.dp),
            contentAlignment = Alignment.TopCenter
        ) { Text("if block", color = Color.White, textAlign = TextAlign.Center) }

        Row(
            modifier = Modifier
                .padding(start = 0.dp, bottom = 10.dp)
        ) {
            val condition = remember {
                mutableStateOf("")
            }
            if (blockType.condition != "") {
                condition.value = blockType.condition
            }
            // todo Arrow drop down??
            var expanded by remember { mutableStateOf(false) }
            Box(contentAlignment = Alignment.CenterEnd) {
                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Добавить блок",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
            Box(modifier = Modifier.size(200.dp, 52.dp)) {
                TextField(
                    value = condition.value,
                    onValueChange = {
                        condition.value = it
                        blockType.condition = it
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
                            text = "condition",
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                            color = colorResource(id = R.color.gray_200)
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
                        clickHandler(
                            buttonType = buttonType,
                            listOfBlocks = newBlocksList
                        )
                    })
                }

            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun IfPrev() {
    Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.padding(horizontal = 16.dp)) {
//        drawIfBlock(block = Block(0, IfBlock("", mutableListOf())), blocksList = mutableListOf())
    }
}