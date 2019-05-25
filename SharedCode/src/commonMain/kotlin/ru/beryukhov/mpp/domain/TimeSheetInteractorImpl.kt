package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.PatternDateFormat

data class DateTimeRecord(var dateTime: DateTime, val isStart: Boolean)

class TimeSheetInteractorImpl(val timeSheetRepository: TimeSheetRepository) : TimeSheetInteractor {

    //todo remove
    val list = mutableSetOf(
            DateTimeRecord(DateTime.createAdjusted(2019, 5, 22, 9), true),
            DateTimeRecord(DateTime.createAdjusted(2019, 5, 22, 17, 30), false)
    )

    private val timeFormat = PatternDateFormat("hh:mm")

    override suspend fun getDatesList(startDate: DateTime): List<DateModel> = getDatesListBlocking(startDate)

    override fun getDatesListBlocking(startDate: DateTime): List<DateModel> {
        return getDateModelList(list.filter { dateTimeRecord -> dateTimeRecord.dateTime >= startDate })
    }

    override suspend fun addStartTime(time: DateTime) = addStartTimeBlocking(time)

    override fun addStartTimeBlocking(time: DateTime) {
        val hasTime = list.find { dateTimeRecord -> dateTimeRecord.isStart && isSameDate(dateTimeRecord.dateTime, time) }
        if (hasTime != null) {
            hasTime.dateTime = time
        }
        list.add(DateTimeRecord(time, true))
    }

    override suspend fun addEndTime(time: DateTime) = addEndTimeBlocking(time)

    override fun addEndTimeBlocking(time: DateTime) {
        val hasTime = list.find { dateTimeRecord -> !dateTimeRecord.isStart && isSameDate(dateTimeRecord.dateTime, time) }
        if (hasTime != null) {
            hasTime.dateTime = time
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
                        (endTime - startTime).hours.toString().take(3)))
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