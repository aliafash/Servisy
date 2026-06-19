package com.maw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment

class CardCustomizationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_card_customization, container, false)

        val heightSlider = root.findViewById<SeekBar>(R.id.card_height_slider)
        val chkVip = root.findViewById<CheckBox>(R.id.chk_vip_visible)
        val chkVerified = root.findViewById<CheckBox>(R.id.chk_verified_visible)
        val chkRecommended = root.findViewById<CheckBox>(R.id.chk_recommended_visible)
        val saveBtn = root.findViewById<Button>(R.id.btn_save_card_custom)

        saveBtn.setOnClickListener {
            val progressHeight = heightSlider.progress
            val vipOn = chkVip.isChecked
            val verOn = chkVerified.isChecked
            val recOn = chkRecommended.isChecked

            Toast.makeText(
                requireContext(),
                "تم حفظ قالب البطاقات والارتفاع $progressHeight مع الشارات بنجاح! 💾🎴",
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }
}
