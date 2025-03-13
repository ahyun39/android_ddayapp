package com.example.ddayapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE events (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                event_date TEXT NOT NULL,
                is_main_event INTEGER DEFAULT 0
            )
        """.trimIndent()
        val createSelectedEventsTable = """
        CREATE TABLE selected_events (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            event_id INTEGER NOT NULL,
            is_main_event INTEGER DEFAULT 0,
            FOREIGN KEY (event_id) REFERENCES events (id)
        )
    """.trimIndent()
        db.execSQL(createTable)
        db.execSQL(createSelectedEventsTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE events ADD COLUMN is_main_event INTEGER DEFAULT 0")
        }
        if (oldVersion < 3) {
            db.execSQL(
                """
            CREATE TABLE IF NOT EXISTS selected_events (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                event_id INTEGER NOT NULL,
                is_main_event INTEGER DEFAULT 0,
                FOREIGN KEY (event_id) REFERENCES events (id)
            )
        """.trimIndent()
            )
        }
    }

    fun insertEvent(event: Event): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("title", event.title)
                put("event_date", event.date.toString())
                put("is_main_event", if (event.isMainEvent) 1 else 0)
            }
            db.insert("events", null, values)
        } finally {
            db.close()
        }
    }


    fun updateEvent(event: Event): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", event.title)
            put("event_date", event.date.toString())
        }
        val result = db.update("events", values, "id=?", arrayOf(event.id.toString()))
        db.close()
        return result > 0
    }

    fun deleteEvent(event: Event) {
        val db = writableDatabase
        try {
            db.delete("events", "id = ?", arrayOf(event.id.toString()))
        } finally {
            db.close()
        }
    }

    fun getAllEvents(selectedEventId: Int? = null): List<Event> {
        val db = readableDatabase
        val events = mutableListOf<Event>()

        // 선택된 일정은 먼저, 대표 일정은 그다음, 나머지는 날짜 순으로 정렬
        val query = if (selectedEventId != null) {
            """
            SELECT * FROM events 
            ORDER BY 
                CASE WHEN id = $selectedEventId THEN 0 ELSE 1 END, 
                is_main_event DESC, 
                date(event_date) ASC
            """
        } else {
            "SELECT * FROM events ORDER BY is_main_event DESC, date(event_date) ASC"
        }

        val cursor = db.rawQuery(query, null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                    val dateString = cursor.getString(cursor.getColumnIndexOrThrow("event_date"))
                    val date = LocalDate.parse(dateString)
                    val isMainEvent =
                        cursor.getInt(cursor.getColumnIndexOrThrow("is_main_event")) == 1

                    events.add(Event(id, title, date, isMainEvent))
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
        }
        return events
    }

    fun setMainEvent(context: Context, eventId: Int) {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.beginTransaction()

            // 모든 일정을 비대표로 설정
            db.execSQL("UPDATE events SET is_main_event = 0")

            // 선택된 일정만 대표 일정으로 설정
            val statement = db.compileStatement("UPDATE events SET is_main_event = 1 WHERE id = ?")
            statement.bindLong(1, eventId.toLong())
            statement.executeUpdateDelete()

            db.setTransactionSuccessful()
            Log.d("DatabaseHelper", "대표 일정 설정 완료, ID: $eventId")

            val intent = Intent(context, DDayWidgetProvider::class.java).apply {
                action = "com.example.ddayapp.UPDATE_WIDGET"
            }
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "대표 일정 설정 실패: ${e.message}")
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun saveSelectedEventId(eventId: Int, isMainEvent: Boolean) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            // 기존의 selected_events 테이블을 초기화
            db.execSQL("DELETE FROM selected_events")

            // 새로운 대표 일정을 저장
            val values = ContentValues().apply {
                put("event_id", eventId)
                put("is_main_event", if (isMainEvent) 1 else 0)
            }
            db.insert("selected_events", null, values)

            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "대표 일정 저장 실패: ${e.message}")
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun saveEventForWidget(widgetId: Int, title: String, date: LocalDate) {
        val db = writableDatabase
        db.execSQL(
            "INSERT OR REPLACE INTO events (widget_id, title, date) VALUES (?, ?, ?)",
            arrayOf(widgetId, title, date.toString())
        )
        db.close()
    }

    fun getEventByWidgetId(id: Int): Event? {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT title, event_date FROM events WHERE id = ?", arrayOf(id.toString()))

        var event: Event? = null
        if (cursor.moveToFirst()) {
            val title = cursor.getString(0)
            val dateString = cursor.getString(1)
            val date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
            event = Event(id, title, date)
        }

        cursor.close()
        db.close()
        return event
    }

    companion object {
        private const val DATABASE_NAME = "events.db"
        private const val DATABASE_VERSION = 3
    }
}