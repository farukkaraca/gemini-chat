package com.example.gemini.model

import android.graphics.Bitmap
import com.example.gemini.util.Constants.MODEL_NAME_GEMINI_PRO

data class MainState(
    val isLoading: Boolean = false,
    val messageList: MutableList<Message> = mutableListOf(),
    val isActive: Boolean = false,
    val modelName: String = MODEL_NAME_GEMINI_PRO,
    val canSelectImage: Boolean = false,
    val imageList: List<Bitmap> = listOf(),
)