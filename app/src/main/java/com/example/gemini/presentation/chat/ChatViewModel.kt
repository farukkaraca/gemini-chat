package com.example.gemini.presentation.chat

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemini.model.MainState
import com.example.gemini.model.Message
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

typealias isCompleted = Boolean

class ChatViewModel : ViewModel() {
    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private var generativeModel: GenerativeModel? = null

    fun setKeyAndModelName(key: String, modelName: String) {
        _mainState.update {
            it.copy(modelName = modelName)
        }

        generativeModel = GenerativeModel(
            modelName = modelName,
            apiKey = key
        )
    }

    private fun setLoading(b: Boolean) {
        _mainState.update {
            it.copy(isLoading = b)
        }
    }

    fun setActive(iA: Boolean) {
        _mainState.update {
            it.copy(isActive = iA)
        }
    }

    fun setCanSelectImage(iA: Boolean) {
        _mainState.update {
            it.copy(canSelectImage = iA)
        }
    }


    fun addNewMessage(message: Message) {
        _mainState.update {
            it.messageList.add(0, message)
            it.copy(messageList = it.messageList)
        }
    }

    fun addNewImage(imageBitmap: Bitmap) {
        _mainState.update {

            val tempList = arrayListOf<Bitmap>()
            tempList.addAll(it.imageList)
            tempList.add(imageBitmap)

            it.copy(imageList = tempList)
        }
    }

    fun deleteImage(index: Int) {
        _mainState.update {

            val tempList = arrayListOf<Bitmap>()
            tempList.addAll(it.imageList)
            tempList.removeAt(index)

            it.copy(imageList = tempList)
        }
    }

    fun clearImages() {
        _mainState.update {
            it.copy(imageList = arrayListOf<Bitmap>())
        }
    }

    suspend fun sendMessage(input: String) = flow<isCompleted> {
        setLoading(true)

        val inputContent = content {
            text(input)
        }
        val job = generateContentJob(inputContent)
        job.join()

        emit(true)
        setLoading(false)
    }


    suspend fun sendMessageWithImage(input: String, imageList: List<Bitmap>) = flow<isCompleted> {
        setLoading(true)

        val inputContent = content {
            for (image in imageList) {
                image(image)
            }
            text(input)
        }

        val job = generateContentJob(inputContent)
        job.join()

        emit(true)
        setLoading(false)
    }


    private fun generateContentJob(inputContent: Content): Job {
        val job = viewModelScope.launch {
            try {
                val response = generativeModel!!.generateContent(inputContent)
                response.text?.let {
                    addNewMessage(Message(response.text!!, isSent = false))
                }
            } catch (e: Exception) {
                addNewMessage(Message(e.message.toString(), isError = true, isSent = false))
            }
        }
        return job
    }
}