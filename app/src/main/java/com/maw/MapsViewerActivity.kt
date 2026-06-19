package com.maw

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MapsViewerActivity : AppCompatActivity() {

    private var selectedLat = 15.3694
    private var selectedLng = 44.1910

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_viewer)

        val tvDistance = findViewById<TextView>(R.id.tv_distance_value)
        val tvCoordinates = findViewById<TextView>(R.id.tv_coordinates)
        val btnDirections = findViewById<Button>(R.id.btn_directions)
        val btnGps = findViewById<Button>(R.id.btn_gps_refresh)
        val btnSave = findViewById<Button>(R.id.btn_save_latlng)

        // Calculate dynamic high-precision distance using Location.distanceBetween()
        fun updateDistanceDisplay() {
            val userLat = 15.3500 // Simulated current user position in Sana'a
            val userLng = 44.2000
            val results = FloatArray(1)
            
            Location.distanceBetween(userLat, userLng, selectedLat, selectedLng, results)
            val distanceInMeters = results[0]

            val distanceText = if (distanceInMeters >= 1000) {
                String.format(Locale("ar"), "المسافة المقدرة: %.2f كم 🚗", distanceInMeters / 1000f)
            } else {
                String.format(Locale("ar"), "المسافة المقدرة: %d متر 🚶", distanceInMeters.toInt())
            }

            tvDistance.text = distanceText
            tvCoordinates.text = String.format(Locale.US, "خط العرض: %.5f | خط الطول: %.5f", selectedLat, selectedLng)
        }

        updateDistanceDisplay()

        // Handle GPS update
        btnGps.setOnClickListener {
            selectedLat = 15.3694 + (Math.random() - 0.5) * 0.05
            selectedLng = 44.1910 + (Math.random() - 0.5) * 0.05
            updateDistanceDisplay()
            Toast.makeText(this, "تم تحديث موقع نظام الـ GPS بدقة عالية! 🛰️", Toast.LENGTH_SHORT).show()
        }

        // Save Coordinates
        btnSave.setOnClickListener {
            // Simulated Firestore GeoPoint persistence
            Toast.makeText(this, "تم حفظ الإحداثيات السحابية لمزود الخدمة بنجاح! 💾📍", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Implicit intent to google maps
        btnDirections.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q=$selectedLat,$selectedLng")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Browser fallback
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$selectedLat,$selectedLng"))
                startActivity(webIntent)
            }
        }
    }
}
