package com.example.gemini.util

import android.content.Context
import com.example.gemini.util.Constants.API_KEY
import com.example.gemini.util.Constants.MODEL_NAME
import com.example.gemini.util.Constants.SHARED_PREF_NAME

class SharedPrefs(context: Context) {
    private val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getKey(): String? {
        return sharedPref.getString(API_KEY, null)
    }

    fun saveKey(key: String) {
        with(sharedPref.edit()) {
            putString(API_KEY, key)
            apply()
        }
    }

    fun getModelName(): String? {
        return sharedPref.getString(MODEL_NAME, null)
    }

    fun saveModelName(modelName: String) {
        with(sharedPref.edit()) {
            putString(MODEL_NAME, modelName)
            apply()
        }
    }
}