package com.maw

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class BookingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        val ivBookingPreview = findViewById<ImageView>(R.id.ivBookingPreview)
        if (ivBookingPreview != null) {
            Glide.with(this)
                .load("https://images.unsplash.com/photo-1582213782179-e0d53f98f2ca?auto=format&fit=crop&w=500&q=80") // Sample repair/team image
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivBookingPreview)
        }

        Toast.makeText(this, "لوحة الحجوزات المجدولة تحت مراجعة ومزامنة التطبيق المباشر.", Toast.LENGTH_SHORT).show()
    }
}
