package com.maw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class MonitoredChat(
    val id: String,
    val senderName: String,
    val lastMessage: String,
    val timestamp: String
)

class ChatMonitoringActivity : AppCompatActivity() {

    private lateinit var adapter: ChatMonitorAdapter
    private val chatList = mutableListOf<MonitoredChat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_monitoring)

        val rv = findViewById<RecyclerView>(R.id.rv_monitored_chats)
        rv.layoutManager = LinearLayoutManager(this)

        chatList.addAll(
            listOf(
                MonitoredChat("1", "كهربائي صنعاء (جميل)", "هل تباع الكابلات بضمانة حقيقية؟", "منذ ٥ دقائق"),
                MonitoredChat("2", "فني تمليط (يحيى)", "نلتقي غداً بموقع العمل لتحديد السعر النهائي", "منذ ١٠ دقائق"),
                MonitoredChat("3", "طبيب عيون (أمين)", "العيادة مفتوحة من الساعة الرابعة عصراً", "منذ ٢٢ دقيقة")
            )
        )

        adapter = ChatMonitorAdapter(chatList) { chatItem ->
            // Use Password-Protected Confirmation Dialog from STEP 1 for deleting!
            ConfirmationDialogManager.showDeleteConfirmation(this) {
                chatList.remove(chatItem)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "تم مسح وحذف قناة المحادثة رصدياً! 🗑️🛡️", Toast.LENGTH_SHORT).show()
            }
        }
        rv.adapter = adapter
    }
}

class ChatMonitorAdapter(
    private val list: List<MonitoredChat>,
    private val onDeleteClicked: (MonitoredChat) -> Unit
) : RecyclerView.Adapter<ChatMonitorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sender: TextView = view.findViewById(R.id.tv_chat_sender)
        val lastMsg: TextView = view.findViewById(R.id.tv_chat_last_msg)
        val time: TextView = view.findViewById(R.id.tv_chat_time)
        val btnDelete: Button = view.findViewById(R.id.btn_delete_chat)
        val btnWarn: Button = view.findViewById(R.id.btn_warn_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_monitored_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.sender.text = "قناة: ${item.senderName}"
        holder.lastMsg.text = "آخر رسالة: ${item.lastMessage}"
        holder.time.text = "التوقيت: ${item.timestamp}"

        holder.btnWarn.setOnClickListener {
            Toast.makeText(holder.itemView.context, "تم إرسال إشعار تحذيري صارم للفني ${item.senderName}! ⚠️", Toast.LENGTH_SHORT).show()
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClicked(item)
        }
    }

    override fun getItemCount(): Int = list.size
}
