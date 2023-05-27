package com.example.myapp.blocks

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.model.Block
import com.example.myapp.model.InputBlock

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawInputBlock(block: Block, blocksList: MutableList<Block>, shiftBlock: Boolean) {
    val blockType = block.blockType as InputBlock
    val blockId = if (!shiftBlock) {
        R.drawable.block
    } else {
        R.drawable.under_block
    }
    Box(contentAlignment = Alignment.CenterStart) {

        Image(
            painter = painterResource(id = blockId),
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .padding(start = 41.dp, bottom = 90.dp),
            contentAlignment = Alignment.TopCenter
        ) { Text("input block", color = Color.White, textAlign = TextAlign.Center) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 10.dp)
        ) {
            val outputValue = remember {
                mutableStateOf("")
            }
            if (blockType.name != "") {
                outputValue.value = blockType.name
            }
            Box(modifier = Modifier.size(231.dp, 52.dp)) {
                TextField(
                    value = outputValue.value,
                    onValueChange = {
                        outputValue.value = it
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
                            text = "input text",
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
        }
    }
}