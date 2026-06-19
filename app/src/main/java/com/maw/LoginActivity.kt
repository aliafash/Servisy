package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvOfflineHint = findViewById<TextView>(R.id.tv_offline_hint)
        val etUser = findViewById<TextInputEditText>(R.id.et_login_username)
        val etPass = findViewById<TextInputEditText>(R.id.et_login_password)
        val btnLogin = findViewById<Button>(R.id.btn_submit_login)

        // Offline masking logic:
        val isNetworkConnected = checkInternetConnectivity()
        if (!isNetworkConnected) {
            tvOfflineHint.text = "رقم الدعم الفني دون إنترنت: wam777644 📞"
        } else {
            tvOfflineHint.text = "رقم الدعم الفني المباشر بالإنترنت: +967 777644670 📞"
        }

        btnLogin.setOnClickListener {
            val userValue = etUser.text.toString()
            val passValue = etPass.text.toString()
            if (userValue == "WAM2026" && (passValue == "maher736462" || passValue == "maher--736462")) {
                Toast.makeText(this, "أهلاً بك يا أدمن ماهر! تم تسجيل الدخول بنجاح! 🔐🔓", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "اسم المستخدم أو كلمة المرور غير صحيحة! ❌", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInternetConnectivity(): Boolean {
        // Mock method to check active network connection or fallback status
        return false
    }
}
