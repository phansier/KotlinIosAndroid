package ru.beryukhov.kotliniosandroid.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.date_item.view.*
import ru.beryukhov.kotliniosandroid.R
import ru.beryukhov.kotliniosandroid.baserecyclerview.IBaseListItem

/**
 * Created by Andrey Beryukhov
 */
data class DateItem (val startTime:String?, val endTime:String?, val duration:String): IBaseListItem {
    override fun getLayoutId(): Int {
        return R.layout.date_item
    }

}

class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val startTime = view.start_time_tv
    val endTime = view.end_time_tv
    val duration = view.duration_tv
}