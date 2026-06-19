package com.yourpackage.name

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maw.R
import java.util.UUID

class CategoryManagementActivity : AppCompatActivity() {

    private lateinit var adapter: CategoryAdapter
    private var categoryList = mutableListOf<Category>()
    private var selectedUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_management)

        val etName = findViewById<EditText>(R.id.et_cat_name)
        val btnPick = findViewById<Button>(R.id.btn_pick_icon)
        val tvPath = findViewById<TextView>(R.id.tv_icon_path)
        val btnAdd = findViewById<Button>(R.id.btn_add_category)
        val rv = findViewById<RecyclerView>(R.id.rv_categories)

        // Seed with standard Arabic categories
        categoryList.add(Category("plumbing", "سباكة وصيانة منازل", "Plumbing", "🔧", 1))
        categoryList.add(Category("electricity", "كهرباء وتمديدات", "Electricity", "⚡", 2))
        categoryList.add(Category("carpentry", "نجارة وفك وتركيب", "Carpentry", "🔨", 3))

        adapter = CategoryAdapter(categoryList) { item ->
            // Delete action with Confirmation
            categoryList.remove(item)
            adapter.updateList(categoryList.toList())
            Toast.makeText(this, "تم حذف القسم ${item.nameAr} بنجاح! 🗑️", Toast.LENGTH_SHORT).show()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // File Picker via ActivityResultContracts
        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedUri = it
                tvPath.text = "تم اختيار الملف: ${uri.lastPathSegment}"
                Toast.makeText(this, "تم اختيار الأيقونة المهنية بنجاح! 🎨", Toast.LENGTH_SHORT).show()
            }
        }

        btnPick.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            if (name.isBlank()) {
                Toast.makeText(this, "يرجى كتابة اسم القسم أولاً! ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newId = "cat_" + UUID.randomUUID().toString().substring(0, 5)
            val newCat = Category(
                id = newId,
                nameAr = name,
                nameEn = "Subcategory",
                iconUrl = selectedUri?.toString() ?: "📁",
                order = categoryList.size + 1
            )

            categoryList.add(newCat)
            adapter.updateList(categoryList.toList())

            etName.setText("")
            selectedUri = null
            tvPath.text = "لم يتم اختيار ملف"
            Toast.makeText(this, "تمت إضافة وتحديث شجرة الفئات بنجاح دون وميض! 🚀🌟", Toast.LENGTH_SHORT).show()
        }
    }
}
