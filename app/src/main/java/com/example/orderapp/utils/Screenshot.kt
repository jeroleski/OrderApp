package com.example.orderapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Screenshot(activity: Activity) {
    init {
        val rootView = activity.window.decorView.rootView
        val screenshotBitmap = takeScreenshot(rootView)
        val imageFile = saveBitmap(screenshotBitmap)
        if (imageFile != null) {
            addImageToGallery(imageFile, activity)
        }
    }

    private fun takeScreenshot(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap): File? {
        val fileName = "screenshot_${System.currentTimeMillis()}.png"
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(directory, fileName)

        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            outputStream?.close()
        }
        return imageFile
    }

    private fun addImageToGallery(file: File, context: Context) {
        MediaStore.Images.Media.insertImage(
            context.contentResolver,
            file.absolutePath,
            file.name,
            null
        )
    }
}
