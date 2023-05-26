package com.example.myapp

import android.annotation.SuppressLint
import android.util.Log
import android.widget.AutoCompleteTextView.OnDismissListener
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

enum class ButtonType {
    Variable,
    Print,
    Input,
    For,
    While,
    DoWhile,
    If,
    Else
}

enum class ButtonCategory {
    Variables,
    Loops,
    InputOutput,
    Conditions
}

data class ButtonItem(val type: ButtonType, val category: ButtonCategory)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ButtonSelectionScreen(
    onButtonClick: (ButtonType) -> Unit
) {
    val buttonItems = remember {
        listOf(
            ButtonItem(ButtonType.Variable, ButtonCategory.Variables),
            ButtonItem(ButtonType.Print, ButtonCategory.InputOutput),
            ButtonItem(ButtonType.Input, ButtonCategory.InputOutput),
            ButtonItem(ButtonType.For, ButtonCategory.Loops),
            ButtonItem(ButtonType.While, ButtonCategory.Loops),
            ButtonItem(ButtonType.DoWhile, ButtonCategory.Loops),
            ButtonItem(ButtonType.If, ButtonCategory.Conditions),
            ButtonItem(ButtonType.Else, ButtonCategory.Conditions)
        )
    }
    Scaffold(
        containerColor = Color.Transparent,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .animateContentSize()
                ) {
                    val categories = buttonItems.groupBy { it.category }
                    for ((category, items) in categories) {
                        stickyHeader {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.White
                            )
                        }
                        items(items) { buttonItem ->
                            Button(
                                onClick = { onButtonClick(buttonItem.type) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .padding(vertical = 8.dp)
                                    .clickable { }
                            ) {
                                Text(
                                    text = buttonItem.type.name,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

fun onClickHandler(block: ButtonType) {
    Log.i("SelectBlock", "Hello I'm handler and this is $block")
}

@Preview
@Composable
fun PreviewButtonSelectionScreen() {
//    ButtonSelectionScreen(onButtonClick = { button -> onClickHandler(button) })
}