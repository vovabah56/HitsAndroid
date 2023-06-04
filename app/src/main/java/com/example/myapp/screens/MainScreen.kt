package com.example.myapp.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.model.Block
import com.example.myapp.model.LogData
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navigationBarItems = remember { NavigationBarItems.values() }
    val blocksList: MutableList<Block> = remember { mutableStateListOf() }
    val logList: MutableList<LogData> = remember { mutableStateListOf() }
    val selectedIndex = remember { mutableStateOf(0) }


    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex.value,
                modifier = Modifier
                    .padding(16.dp)
                    .height(64.dp),
                cornerRadius = shapeCornerRadius(34.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = if (selectedIndex.value == 0) colorResource(id = R.color.gray_200)
                else MaterialTheme.colorScheme.secondary,
                ballColor = if (selectedIndex.value == 0) colorResource(id = R.color.gray_200)
                else MaterialTheme.colorScheme.secondary,
            ) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable { selectedIndex.value = item.ordinal },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Botton Bar Icon",
                            tint = if (selectedIndex.value == item.ordinal) colorResource(id = R.color.white)
                            else colorResource(id = R.color.gray_400)
                        )
                    }
                }
            }
        }) {
        AnimatedVisibility(
            visible = selectedIndex.value == 0,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Home(blocksList, logList, selectedIndex)
        }
        AnimatedVisibility(
            visible = selectedIndex.value == 1,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.fillMaxWidth()
        ) {
            ConsoleScreen(logList)
            BotSheet(logList)


        }
    }
}


enum class NavigationBarItems(val icon: Int) {
    Home(R.drawable.code1),
    Console(R.drawable.terminal_fill),
}