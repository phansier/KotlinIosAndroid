package ru.beryukhov.mpp.view

import ru.beryukhov.mpp.domain.DateModel

expect class TimeSheetDatesView{
    fun addAll(list:List<DateModel>)
    fun clear()
    fun showError(message:String)
    fun showProgress()
    fun hideProgress()
}