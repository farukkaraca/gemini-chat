package com.example.gemini.presentation.settings

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

val annotatedLinkString: AnnotatedString = buildAnnotatedString {
    val str = "If you don't have one, click here to create your own API key"
    val startIndex = str.indexOf("here")
    val endIndex = startIndex + 4
    append(str)
    addStyle(
        style = SpanStyle(
            color = Color(0xFF57B1FA),
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline
        ), start = startIndex, end = endIndex
    )
    addStringAnnotation(
        tag = "URL",
        annotation = "https://makersuite.google.com/app/apikey",
        start = startIndex,
        end = endIndex
    )
}