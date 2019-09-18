package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateTime

data class DateTimeRecord(var dateTime: DateTime, val isStart: Boolean)