package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class NotificationsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val etTitle = findViewById<EditText>(R.id.etNotificationTitle)
        val etBody = findViewById<EditText>(R.id.etNotificationBody)
        val btnSend = findViewById<Button>(R.id.btnSendNotification)

        btnSend?.setOnClickListener {
            val titleText = etTitle?.text?.toString() ?: ""
            if (titleText.isNotBlank()) {
                Toast.makeText(this, "تم إرسال وبث الإشعار: $titleText بنجاح لجميع المستخدمين!", Toast.LENGTH_SHORT).show()
                etTitle?.setText("")
                etBody?.setText("")
            } else {
                Toast.makeText(this, "الرجاء كتابة عنوان الإشعار أولاً!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
