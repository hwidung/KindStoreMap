package com.konkuk.kindmap.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun Bitmap.saveBitmapToCache(
    context: Context,
    fileName: String = "wepli_${System.currentTimeMillis()}",
    onSuccess: (Uri, String) -> Unit,
    onFailure: () -> Unit,
) {
    val filename = "$fileName.png"
    val file = File(context.cacheDir, filename)

    try {
        FileOutputStream(file).use { outputStream ->
            val success = this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            if (success) {
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                onSuccess(uri, file.absolutePath)
            } else {
                onFailure()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onFailure()
    }
}
