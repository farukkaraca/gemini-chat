package com.example.gemini.presentation.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.gemini.presentation.settings.components.SettingsTopAppBar
import com.example.gemini.presentation.ui.theme.GeminiTheme
import com.example.gemini.util.Constants.MODEL_NAME_GEMINI_PRO
import com.example.gemini.util.Constants.MODEL_NAME_GEMINI_PRO_VISION
import com.example.gemini.util.SharedPrefs

class SettingsActivity : ComponentActivity() {
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = SharedPrefs(this)

        val key = sharedPrefs.getKey()
        val modelName = sharedPrefs.getModelName()

        val modelNameList = listOf(MODEL_NAME_GEMINI_PRO, MODEL_NAME_GEMINI_PRO_VISION)

        setContent {

            val context = LocalContext.current
            var keyValue by remember { mutableStateOf(key ?: "") }
            val focusManager = LocalFocusManager.current
            var selectedModel = modelName ?: modelNameList[0]

            GeminiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            SettingsTopAppBar(
                                onClickBackIcon = {
                                    onBackPressedDispatcher.onBackPressed()
                                }
                            )
                        },
                        bottomBar = {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 10.dp
                                    ),
                                onClick = {

                                    sharedPrefs.saveKey(keyValue)
                                    sharedPrefs.saveModelName(selectedModel)

                                    focusManager.clearFocus(true)

                                }) {
                                Text(text = "Save Changes")
                            }
                        }) { paddingValues ->

                        SettingsContent(
                            paddingValues = paddingValues,
                            keyValue = keyValue,
                            onKeyValueChange = {
                                keyValue = it
                            },
                            modelNameList = modelNameList,
                            onSelectChange = {
                                selectedModel = it
                            },
                            selectedModel = selectedModel
                        )
                    }
                }
            }
        }
    }
}