package com.example.pa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

fun loadBitmap(context: Context, path: String): Bitmap? {
    return if (path.startsWith("android.resource://")) {
        // 从res/raw目录加载
        val uri = Uri.parse(path)
        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
    } else {
        // 从外部存储加载
        BitmapFactory.decodeFile(path)
    }
}