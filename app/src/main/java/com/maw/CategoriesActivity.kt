package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class CategoriesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val etCatName = findViewById<EditText>(R.id.etCategoryNameAr)
        val ivIconPreview = findViewById<ImageView>(R.id.ivCategoryIconPreview)
        val btnPick = findViewById<Button>(R.id.btnPickCategoryIcon)

        // Load starting preview icon with Glide
        if (ivIconPreview != null) {
            Glide.with(this)
                .load("https://img.icons8.com/color/96/000000/worker.png") // default craftsman icon
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(ivIconPreview)
        }

        btnPick?.setOnClickListener {
            Toast.makeText(this, "فتح موجه الملفات لاختيار أيقونة مخصصة للقسم الفني وتمرير Uri...", Toast.LENGTH_SHORT).show()
            // Simulating image picked and updated via Glide
            if (ivIconPreview != null) {
                Glide.with(this)
                    .load("https://img.icons8.com/color/96/000000/maintenance.png") // updated maintenance icon
                    .into(ivIconPreview)
            }
        }
    }
}
