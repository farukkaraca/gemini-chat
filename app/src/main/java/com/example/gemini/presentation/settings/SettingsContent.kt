package com.example.gemini.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.gemini.presentation.components.DropdownMenuBox

@Composable
fun SettingsContent(paddingValues: PaddingValues,
                    keyValue: String,
                    onKeyValueChange: (String) -> Unit,
                    modelNameList: List<String>,
                    onSelectChange: (String) -> Unit,
                    selectedModel: String
                    ) {
    var apiKeyVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = keyValue,
                onValueChange = {
                    onKeyValueChange(it)
                },
                label = {
                    Text(text = "Enter api key")
                },
                visualTransformation = if (apiKeyVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    val icon = if (apiKeyVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(
                        onClick = {
                            apiKeyVisible = !apiKeyVisible
                        }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                        )
                    }
                })

            Spacer(modifier = Modifier.height(5.dp))

            DropdownMenuBox(
                options = modelNameList,
                text = selectedModel,
                onSelectedChange = {
                    onSelectChange(it)
                })
        }

        val uriHandler = LocalUriHandler.current

        ClickableText(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = annotatedLinkString,
            onClick = {
                annotatedLinkString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            },
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}