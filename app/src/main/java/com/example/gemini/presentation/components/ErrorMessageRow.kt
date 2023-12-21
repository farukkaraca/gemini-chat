package com.example.gemini.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorMessageRow(
    messageText: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 5.dp, 50.dp, 5.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(20.dp),
            ),
        horizontalArrangement = Arrangement.Start
    ) {

        Icon(
            imageVector = Icons.Default.Info, contentDescription = null,
            tint = MaterialTheme.colorScheme.errorContainer
        )

        Box(
            modifier = Modifier
                .padding(start = 5.dp)
                .wrapContentWidth()
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(20.dp),
                ),
        ) {
            Text(
                modifier = Modifier.padding(13.dp),
                text = messageText,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}