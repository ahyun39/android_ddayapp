package com.example.ddayapp

import java.time.LocalDate

data class Event(
    val id: Int,
    val title: String,
    val date: LocalDate,
    val sdate: LocalDate, //시작 일정 설정
    val isMainEvent: Boolean = false // 대표 일정 여부
)