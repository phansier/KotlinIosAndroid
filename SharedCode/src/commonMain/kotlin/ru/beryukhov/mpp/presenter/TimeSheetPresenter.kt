@file:UseExperimental(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)

package ru.beryukhov.mpp.presenter

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import kotlinx.coroutines.*
import ru.beryukhov.mpp.coroutines.netScope
import ru.beryukhov.mpp.coroutines.processScope
import ru.beryukhov.mpp.coroutines.uiScope
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetInteractor
import ru.beryukhov.mpp.domain.TimeSheetInteractorImpl
import ru.beryukhov.mpp.domain.TimeSheetRepository
import ru.beryukhov.mpp.view.TimeSheetView

/**
 * Created by Andrey Beryukhov
 */
class TimeSheetPresenter(val timeSheetView: TimeSheetView, timeSheetRepository: TimeSheetRepository) {
    val timeSheetInteractor: TimeSheetInteractor

    val job = Job()

    init {
        timeSheetInteractor = TimeSheetInteractorImpl(timeSheetRepository)
    }

    fun onCreateView() {
        timeSheetView.showProgress()
        netScope.launch(context = job) {
            val dates = async {
                timeSheetInteractor.getDatesList(getStartDate())
            }
            withContext(uiScope.coroutineContext) {
                timeSheetView.clear()
                timeSheetView.addAll(dates.await())
                timeSheetView.hideProgress()
            }
        }
    }

    fun onFixStart() {
        timeSheetView.showProgress()
        processScope.launch(context = job) {
            val dates = async {
                timeSheetInteractor.addStartTime(DateTime.now())
                timeSheetInteractor.getDatesList(getStartDate())
            }
            withContext(uiScope.coroutineContext) {
                timeSheetView.clear()
                timeSheetView.addAll(dates.await())
                timeSheetView.hideProgress()
            }
        }
    }

    fun onFixEnd() {
        timeSheetView.showProgress()
        processScope.launch(context = job) {
            val dates = async {
                timeSheetInteractor.addEndTime(DateTime.now())
                timeSheetInteractor.getDatesList(getStartDate())
            }
            withContext(uiScope.coroutineContext) {
                timeSheetView.clear()
                timeSheetView.addAll(dates.await())
                timeSheetView.hideProgress()
            }
        }
    }

    fun onItemClick(item: DateModel) { /*todo*/
    }

    fun onDestroyView() {
        job.cancel()
    }

    companion object {
        fun getStartDate() = DateTime.now() - DateTimeSpan(days = 7)
    }
}