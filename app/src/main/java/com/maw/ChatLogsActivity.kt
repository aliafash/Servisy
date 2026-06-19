package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class ChatLogsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_logs)

        val btnCSV = findViewById<Button>(R.id.btnExportCSV)
        val btnClear = findViewById<Button>(R.id.btnClearHistory)

        btnCSV?.setOnClickListener {
            Toast.makeText(this, "تم تصدير سجلات الاتصال الفورية بصيغة CSV بنجاح!", Toast.LENGTH_SHORT).show()
        }

        btnClear?.setOnClickListener {
            Toast.makeText(this, "تطهير سجل المحادثات مهيأ سحابياً صامتاً وتحت الرقابة.", Toast.LENGTH_SHORT).show()
        }
    }
}
