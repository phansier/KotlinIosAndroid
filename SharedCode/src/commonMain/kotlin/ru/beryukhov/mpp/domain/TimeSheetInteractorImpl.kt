package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateTime

data class DateTimeRecord(var dateTime: DateTime, val isStart: Boolean)

class TimeSheetInteractorImpl(val timeSheetRepository: TimeSheetRepository) : TimeSheetInteractor {
    //todo remove
    val list = mutableSetOf(
            DateTimeRecord(DateTime.createAdjusted(2019, 5, 22, 9), true),
            DateTimeRecord(DateTime.createAdjusted(2019, 5, 22, 17, 30), false)
    )

    override suspend fun getDatesList(startDate: DateTime): List<DateModel> {
        return getDateModelList(list.filter{dateTimeRecord -> dateTimeRecord.dateTime>=startDate })
    }

    override suspend fun addStartTime(time: DateTime){
        val hasTime = list.find { dateTimeRecord -> dateTimeRecord.isStart&&isSameDate(dateTimeRecord.dateTime,time) }
        if (hasTime != null) {
            hasTime.dateTime=time
        }
        list.add(DateTimeRecord(time,true))
    }

    override suspend fun addEndTime(time: DateTime) {
        val hasTime = list.find { dateTimeRecord -> !dateTimeRecord.isStart&&isSameDate(dateTimeRecord.dateTime,time) }
        if (hasTime != null) {
            hasTime.dateTime=time
        }
        list.add(DateTimeRecord(time,false))
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
                result.add(DateModel("${startTime.dayOfMonth} ${startTime.month} ${startTime.year}",
                        "${startTime.hours}:${startTime.minutes}",
                        "${endTime.hours}:${endTime.minutes}",
                        (endTime - startTime).hours.toString()))
            }
        }
        return result
    }

    private fun isSameDate(startTime: DateTime, endTime: DateTime): Boolean = endTime.dayOfYear == startTime.dayOfYear && endTime.year == startTime.year

}