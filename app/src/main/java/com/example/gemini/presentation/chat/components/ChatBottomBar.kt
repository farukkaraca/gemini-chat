package com.example.gemini.presentation.chat.components

import androidx.compose.runtime.Composable
import com.example.gemini.model.MainState
import com.example.gemini.presentation.components.InputArea

@Composable
fun ChatBottomBar(
    state: MainState,
    onClickSend: (String) -> Unit,
    onClickSelectImage: () -> Unit,
    onClickDeleteImage: (Int) -> Unit
) {
    InputArea(
        state = state,
        onClickSend = { input ->
            onClickSend(input)
        },

        onClickSelectImage = {
            onClickSelectImage()
        },

        onClickDeleteImage = {
            onClickDeleteImage(it)
        }
    )
}