package com.example.ddayapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditEventDialog(
    context: Context,
    private val event: Event,
    private val onEventUpdated: (Event) -> Unit
) : Dialog(context) {

    private val dbHelper = DatabaseHelper(context)

    private lateinit var etTitle: EditText

    private lateinit var etYear: EditText
    private lateinit var etMonth: EditText
    private lateinit var etDay: EditText

    private lateinit var sYear: EditText
    private lateinit var sMonth: EditText
    private lateinit var sDay: EditText

    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        etTitle = findViewById(R.id.etTitle)
        etYear = findViewById(R.id.etYear)
        etMonth = findViewById(R.id.etMonth)
        etDay = findViewById(R.id.etDay)
        sYear = findViewById(R.id.sYear)
        sMonth = findViewById(R.id.sMonth)
        sDay = findViewById(R.id.sDay)
        btnUpdate = findViewById(R.id.btnUpdate)

        etTitle.setSelectAllOnFocus(true)

        // 기존 데이터 불러오기
        etTitle.setText(event.title)
        val dateParts = event.date.toString().split("-")
        etYear.setText(dateParts[0])
        etMonth.setText(dateParts[1])
        etDay.setText(dateParts[2])

        val sdateParts = event.sdate.toString().split("-")
        sYear.setText(sdateParts[0])
        sMonth.setText(sdateParts[1])
        sDay.setText(sdateParts[2])

        btnUpdate.setOnClickListener {
            val newTitle = etTitle.text.toString().trim()

            val year = etYear.text.toString().trim()
            val month = etMonth.text.toString().trim()
            val day = etDay.text.toString().trim()

            val syear = sYear.text.toString().trim()
            val smonth = sMonth.text.toString().trim()
            val sday = sDay.text.toString().trim()

            if (newTitle.isEmpty() || year.isEmpty() || month.isEmpty() || day.isEmpty() || syear.isEmpty() || smonth.isEmpty() || sday.isEmpty()) {
                Toast.makeText(context, "제목과 날짜를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 날짜를 YYYY-MM-DD 형식으로 조합
            val newDate = "$year-${month.padStart(2, '0')}-${day.padStart(2, '0')}"
            val newsDate = "$syear-${smonth.padStart(2, '0')}-${sday.padStart(2, '0')}"

            try {
                val date = LocalDate.parse(newDate, DateTimeFormatter.ISO_LOCAL_DATE)
                val sdate = LocalDate.parse(newsDate, DateTimeFormatter.ISO_LOCAL_DATE)
                val updatedEvent = event.copy(title = newTitle, date = date, sdate = sdate)


                val success = dbHelper.updateEvent(updatedEvent)
                if (success) {
                    Toast.makeText(context, "일정이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    onEventUpdated(updatedEvent) // UI 갱신
                    dismiss()
                } else {
                    Toast.makeText(context, "일정 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "날짜 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
