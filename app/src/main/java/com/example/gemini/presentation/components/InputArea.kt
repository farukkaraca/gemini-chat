package com.example.gemini.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.model.MainState

@Composable
fun InputArea(
    state: MainState,
    onClickSend: (value: String) -> Unit,
    onClickSelectImage: () -> Unit,
    onClickDeleteImage: (Int) -> Unit
) {
    var value by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        SelectedImagesRow(
            modifier = Modifier,
            imageList = state.imageList,
            onClickDeleteImage = {
                onClickDeleteImage(it)
            }
        )

        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .padding(13.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp)
                    .heightIn(
                        min = 50.dp,
                        max = 60.dp
                    ),
                shape = RoundedCornerShape(25.dp),
                value = value,
                onValueChange = {
                    value = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp
                ),
                placeholder = {
                    Text(
                        text = "Enter here",
                        fontSize = 16.sp
                    )
                },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (value.isNotBlank()) {
                            onClickSend(value)
                            value = ""
                        }
                    }),

                leadingIcon = {
                    IconButton(
                        onClick = {
                            if (state.canSelectImage) {
                                onClickSelectImage()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            tint = if (state.canSelectImage) LocalContentColor.current
                            else LocalContentColor.current.copy(
                                alpha = 0.7f
                            )
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.width(7.dp))

            IconButton(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.primary
                    ),
                onClick = {
                    if (!state.isLoading && state.isActive) {
                        if (value.isNotBlank()) {
                            onClickSend(value)
                            value = ""
                        }
                    }
                },
            ) {
                Icon(
                    modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = if (!state.isLoading && state.isActive) Color.White
                    else Color.White.copy(
                        alpha = 0.5f
                    ),
                )
            }
        }
    }
}