package ru.beryukhov.kotliniosandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.beryukhov.kotliniosandroid.baserecyclerview.SimpleListAdapter
import ru.beryukhov.kotliniosandroid.recyclerview.DateItem
import ru.beryukhov.kotliniosandroid.recyclerview.DateListAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SimpleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = DateListAdapter()

        days_rv.layoutManager = LinearLayoutManager(this)
        days_rv.adapter = adapter

        setUpButtons()
    }

    private fun setUpButtons() {
        add_start_button.setOnClickListener { adapter.add(DateItem("testStart", "testEnd", "testDuration")) }
        add_end_button.setOnClickListener { adapter.removeLast() }
    }
}
