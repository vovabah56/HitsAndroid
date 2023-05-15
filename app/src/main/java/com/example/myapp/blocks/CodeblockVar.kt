package com.example.myapp

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeBlockVar(codeBlock: CodeBlock) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.Transparent,
        modifier = Modifier
    ) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.add_var),
                contentDescription = null,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
            ) {
                var variable = remember {
                    mutableStateOf("")
                }
                if (codeBlock.name != "") {
                    variable.value = codeBlock.name
                }
                Box(modifier = Modifier.size(100.dp, 50.dp)) {
                    TextField(
                        value = variable.value,
                        onValueChange = {
                            variable.value = it
                            codeBlock.name = it
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
                if (codeBlock.value != 0) {
                    varValue.value = codeBlock.value.toString()
                }
                Box(modifier = Modifier.size(100.dp, 50.dp)) {
                    TextField(
                        value = varValue.value,
                        onValueChange = {
                            varValue.value = it
                            codeBlock.value = it.toInt()
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        textStyle = TextStyle(textAlign = TextAlign.Center)

                    )
                }
                Box(
                    modifier = Modifier
                        .padding(start = 42.dp)

                ) {
                    TextButton(
                        onClick = {
                            Log.d(
                                "LOG",
                                "deleting block id: $codeBlock and $variable and $varValue"
                            )
                            variable.value = ""
                            varValue.value = ""
                            blockList.remove(codeBlock)
                        },
                    ) {
                        Text("×", fontSize = 20.sp, color = Color.White)
                    }
                }
            }
        }
    }
}