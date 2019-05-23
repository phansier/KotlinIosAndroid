package ru.beryukhov.kotliniosandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_main.*
import ru.beryukhov.kotliniosandroid.baserecyclerview.SimpleListAdapter
import ru.beryukhov.kotliniosandroid.recyclerview.DateItem
import ru.beryukhov.kotliniosandroid.recyclerview.DateListAdapter
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetRepositoryImpl
import ru.beryukhov.mpp.presenter.TimeSheetPresenter
import ru.beryukhov.mpp.view.TimeSheetView


class MainActivity : AppCompatActivity(), TimeSheetView {

    private lateinit var adapter: SimpleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = DateListAdapter()

        days_rv.layoutManager = LinearLayoutManager(this)
        days_rv.adapter = adapter

        val presenter = TimeSheetPresenter(this, TimeSheetRepositoryImpl())
        presenter.onCreateView()

        setUpButtons(presenter)
    }

    override fun addAll(list: List<DateModel>) {
        runOnUiThread {
            adapter.add(list.map { dm -> DateItem(dm.startTime, dm.endTime, dm.hours) })
        }
    }

    override fun clear() {
        runOnUiThread {
            adapter.clearAll()
        }
    }

    override fun showError(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, LENGTH_SHORT).show()
        }
    }

    override fun showProgress() {
        //todo
        runOnUiThread {
        }
    }

    override fun hideProgress() {
        //todo
        runOnUiThread {
        }
    }

    private fun setUpButtons(presenter: TimeSheetPresenter) {
        add_start_button.setOnClickListener { presenter.onFixStart() }
        add_end_button.setOnClickListener { presenter.onFixEnd() }
    }
}
