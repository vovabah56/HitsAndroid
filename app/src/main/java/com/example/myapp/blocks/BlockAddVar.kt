package com.example.myapp.blocks

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.model.Block
import com.example.myapp.model.VarBlock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawVariableBlock(block: Block, blocksList: MutableList<Block>, shiftBlock: Boolean) {
    val blockType = block.blockType as VarBlock
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
                .padding(start = 49.dp, bottom = 90.dp),
            contentAlignment = Alignment.TopCenter
        ) { Text("variable", color = Color.White, textAlign = TextAlign.Center) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 10.dp)
        ) {
            val variable = rememberSaveable {
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
                        Color.Black,
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
            val varValue = remember {
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
                            color = colorResource(id = R.color.gray_200)
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
}

//@ExperimentalAnimationApi
//@Preview
//@Composable
//fun CardPreview() {
//    Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.padding(horizontal = 16.dp)) {
//        drawVariableBlock(
//            block = Block(
//                0,
//                VarBlock("", "")
//            ), blocksList = mutableListOf()
//        )
//    }
//
//}