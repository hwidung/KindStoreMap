package com.konkuk.kindmap.ui.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

class ShareUtil(private val context: Context) {
    companion object {
        private const val IMAGE_TYPE_ALL = "image/*"
        private const val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"
    }

    fun shareToInstagramStory(
        stickerAssetUri: Uri,
        backgroundAssetUri: Uri? = null,
    ) {
        val activity = context as? Activity ?: return

        if (!isAppInstalled(INSTAGRAM_PACKAGE_NAME)) return

        val intent =
            Intent("com.instagram.share.ADD_TO_STORY").apply {
                setType(IMAGE_TYPE_ALL)
                putExtra("interactive_asset_uri", stickerAssetUri)
                backgroundAssetUri?.let { setDataAndType(it, IMAGE_TYPE_ALL) }
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

        activity.grantUriPermission(INSTAGRAM_PACKAGE_NAME, stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        backgroundAssetUri?.let {
            activity.grantUriPermission(INSTAGRAM_PACKAGE_NAME, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        activity.startActivity(intent)
    }

    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(context, "인스타그램을 설치해주세요.", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
