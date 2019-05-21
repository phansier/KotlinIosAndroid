package ru.beryukhov.kotliniosandroid.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.beryukhov.kotliniosandroid.R
import ru.beryukhov.kotliniosandroid.baserecyclerview.SimpleListAdapter

/**
 * Created by Andrey Beryukhov
 */
class DateListAdapter : SimpleListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context

        return when (viewType) {
            R.layout.date_item -> DateViewHolder(inflateByViewType(context, viewType, parent))
            else -> throw IllegalStateException("There is no match with current layoutId")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is DateViewHolder -> {
                val dateItem = items[position] as DateItem
                holder.startTime.text = dateItem.startTime
                holder.endTime.text=dateItem.endTime
                holder.duration.text=dateItem.duration
            }

            else -> throw IllegalStateException("There is no match with current holder instance")
        }
    }
}