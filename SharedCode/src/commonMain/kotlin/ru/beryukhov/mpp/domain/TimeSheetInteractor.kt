package ru.beryukhov.mpp.domain

//import com.soywiz.klock.DateTime

interface TimeSheetInteractor {
    suspend fun getDatesList(startDate: DateTime): List<DateModel>
    suspend fun addStartTime(time: DateTime)
    suspend fun addEndTime(time: DateTime)
}

