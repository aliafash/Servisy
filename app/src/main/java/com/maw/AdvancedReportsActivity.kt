package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdvancedReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_reports)

        val btnWeek = findViewById<Button>(R.id.btn_filter_week)
        val btnMonth = findViewById<Button>(R.id.btn_filter_month)
        val btnYear = findViewById<Button>(R.id.btn_filter_year)
        val tvStats = findViewById<TextView>(R.id.tv_report_stats)

        btnWeek.setOnClickListener {
            tvStats.text = "إجمالي الحجوزات النشطة هذا الأسبوع: ٢٤ عملية حجز\nنسبة الاستجابة الفورية: ٩٦٪\nأكثر الأقسام طلباً: السباكة والكهرباء"
            Toast.makeText(this, "تمت تصفية التقارير للفترة الأسبوعية بنجاح! 📈", Toast.LENGTH_SHORT).show()
        }

        btnMonth.setOnClickListener {
            tvStats.text = "إجمالي الحجوزات النشطة هذا الشهر: ٨7 عملية حجز\nنسبة الاستجابة الفورية للمهنيين: ٩٤٪\nأكثر المحافظات نشاطاً بالدليل: العاصمة صنعاء"
            Toast.makeText(this, "تمت تصفية التقارير للفترة الشهرية بنجاح! 📈🗓️", Toast.LENGTH_SHORT).show()
        }

        btnYear.setOnClickListener {
            tvStats.text = "إجمالي الحجوزات السنوية: ١,٢٤٠ عملية حجز\nمعدل رضاء العملاء العام: ٤.٨ / ٥.٠\nإجمالي الكوادر المسجلة النشطة: ٤2٠ فني مهني"
            Toast.makeText(this, "تمت تصفية التقارير للفترة السنوية بنجاح! 📈🌟", Toast.LENGTH_SHORT).show()
        }
    }
}
