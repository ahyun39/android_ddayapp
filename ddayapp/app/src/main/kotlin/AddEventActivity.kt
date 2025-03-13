package com.example.ddayapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import java.time.LocalDate
import java.util.Locale

class AddEventActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        dbHelper = DatabaseHelper(this)

        val etTitle: EditText = findViewById(R.id.etTitle)
        val etDate: EditText = findViewById(R.id.etDate)
        val btnSave: Button = findViewById(R.id.btnSave)

        etDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("날짜를 선택하세요")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance().apply { timeInMillis = selection }
                val selectedDate = String.format(
                    Locale.KOREA,
                    "%04d-%02d-%02d",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                etDate.setText(selectedDate)
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val dateString = etDate.text.toString()

            if (title.isNotEmpty() && dateString.isNotEmpty()) {
                // 날짜 문자열을 LocalDate 객체로 변환
                val dateParts = dateString.split("-")
                val date = LocalDate.of(
                    dateParts[0].toInt(),
                    dateParts[1].toInt(),
                    dateParts[2].toInt()
                )

                // Event 객체 생성 후 insertEvent에 전달
                val event = Event(id = 0, title = title, date = date)
                dbHelper.insertEvent(event)
                finish()
            }
        }
    }
}