package com.example.ddayapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class EventAdapter(
    private val context: Context,
    private var eventList: MutableList<Event>,
    private val dbHelper: DatabaseHelper
) : BaseAdapter() {

    override fun getCount(): Int = eventList.size

    override fun getItem(position: Int): Any = eventList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val event = eventList[position]
        holder.title.text = event.title

        val currentDate = LocalDate.now()
        val daysLeft = ChronoUnit.DAYS.between(currentDate, event.date).toInt()

        holder.dday.text = if (daysLeft >= 0) "D-$daysLeft" else "D+${-daysLeft}"

        val totalDays = ChronoUnit.DAYS.between(event.sdate, event.date).toInt()
        val progressDay = ChronoUnit.DAYS.between(event.sdate, currentDate).toInt()
        val progress = ((progressDay) * 100 / totalDays).coerceIn(0, 100)

        holder.progressBar.progress = progress
        holder.progressPercentage.text = "$progress%"

        // 오늘 날짜 표시
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        holder.eventDate.text = event.date.format(formatter)

        fun updateRunnerPosition(progressBar: ProgressBar, runnerIcon: ImageView, progress: Int) {
            progressBar.post {
                val maxTranslation = progressBar.width - runnerIcon.width
                val translationX = maxTranslation * (progress / 100f)
                runnerIcon.translationX = translationX
            }
        }

        // ProgressBar에 따른 아이콘 이동
        updateRunnerPosition(holder.progressBar, holder.ivRunner, progress)

        holder.btnEdit.setOnClickListener {
            val editDialog = EditEventDialog(context, event) { updatedEvent ->
                eventList[position] = updatedEvent // 리스트 업데이트
                notifyDataSetChanged() // UI 갱신
                Toast.makeText(context, "일정이 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }
            editDialog.show()

        }

        holder.btnDelete.setOnClickListener {
            dbHelper.deleteEvent(event)
            eventList.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        holder.title.setOnClickListener {
            val fullTextDialog = FullTextDialog(context, event.title)
            fullTextDialog.show()
        }

        return view
    }

    private class ViewHolder(view: View) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val dday: TextView = view.findViewById(R.id.tvDday)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val progressPercentage: TextView = view.findViewById(R.id.tvProgressPercentage)
        val eventDate: TextView = view.findViewById(R.id.tveventDate)
        val ivRunner: ImageView = view.findViewById(R.id.ivRunner)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
    }
}
