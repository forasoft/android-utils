package com.forasoft.androidutils.logpecker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

internal class SimpleListAdapter<T>(
    private val itemLayoutResId: Int,
    private val bindView: SimpleListAdapter<T>.(View, T) -> Unit,
) : BaseAdapter() {

    private var items: List<T> = emptyList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(itemLayoutResId, parent, false)!!
        val item = getItem(position)
        bindView(view, item)
        return view
    }

    override fun getItem(position: Int): T = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

    fun submitList(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

}
