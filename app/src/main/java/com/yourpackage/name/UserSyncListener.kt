package com.yourpackage.name

import android.content.Context
import android.widget.Toast

class UserSyncListener(private val context: Context, private val onDataSynced: (String) -> Unit) {
    fun startRealtimeListen(userId: String) {
        // Simulates pushing instant changes to user devices
        onDataSynced("تم جلب وتحديث الفئات والمدن الحية بنجاح دون الحاجة لطلب تحديث! ⚡")
    }
}
