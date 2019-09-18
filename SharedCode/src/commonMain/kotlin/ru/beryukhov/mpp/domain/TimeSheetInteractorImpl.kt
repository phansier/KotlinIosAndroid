package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateTime
import com.soywiz.klock.PatternDateFormat
import kotlin.math.roundToInt

class TimeSheetInteractorImpl(val timeSheetRepository: TimeSheetRepository) : TimeSheetInteractor {

    val list = mutableSetOf<DateTimeRecord>()

    private val timeFormat = PatternDateFormat("hh:mm:ss")

    override suspend fun getDatesList(startDate: DateTime): List<DateModel> {
        return getDateModelList(list.filter { dateTimeRecord -> dateTimeRecord.dateTime >= startDate })
    }

    override suspend fun addStartTime(time: DateTime) {
        val hasTime = list.find { dateTimeRecord -> dateTimeRecord.isStart && isSameDate(dateTimeRecord.dateTime, time) }
        if (hasTime != null) {
            list.remove(hasTime)
        }
        list.add(DateTimeRecord(time, true))
    }

    override suspend fun addEndTime(time: DateTime) {
        val hasTime = list.find { dateTimeRecord -> !dateTimeRecord.isStart && isSameDate(dateTimeRecord.dateTime, time) }
        if (hasTime != null) {
            list.remove(hasTime)
        }
        list.add(DateTimeRecord(time, false))
    }

    private fun getDateModelList(list: List<DateTimeRecord>): List<DateModel> {
        val sortedList = list.sortedBy { dateTimeRecord -> dateTimeRecord.dateTime }
        val result = mutableListOf<DateModel>()
        var startTime = DateTime(0)
        for (dateTimeRecord in sortedList) {
            if (dateTimeRecord.isStart) {
                startTime = dateTimeRecord.dateTime
            } else if (isSameDate(startTime, dateTimeRecord.dateTime)) {
                val endTime = dateTimeRecord.dateTime
                result.add(DateModel("${startTime.dayOfMonth} ${startTime.month} ${startTime.yearInt}",
                        startTime.toString(timeFormat),
                        endTime.toString(timeFormat),
                        (endTime - startTime).seconds.roundToInt().toString()))//seconds are for tests and demo todo set hours
                startTime = DateTime(0)
            }
        }
        if (startTime != DateTime(0)) {
            result.add(DateModel("${startTime.dayOfMonth} ${startTime.month} ${startTime.yearInt}",
                    startTime.toString(timeFormat),
                    "~",
                    "~"))
        }
        return result
    }

    private fun isSameDate(startTime: DateTime, endTime: DateTime): Boolean = endTime.dayOfYear == startTime.dayOfYear && endTime.year == startTime.year
}