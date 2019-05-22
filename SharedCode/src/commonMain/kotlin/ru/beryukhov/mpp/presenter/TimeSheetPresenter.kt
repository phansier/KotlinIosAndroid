package ru.beryukhov.mpp.presenter

import com.soywiz.klock.DateTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetInteractor
import ru.beryukhov.mpp.view.TimeSheetDatesView

/**
 * Created by Andrey Beryukhov
 */
class TimeSheetPresenter(val timeSheetInteractor: TimeSheetInteractor, val timeSheetView: TimeSheetDatesView) {
    fun onCreateView() {
        GlobalScope.launch {
            timeSheetView.showProgress()
            val dates = async { timeSheetInteractor.getDatesList(DateTime.now()) }
            timeSheetView.addAll(dates.await())
            timeSheetView.hideProgress()
        }
    }

    fun onFixStart() {
        GlobalScope.launch {
            timeSheetView.showProgress()
            val dates = async { timeSheetInteractor.addStartTime(DateTime.now()) }
            timeSheetView.addAll(dates.await())
            timeSheetView.hideProgress()
        }
    }

    fun onFixEnd() {
        GlobalScope.launch {
            timeSheetView.showProgress()
            val dates = async { timeSheetInteractor.addEndTime(DateTime.now()) }
            timeSheetView.addAll(dates.await())
            timeSheetView.hideProgress()
        }
    }

    fun onItemClick(item: DateModel) { /*todo*/
    }
}