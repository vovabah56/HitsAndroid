package com.example.myapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.myapp.R
import com.example.myapp.model.LogData

@Composable
fun ConsoleScreen(consoleOutput: MutableList<LogData>) {
    Surface(
        color = colorResource(id = R.color.gray_dark),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Text(
                text = "Console",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = {
                    consoleOutput.clear()
                },
            ) {
                Icon(painterResource(id = R.drawable.eraser_line), "", tint = Color.White)
            }
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 24.dp, top = 64.dp, bottom = 80.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                items(consoleOutput) { outputLine ->
                    if (outputLine.isError) {
                        Text(
                            text = outputLine.log,
                            color = colorResource(id = R.color.red_error),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Text(
                            text = outputLine.log,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewConsoleScreen() {
//    ConsoleScreen()
}
