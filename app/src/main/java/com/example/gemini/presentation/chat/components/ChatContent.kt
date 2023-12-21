package com.example.gemini.presentation.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ChatBubble
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemini.model.MainState
import com.example.gemini.presentation.components.ErrorMessageRow
import com.example.gemini.presentation.components.LoadingAnimation
import com.example.gemini.presentation.components.ReceivedMessageRow
import com.example.gemini.presentation.components.SentMessageRow

@Composable
fun ChatContent(
    paddingValues: PaddingValues = PaddingValues(),
    scrollState: LazyListState = LazyListState(),
    state: MainState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        if (state.messageList.isEmpty() && state.imageList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.TwoTone.ChatBubble, contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Start Chat")
                }
            }
        }

        LazyColumn(
            state = scrollState,
            reverseLayout = true
        ) {

            itemsIndexed(state.messageList) { index, item ->
                if (state.isLoading) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(5.dp))
                        LoadingAnimation()
                    }
                }

                if (item.isSent) {
                    SentMessageRow(messageText = item.text.trim())
                } else if (item.isError) {
                    ErrorMessageRow(messageText = item.text.trim())
                } else {
                    ReceivedMessageRow(messageText = item.text.trim())
                }
            }
        }
    }
}
