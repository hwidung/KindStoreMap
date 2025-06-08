package com.konkuk.kindmap.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

object SharedPrepare {
    fun prepareShare(
        context: Context,
        bitmap: Bitmap,
        backgroundUri: Uri? = null,
        onFailure: (() -> Unit) = {},
    ) {
        bitmap.saveBitmapToCache(
            context = context,
            fileName = "wepli_${System.currentTimeMillis()}",
            onSuccess = { uri, _ ->
                ShareUtil(context).shareToInstagramStory(
                    stickerAssetUri = uri,
                    backgroundAssetUri = backgroundUri,
                )
            },
            onFailure = onFailure,
        )
    }
}
