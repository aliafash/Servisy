package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotificationManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_management)

        val etTitle = findViewById<EditText>(R.id.et_notify_title)
        val etBody = findViewById<EditText>(R.id.et_notify_body)
        val btnBroadcast = findViewById<Button>(R.id.btn_broadcast_push)

        btnBroadcast.setOnClickListener {
            val title = etTitle.text.toString()
            val body = etBody.text.toString()

            if (title.isBlank() || body.isBlank()) {
                Toast.makeText(this, "يرجى تعبئة العنوان والمحتوى أولاً! ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulating cloud-broadcast
            Toast.makeText(this, "تم بث الإشعار بنجاح لكافة الأجهزة باليمن! 📡📢", Toast.LENGTH_LONG).show()
            etTitle.setText("")
            etBody.setText("")
            finish()
        }
    }
}
