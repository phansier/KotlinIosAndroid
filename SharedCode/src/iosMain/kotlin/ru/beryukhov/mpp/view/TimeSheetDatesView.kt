package ru.beryukhov.mpp.view

import ru.beryukhov.mpp.domain.DateModel

actual class TimeSheetDatesView{
    actual fun addAll(list: List<DateModel>){}
    actual fun clear(){}
    actual fun showError(message: String){}
    actual fun showProgress(){}
    actual fun hideProgress(){}
}