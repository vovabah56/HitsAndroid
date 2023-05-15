package com.example.myapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    Scaffold { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockListItem() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row() {
            var textVar by remember { mutableStateOf("") }
            TextField(
                value = textVar, onValueChange = { textVar = it })
            Text(text = " = ")
            var textValue by remember { mutableStateOf("") }
            TextField(
                value = textValue, onValueChange = { textValue = it })
        }
    }
}