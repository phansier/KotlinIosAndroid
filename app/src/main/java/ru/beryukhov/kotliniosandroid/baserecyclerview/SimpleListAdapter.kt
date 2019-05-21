package ru.beryukhov.kotliniosandroid.baserecyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Andrey Beryukhov
 */
abstract class SimpleListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IBaseListAdapter<IBaseListItem> {

    protected val items: ArrayList<IBaseListItem> = ArrayList()

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = items[position].getLayoutId()

    protected fun inflateByViewType(context: Context?, viewType: Int, parent: ViewGroup) =
            LayoutInflater.from(context).inflate(viewType, parent, false)

    override fun add(newItem: IBaseListItem) {
        items.add(newItem)
        notifyDataSetChanged()
    }

    override fun add(newItems: ArrayList<IBaseListItem>?) {

        for (newItem in newItems ?: return) {
            items.add(newItem)
            notifyDataSetChanged()
        }
    }

    override fun addAtPosition(pos: Int, newItem: IBaseListItem) {
        items.add(pos, newItem)
        notifyDataSetChanged()
    }

    override fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun remove(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeLast() {
        if (items.size == 0) {
            return
        }
        items.removeAt(items.size - 1)
        notifyDataSetChanged()
    }
}