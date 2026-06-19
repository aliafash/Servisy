package com.maw

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import coil.load
import java.io.ByteArrayInputStream

object ImageUtils {

    fun decodeBase64OrLoadUrl(imageView: ImageView, imageSource: String) {
        if (imageSource.startsWith("data:image/") || imageSource.length > 200) {
            try {
                val cleanString = if (imageSource.contains(",")) {
                    imageSource.substringAfter(",")
                } else {
                    imageSource
                }
                val decodedBytes = Base64.decode(cleanString, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                } else {
                    // Fallback to coil load if decode failed
                    imageView.load(imageSource)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Fail-safe load
                imageView.load(R.drawable.ic_launcher_foreground)
            }
        } else if (imageSource.startsWith("http://") || imageSource.startsWith("https://")) {
            // Elegant load using Coil (highly optimized, modern equivalent of Glide/Picasso)
            imageView.load(imageSource) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
            }
        } else {
            // Text emoji indicator fallback
            imageView.load(R.drawable.ic_launcher_foreground)
        }
    }
}
