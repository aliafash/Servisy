package com.maw

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

data class LocalBooking(
    var id: String,
    var clientName: String,
    var serviceRequired: String,
    var preferredTime: String,
    var notes: String,
    var status: String = "قيد الانتظار 🕒"
)

class BookingAdapter(
    private var list: List<LocalBooking>,
    private val context: Context,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val client: TextView = view.findViewById(R.id.tv_booking_client)
        val service: TextView = view.findViewById(R.id.tv_booking_service)
        val time: TextView = view.findViewById(R.id.tv_booking_time)
        val notes: TextView = view.findViewById(R.id.tv_booking_notes)
        val status: TextView = view.findViewById(R.id.tv_booking_status)
        val btnAccept: Button = view.findViewById(R.id.btn_accept_booking)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel_booking)
        val btnEdit: Button = view.findViewById(R.id.btn_edit_booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val item = list[position]
        holder.client.text = "العميل: ${item.clientName}"
        holder.service.text = "مطلب الخدمة: ${item.serviceRequired}"
        holder.time.text = "التوقيت المقترح: ${item.preferredTime}"
        holder.notes.text = "ملاحظات إضافية: ${item.notes}"
        holder.status.text = item.status

        holder.btnAccept.setOnClickListener {
            item.status = "مقبول بنجاح 🟢"
            notifyItemChanged(position)
            Toast.makeText(context, "تم قبول طلب حجز العميل ${item.clientName}! ✅", Toast.LENGTH_SHORT).show()
        }

        holder.btnCancel.setOnClickListener {
            item.status = "مرفوض ومغلق 🔴"
            notifyItemChanged(position)
            Toast.makeText(context, "تم رفض وإلغاء طلب الحجز! ❌", Toast.LENGTH_SHORT).show()
        }

        holder.btnEdit.setOnClickListener {
            // Edit 3 custom client input fields
            showEditDialog(item, position)
        }
    }

    override fun getItemCount(): Int = list.size

    private fun showEditDialog(item: LocalBooking, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("✏️ تعديل بيانات الحجز للعميل")

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val etClient = EditText(context).apply {
            hint = "اسم العميل"
            setText(item.clientName)
        }
        val etService = EditText(context).apply {
            hint = "الخدمة المطلوبة"
            setText(item.serviceRequired)
        }
        val etTime = EditText(context).apply {
            hint = "التوقيت المناسب"
            setText(item.preferredTime)
        }

        layout.addView(etClient)
        layout.addView(etService)
        layout.addView(etTime)
        builder.setView(layout)

        builder.setPositiveButton("حفظ التعديلات 💾") { dialog, _ ->
            item.clientName = etClient.text.toString()
            item.serviceRequired = etService.text.toString()
            item.preferredTime = etTime.text.toString()
            notifyItemChanged(position)
            Toast.makeText(context, "تم تحديث وتعديل حقول الحجز بنجاح! ⚡", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("إلغاء") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}
