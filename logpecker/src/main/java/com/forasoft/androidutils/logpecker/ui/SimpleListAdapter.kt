package com.forasoft.androidutils.logpecker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class SimpleListAdapter<T>(
    private val itemLayoutResId: Int,
    private val items: List<T>,
    private val bindView: SimpleListAdapter<T>.(View, Int) -> Unit,
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(itemLayoutResId, parent, false)!!
        bindView(view, position)
        return view
    }

    override fun getItem(position: Int): T = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

}
