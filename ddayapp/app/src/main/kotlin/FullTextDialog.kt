package com.example.ddayapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView

class FullTextDialog(context: Context, private val fullText: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_full_text)

        val tvFullText = findViewById<TextView>(R.id.tvFullText)
        tvFullText.text = fullText
    }
}