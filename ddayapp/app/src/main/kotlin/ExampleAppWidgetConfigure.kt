package com.example.ddayapp

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class ExampleAppWidgetConfigure : AppCompatActivity() {
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var dbHelper: DatabaseHelper
    private var selectedEventId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_config)

        // 위젯 ID 가져오기
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        dbHelper = DatabaseHelper(this)
        val events = dbHelper.getAllEvents()

        val spinner: Spinner = findViewById(R.id.spinnerWidgetEvents)
        val btnSave: Button = findViewById(R.id.btnSaveWidgetEvent)

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
                val selectedEvent = events.firstOrNull { it.id == selectedEventId }

                if (selectedEvent != null) {
                    val sharedPreferences = getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putInt("widget_$appWidgetId", selectedEventId)
                        putString("widget_${appWidgetId}_title", selectedEvent.title)
                        putString("widget_${appWidgetId}_date", selectedEvent.date?.toString() ?: LocalDate.now().toString()) // 기본값 추가
                        putString("widget_${appWidgetId}_sdate", selectedEvent.sdate?.toString() ?: LocalDate.now().toString())
                        apply()
                    }
                }


                Log.d("WidgetConfigActivity", "Saved Event ID: $selectedEventId for Widget: $appWidgetId")

                // 위젯 업데이트 실행
                val appWidgetManager = AppWidgetManager.getInstance(this)
                DDayWidgetProvider.updateWidget(this, appWidgetManager, appWidgetId)

                // 위젯 설정 완료 처리
                val resultValue = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }
                setResult(RESULT_OK, resultValue)
                finish()
            } else {
                Log.w("WidgetConfigActivity", "No event selected!")
            }
        }
    }
}
