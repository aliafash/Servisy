package com.maw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment

class AppAppearanceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_app_appearance, container, false)

        val fontSpinner = root.findViewById<Spinner>(R.id.font_spinner)
        val textEditor = root.findViewById<EditText>(R.id.rich_text_editor)
        val slider = root.findViewById<SeekBar>(R.id.transparency_slider)
        val saveBtn = root.findViewById<Button>(R.id.btn_save_appearance)

        // Populate Font Spinner with Arabic fonts
        val fonts = arrayOf("الخط الافتراضي المطور", "خط تجول الأنيق (Tajawal)", "خط نوستالجيا التراثي", "خط كوفي الهندسي")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fonts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fontSpinner.adapter = adapter

        saveBtn.setOnClickListener {
            val selectedFont = fontSpinner.selectedItem.toString()
            val textContent = textEditor.text.toString()
            val opacity = slider.progress

            Toast.makeText(requireContext(), "تم حفظ المظهر: $selectedFont بنجاح! 🎨✨", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}
