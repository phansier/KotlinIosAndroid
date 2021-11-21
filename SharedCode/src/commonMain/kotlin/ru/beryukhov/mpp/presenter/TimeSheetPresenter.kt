@file:OptIn(
    ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class
)

package ru.beryukhov.mpp.presenter

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetInteractor
import ru.beryukhov.mpp.domain.TimeSheetInteractorImpl
import ru.beryukhov.mpp.domain.TimeSheetRepository
import ru.beryukhov.mpp.view.TimeSheetView

/**
 * Created by Andrey Beryukhov
 */
class TimeSheetPresenter(
    val timeSheetView: TimeSheetView,
    timeSheetRepository: TimeSheetRepository
) {
    val timeSheetInteractor: TimeSheetInteractor

    private val scope = MainScope()

    init {
        timeSheetInteractor = TimeSheetInteractorImpl(timeSheetRepository)
    }

    fun onCreateView() {
        timeSheetView.showProgress()
        scope.launch {
            val dates = async {
                timeSheetInteractor.getDatesList(getStartDate())
            }
            timeSheetView.clear()
            timeSheetView.addAll(dates.await())
            timeSheetView.hideProgress()
        }
    }

    fun onFixStart() {
        timeSheetView.showProgress()
        scope.launch {
            val dates = async {
                timeSheetInteractor.addStartTime(DateTime.now())
                timeSheetInteractor.getDatesList(getStartDate())
            }
            timeSheetView.clear()
            timeSheetView.addAll(dates.await())
            timeSheetView.hideProgress()
        }
    }

    fun onFixEnd() {
        timeSheetView.showProgress()
        scope.launch {
            val dates = async {
                timeSheetInteractor.addEndTime(DateTime.now())
                timeSheetInteractor.getDatesList(getStartDate())
            }
            timeSheetView.clear()
            timeSheetView.addAll(dates.await())
            timeSheetView.hideProgress()
        }
    }

    fun onItemClick(item: DateModel) { /*todo*/
    }

    fun onDestroyView() {
        scope.cancel()
    }

    companion object {
        fun getStartDate() = DateTime.now() - DateTimeSpan(days = 7)
    }
}