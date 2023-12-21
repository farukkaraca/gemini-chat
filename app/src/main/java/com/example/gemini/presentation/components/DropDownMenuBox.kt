package com.example.gemini.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    text: String = "Select",
    options: List<String>,
    errorText: String? = null,
    onSelectedChange: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(fontSize = 14.sp),
                leadingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.6f
                    ),
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.6f
                    ),
                    unfocusedIndicatorColor = if (errorText == null)
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.3f
                        )
                    else MaterialTheme.colorScheme.error,
                ),
                supportingText = {
                    if (errorText != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorText,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.End
                        )
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item
                            )
                        },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onSelectedChange(item)
                        }
                    )
                }
            }
        }
    }
}