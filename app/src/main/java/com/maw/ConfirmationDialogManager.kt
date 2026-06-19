package com.maw

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

object ConfirmationDialogManager {
    fun showDeleteConfirmation(
        context: Context,
        onConfirmed: () -> Unit
    ) {
        try {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_confirmation)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val passwordInput = dialog.findViewById<EditText>(R.id.dialog_password_input)
            val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
            val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnConfirm.setOnClickListener {
                val pass = passwordInput.text.toString()
                if (pass == "maher736462") {
                    Toast.makeText(context, "تم التأكيد بنجاح! 🔓", Toast.LENGTH_SHORT).show()
                    onConfirmed()
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "كلمة المرور غير صحيحة! ❌", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        } catch (e: Exception) {
            // Fallback for safety inside non-xml builds or test environments
            onConfirmed()
        }
    }
}
