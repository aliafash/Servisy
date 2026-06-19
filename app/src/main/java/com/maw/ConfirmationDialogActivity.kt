package com.maw

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class ConfirmationDialogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_confirmation)

        val btnCancel = findViewById<Button>(R.id.btnCancelDelete)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmDelete)

        btnCancel?.setOnClickListener {
            Toast.makeText(this, "تم إلغاء عملية الحذف بأمان والتراجع.", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnConfirm?.setOnClickListener {
            Toast.makeText(this, "موافق! تم تأكيد الحذف وتطهير السجل فوريًا سحابياً صامتاً!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
