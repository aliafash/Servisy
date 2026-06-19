package com.maw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookingManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_management)

        val rv = findViewById<RecyclerView>(R.id.rv_bookings)
        rv.layoutManager = LinearLayoutManager(this)

        val seedList = listOf(
            LocalBooking("1", "ماهر اليماني", "صيانة لوحة تكييف رئيسية", "غداً العاشرة صباحاً", "يرجى الحضور بالعدة الكلية"),
            LocalBooking("2", "أبو بكر كمال", "تمديدات شبكة كهرباء منزلية كاملة", "الأسبوع القادم السبت", "يرجى توفير أسلاك كورية"),
            LocalBooking("3", "محمد الحميري", "فحص وإصلاح بطارية طاقة شمسية", "اليوم الرابعة عصراً", "البطارية جافة عميقة")
        )

        val adapter = BookingAdapter(seedList, this) {}
        rv.adapter = adapter
    }
}
