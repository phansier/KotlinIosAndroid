package ru.beryukhov.mpp.presenter

import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetRepository
import ru.beryukhov.mpp.view.TimeSheetView


actual class TimeSheetPresenterKmp actual constructor(timeSheetView: TimeSheetView, timeSheetRepository: TimeSheetRepository) {
    val timeSheetPresenter: TimeSheetPresenter

    init {
        timeSheetPresenter = TimeSheetPresenter(timeSheetView, timeSheetRepository)
    }

    actual fun onCreateView() = timeSheetPresenter.onCreateView()

    actual fun onFixStart() = timeSheetPresenter.onFixStart()

    actual fun onFixEnd() = timeSheetPresenter.onFixEnd()

    actual fun onItemClick(item: DateModel) = timeSheetPresenter.onItemClick(item)

}