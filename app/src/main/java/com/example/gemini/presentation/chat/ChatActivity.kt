package com.example.gemini.presentation.chat

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gemini.model.Message
import com.example.gemini.presentation.chat.components.ChatBottomBar
import com.example.gemini.presentation.chat.components.ChatContent
import com.example.gemini.presentation.chat.components.ChatTopAppBar
import com.example.gemini.presentation.settings.SettingsActivity
import com.example.gemini.presentation.ui.theme.GeminiTheme
import com.example.gemini.util.Constants.MODEL_NAME_GEMINI_PRO
import com.example.gemini.util.Constants.MODEL_NAME_GEMINI_PRO_VISION
import com.example.gemini.util.SharedPrefs
import com.example.gemini.util.resizeImage
import com.example.gemini.util.uriToBitmap
import kotlinx.coroutines.launch

class ChatActivity : ComponentActivity() {
    private lateinit var viewModel: ChatViewModel
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = SharedPrefs(this)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        setContent {
            val rootView = findViewById<FrameLayout>(android.R.id.content).rootView
            rootView.setBackgroundColor(if (isSystemInDarkTheme()) Color.BLACK else Color.WHITE)

            val context = LocalContext.current
            val scrollState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            val state = viewModel.mainState.collectAsState()

            val galleryLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.GetMultipleContents()
                ) { list ->
                    if (list.isNotEmpty()) {
                        val cr = context.contentResolver
                        for (image in list) {
                            val imageBitmap = image.uriToBitmap(cr)

                            if (imageBitmap.height > 1000 || imageBitmap.width > 1000) {
                                viewModel.addNewImage(imageBitmap.resizeImage())
                            } else {
                                viewModel.addNewImage(imageBitmap)
                            }
                        }
                    }
                }

            GeminiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            ChatTopAppBar(
                                onClickSettingIcon = {
                                    goToSettings()
                                }
                            )
                        },
                        bottomBar = {
                            ChatBottomBar(
                                state = state.value,
                                onClickSend = { input ->
                                    lifecycleScope.launch {
                                        viewModel.addNewMessage(
                                            Message(
                                                text = input,
                                                isSent = true
                                            )
                                        )
                                        if (state.value.modelName == MODEL_NAME_GEMINI_PRO) {
                                            viewModel.sendMessage(input).collect {

                                            }
                                        } else if (state.value.modelName == MODEL_NAME_GEMINI_PRO_VISION) {
                                            viewModel.sendMessageWithImage(
                                                input,
                                                state.value.imageList
                                            ).collect {

                                            }
                                        }
                                    }
                                },
                                onClickSelectImage = {
                                    galleryLauncher.launch("image/*")
                                },

                                onClickDeleteImage = {
                                    viewModel.deleteImage(it)
                                }
                            )
                        }
                    ) { paddingValues ->
                        ChatContent(
                            paddingValues = paddingValues,
                            scrollState = scrollState,
                            state = state.value
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val key = sharedPrefs.getKey()
        val modelName = sharedPrefs.getModelName()

        if (key.isNullOrBlank()) {
            viewModel.setActive(false)

            Toast.makeText(
                this,
                "Please set a valid Api Key by going to settings!",
                Toast.LENGTH_LONG
            ).show()

        } else {
            viewModel.setActive(true)
            viewModel.setKeyAndModelName(key = key, modelName = modelName ?: MODEL_NAME_GEMINI_PRO)

            if (modelName == MODEL_NAME_GEMINI_PRO) {
                viewModel.setCanSelectImage(false)
                viewModel.clearImages()

            } else if (modelName == MODEL_NAME_GEMINI_PRO_VISION) {
                viewModel.setCanSelectImage(true)
            }
        }
    }

    private fun goToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}