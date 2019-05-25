package ru.beryukhov.mpp.presenter

import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetRepository
import ru.beryukhov.mpp.view.TimeSheetView

/**
 * Created by Andrey Beryukhov
 */
expect class TimeSheetPresenterKmp(timeSheetView: TimeSheetView, timeSheetRepository: TimeSheetRepository) {
    fun onCreateView()
    fun onFixStart()
    fun onFixEnd()
    fun onItemClick(item: DateModel)

}