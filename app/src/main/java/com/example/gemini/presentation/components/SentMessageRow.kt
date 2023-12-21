package com.example.gemini.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SentMessageRow(
    messageText: String = "Message"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp, 5.dp, 20.dp, 5.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(20.dp),
            ),
        horizontalArrangement = Arrangement.End
    ) {

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp),
                ),
        ) {
            Text(
                modifier = Modifier.padding(13.dp),
                text = messageText,
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}