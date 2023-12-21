package com.example.gemini.model

data class Message(
    val text: String,
    val isSent: Boolean,
    val isError: Boolean = false
)