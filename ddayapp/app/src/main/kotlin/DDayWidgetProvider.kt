package com.example.ddayapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DDayWidgetProvider : AppWidgetProvider() {

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        val sharedPreferences = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("default_event_title", "일정 없음")
        editor.apply()
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }

    companion object {
        fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val sharedPreferences = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
            val eventTitle = sharedPreferences.getString("widget_${appWidgetId}_title", "일정 없음")
            val eventDateStr = sharedPreferences.getString("widget_${appWidgetId}_date", null)
            val startDateStr = sharedPreferences.getString("widget_${appWidgetId}_sdate", null)

            var dDayText = "날짜 없음"
            var progress = 0
            var percent = "0%"

            if (!eventDateStr.isNullOrEmpty() && !startDateStr.isNullOrEmpty()) {
                try {
                    val today = LocalDate.now()
                    val eventDate = LocalDate.parse(eventDateStr, DateTimeFormatter.ISO_LOCAL_DATE)
                    val startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE)
                    val daysLeft = ChronoUnit.DAYS.between(today, eventDate)

                    dDayText = if (daysLeft >= 0) "D-$daysLeft" else "D+${-daysLeft}"

                    // Progress 계산
                    val totalDays = ChronoUnit.DAYS.between(startDate, eventDate).toInt()
                    val progressDay = ChronoUnit.DAYS.between(startDate, today).toInt()
                    progress = ((progressDay) * 100 / totalDays).coerceIn(0, 100).toInt()
                    percent = "${progress}%"
                } catch (e: Exception) {
                    e.printStackTrace() // 로그 출력
                    dDayText = "날짜 오류"
                }
            }

            val views = RemoteViews(context.packageName, R.layout.widget_dday)
            views.setTextViewText(R.id.tvTitle, eventTitle)
            views.setTextViewText(R.id.tvDday, dDayText)
            views.setTextViewText(R.id.tveventDate, eventDateStr ?: "날짜 없음")
            views.setProgressBar(R.id.progressBar, 100, progress, false)
            views.setTextViewText(R.id.tvProgressPercentage, percent)

            // 클릭 시 WidgetConfigActivity 실행
            val intent = Intent(context, ExampleAppWidgetConfigure::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
