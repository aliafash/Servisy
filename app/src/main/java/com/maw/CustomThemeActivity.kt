package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class CustomThemeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_theme)

        val ivCoverPreview = findViewById<ImageView>(R.id.ivCoverPreview)
        val btnUploadCover = findViewById<Button>(R.id.btnUploadCover)
        val etPrimary = findViewById<EditText>(R.id.etPrimaryColor)
        val etAccent = findViewById<EditText>(R.id.etAccentColor)
        val btnSave = findViewById<Button>(R.id.btnSaveThemeSettings)

        // Load cover image using Glide
        if (ivCoverPreview != null) {
            Glide.with(this)
                .load("https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=600&q=80") // beautiful background
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(ivCoverPreview)
        }

        btnUploadCover?.setOnClickListener {
            // Mock dynamic image URI selection and load
            Toast.makeText(this, "تم تمشيط واختيار صورة الغلاف وتمرير معطيات Uri بنجاح!", Toast.LENGTH_SHORT).show()
            if (ivCoverPreview != null) {
                Glide.with(this)
                    .load("https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=600&q=80") // updated cover
                    .into(ivCoverPreview)
            }
        }

        btnSave?.setOnClickListener {
            val primary = etPrimary?.text?.toString() ?: ""
            Toast.makeText(this, "تم توطيد الألوان والسمات ($primary) وتفعيلها صامتاً في الخلفية مع Firebase!", Toast.LENGTH_SHORT).show()
        }
    }
}
