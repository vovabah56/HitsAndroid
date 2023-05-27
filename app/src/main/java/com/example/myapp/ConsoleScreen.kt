package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsoleScreen(consoleOutput: MutableList<String>) {
    val isDialogOpen = remember { mutableStateOf(false) }

    Surface(
        color = Color.DarkGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                items(consoleOutput) { outputLine ->
                    Text(
                        text = outputLine,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

    if (isDialogOpen.value) {
        drawDialog(isDialogOpen = isDialogOpen)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawDialog(isDialogOpen: MutableState<Boolean>) {
    val inputText = remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { isDialogOpen.value = false },
        title = { Text(text = "Enter a log") },
        text = {
            TextField(
                value = inputText.value,
                onValueChange = { inputText.value = it },
                modifier = Modifier.background(Color.White)
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    inputText.value = ""
                    isDialogOpen.value = false
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    isDialogOpen.value = false
                }
            ) {
            }
        }
    )
}

@Preview
@Composable
fun PreviewConsoleScreen() {
//    ConsoleScreen()
}
