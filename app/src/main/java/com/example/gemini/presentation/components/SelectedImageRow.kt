package com.example.gemini.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun SelectedImagesRow(
    modifier: Modifier,
    imageList: List<Bitmap>,
    onClickDeleteImage: (index: Int) -> Unit
) {
    Box(
        modifier = modifier
            .height(120.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyRow(
            modifier = modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(
                    vertical = 5.dp,
                    horizontal = 20.dp
                ),
            state = rememberLazyListState()
        ) {
            itemsIndexed(imageList) { index, imageBitmap ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(120.dp)
                ) {
                    IconButton(
                        modifier = Modifier
                            .align(
                                alignment = Alignment.TopEnd
                            ),
                        onClick = {
                            onClickDeleteImage(imageList.indexOf(imageBitmap))
                        }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }

                    Image(
                        modifier = Modifier
                            .padding(
                                top = 30.dp,
                                end = 30.dp
                            )
                            .clip(RoundedCornerShape(10.dp)),
                        bitmap = imageBitmap.asImageBitmap(),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}