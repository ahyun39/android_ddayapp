package com.example.ddayapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EventSelectionActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private var selectedEventId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_selection)

        dbHelper = DatabaseHelper(this)
        val events = dbHelper.getAllEvents()

        val spinner: Spinner = findViewById(R.id.spinnerEvents)
        val btnSave: Button = findViewById(R.id.btnSaveEvent)

        val eventTitles = events.map { it.title }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTitles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedEventId = events[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnSave.setOnClickListener {
            if (selectedEventId != -1) {
                val isMainEvent = true // 대표 일정으로 선택할 때 true (예시)

                // 대표 일정 설정
                dbHelper.setMainEvent(this, selectedEventId) // 트랜잭션을 먼저 완료

                // selected_events 테이블에 저장
                dbHelper.saveSelectedEventId(selectedEventId, isMainEvent) // 두 번째 작업

                Log.d("EventSelectionActivity", "Saved Event ID: $selectedEventId, isMainEvent: $isMainEvent")

                // MainActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.w("EventSelectionActivity", "No event selected!")
            }
        }
    }
}