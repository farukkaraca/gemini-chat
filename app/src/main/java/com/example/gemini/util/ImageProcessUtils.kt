package com.example.gemini.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun Bitmap.resizeImage(): Bitmap {
    var width = this.width
    var height = this.height

    val max = 500

    if (width > height) {
        // landscape
        val ratio = width / max
        width = max
        height /= ratio
    } else if (height > width) {
        // portrait
        val ratio = height / max
        height = max
        width /= ratio
    } else {
        // square
        height = max
        width = max
    }

    return Bitmap.createScaledBitmap(this, width, height, false)
}

fun Uri.uriToBitmap(cr: ContentResolver): Bitmap {
    return when {
        Build.VERSION.SDK_INT < 28 -> {
            MediaStore.Images.Media.getBitmap(
                cr,
                this
            )
        }

        else -> {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(cr, this))
        }
    }
}
