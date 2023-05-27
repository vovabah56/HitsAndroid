package com.example.myapp.blocks

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.example.myapp.screens.ButtonSelectionScreen
import com.example.myapp.R
import com.example.myapp.screens.clickHandler
import com.example.myapp.model.Block
import com.example.myapp.model.ElseBlock

@ExperimentalAnimationApi
@Composable
fun DrawElseBlock(block: Block, blocksList: MutableList<Block>, shiftBlock: Boolean) {
    val blockId = if (!shiftBlock) {
        R.drawable.block
    } else {
        R.drawable.under_block
    }
    val blockType = block.blockType as ElseBlock
    val newBlocksList: MutableList<Block> = blockType.blocks
    Box(
        Modifier
            .offset(y = 110.dp)
            .height(105.dp + 110.dp * newBlocksList.size)
            .padding(start = 26.dp)
    ) { OnShiftBlockCreate(newBlocksList) }

    Box(contentAlignment = Alignment.CenterStart) {

        Image(
            painter = painterResource(id = blockId),
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .padding(start = 44.dp, bottom = 90.dp),
            contentAlignment = Alignment.TopCenter
        ) { Text("else block", color = Color.White, textAlign = TextAlign.Center) }

        Row(
            modifier = Modifier
                .padding(start = 0.dp, bottom = 10.dp)
        ) {
            // todo update on delete...
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
                            listOfBlocks = blockType.blocks
                        )
                    })
                }

            }
        }
    }
}