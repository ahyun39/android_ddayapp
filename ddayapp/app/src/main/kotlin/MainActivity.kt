package com.example.ddayapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventList: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("first_run", true)

        if (isFirstRun) {
            clearAppData()  // 모든 데이터 삭제
            prefs.edit().putBoolean("first_run", false).apply()
        }

        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        eventList = findViewById(R.id.listView)
        val btnAddEvent: Button = findViewById(R.id.btnAddEvent)

        btnAddEvent.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        val btnSelectEvent: Button = findViewById(R.id.btnSelectEvent)
        btnSelectEvent.setOnClickListener {
            val intent = Intent(this, EventSelectionActivity::class.java)
            startActivity(intent)
        }


        loadEvents()
    }

    private fun clearAppData() {
        try {
            // 1. SharedPreferences 삭제
            val sharedPreferences = getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            val appPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            appPrefs.edit().clear().apply()

            // 2. 캐시 삭제
            val cacheDir = applicationContext.cacheDir
            cacheDir.deleteRecursively()

            // 3. 내부 저장소 데이터 삭제
            val filesDir = applicationContext.filesDir
            filesDir.deleteRecursively()

            // 4. 데이터베이스 삭제
            val dbPath = applicationContext.getDatabasePath("events.db")
            if (dbPath.exists()) {
                dbPath.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun loadEvents() {
        val events = dbHelper.getAllEvents().toMutableList()

        // 대표 일정이 상단에 위치하도록 정렬
        events.sortByDescending { it.isMainEvent }

        eventAdapter = EventAdapter(this, events, dbHelper)
        eventList.adapter = eventAdapter
        eventAdapter.notifyDataSetChanged()
    }


    override fun onResume() {
        super.onResume()
        loadEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close() 
    }
}