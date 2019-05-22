package ru.beryukhov.mpp.domain

import com.soywiz.klock.DateTime

class TimeSheetInteractorImpl(val timeSheetRepository: TimeSheetRepository): TimeSheetInteractor {
    override suspend fun getDatesList(startDate: DateTime): List<DateModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addStartTime(time: DateTime): List<DateModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addEndTime(time: DateTime): List<DateModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}