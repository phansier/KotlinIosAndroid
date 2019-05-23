package ru.beryukhov.mpp.presenter

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetInteractor
import ru.beryukhov.mpp.view.TimeSheetDatesView

/**
 * Created by Andrey Beryukhov
 */
class TimeSheetPresenter(val timeSheetInteractor: TimeSheetInteractor, val timeSheetView: TimeSheetDatesView) {
    fun onCreateView() {
        timeSheetView.showProgress()
        GlobalScope.launch {
            val dates = async { timeSheetInteractor.getDatesList(getStartDate()) }
            timeSheetView.addAll(dates.await())
            withContext(Dispatchers.Main) {
                timeSheetView.hideProgress()
            }
        }
    }

    fun onFixStart() {
        timeSheetView.showProgress()
        GlobalScope.launch {
            val dates = async {
                timeSheetInteractor.addStartTime(DateTime.now())
                timeSheetInteractor.getDatesList(getStartDate())
            }
            timeSheetView.addAll(dates.await())
            withContext(Dispatchers.Main) {
                timeSheetView.hideProgress()
            }
        }
    }

    fun onFixEnd() {
        timeSheetView.showProgress()
        GlobalScope.launch {
            val dates = async {
                timeSheetInteractor.addEndTime(DateTime.now())
                timeSheetInteractor.getDatesList(getStartDate())
            }
            timeSheetView.addAll(dates.await())
            withContext(Dispatchers.Main) {
                timeSheetView.hideProgress()
            }
        }
    }

    fun onItemClick(item: DateModel) { /*todo*/
    }

    private fun getStartDate() = DateTime.now() - DateTimeSpan(days = 7)
}