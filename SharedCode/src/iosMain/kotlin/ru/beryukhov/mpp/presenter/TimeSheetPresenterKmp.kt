package ru.beryukhov.mpp.presenter

import com.soywiz.klock.DateTime
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetInteractor
import ru.beryukhov.mpp.domain.TimeSheetInteractorImpl
import ru.beryukhov.mpp.domain.TimeSheetRepository
import ru.beryukhov.mpp.presenter.TimeSheetPresenter.Companion.getStartDate
import ru.beryukhov.mpp.view.TimeSheetView

/**
 * Created by Andrey Beryukhov
 * Expected that all the multithreading logic is solved by Swift code
 * Presenter method should be called from background thread
 * View methods implementation should return to main (UI) thread
 */
actual class TimeSheetPresenterKmp actual constructor(timeSheetView: TimeSheetView, timeSheetRepository: TimeSheetRepository) {
    val timeSheetInteractor: TimeSheetInteractor
    val timeSheetView: TimeSheetView

    init {
        timeSheetInteractor = TimeSheetInteractorImpl(timeSheetRepository)
        this.timeSheetView = timeSheetView
    }

    /**
     * Should be called from background thread
     */
    actual fun onCreateView() {
        timeSheetView.showProgress()
        val dates = timeSheetInteractor.getDatesListBlocking(getStartDate())
        timeSheetView.clear()
        timeSheetView.addAll(dates)
        timeSheetView.hideProgress()
    }

    /**
     * Should be called from background thread
     */
    actual fun onFixStart() {
        timeSheetView.showProgress()
        timeSheetInteractor.addStartTimeBlocking(DateTime.now())
        val dates = timeSheetInteractor.getDatesListBlocking(getStartDate())
        timeSheetView.clear()
        timeSheetView.addAll(dates)
        timeSheetView.hideProgress()

    }


    /**
     * Should be called from background thread
     */
    actual fun onFixEnd() {
        timeSheetView.showProgress()


        timeSheetInteractor.addEndTimeBlocking(DateTime.now())
        val dates = timeSheetInteractor.getDatesListBlocking(getStartDate())
        timeSheetView.clear()
        timeSheetView.addAll(dates)
        timeSheetView.hideProgress()

    }


    actual fun onItemClick(item: DateModel) {
    }

}