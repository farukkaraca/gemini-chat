package com.example.gemini.presentation.chat.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(
    onClickSettingIcon: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Gemini Assistant",
                fontSize = 18.sp
            )
        }, actions = {
            IconButton(
                onClick = {
                    onClickSettingIcon()
                }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            }
        })
}