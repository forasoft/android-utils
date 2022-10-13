package com.forasoft.androidutils.logpecker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

/**
 * Basic adapter for [ListView].
 *
 * Use [submitList] for updating the underlying data.
 *
 * @param T item type.
 * @property itemLayoutResId item layout resource ID.
 * @property bindItem callback to be invoked when binding view to the item.
 */
internal class SimpleListAdapter<T>(
    private val itemLayoutResId: Int,
    private val bindItem: SimpleListAdapter<T>.(View, T) -> Unit,
) : BaseAdapter() {

    private var items: List<T> = emptyList()

    /**
     * Updates the underlying data with the given [items].
     *
     * Note: this method uses [notifyDataSetChanged] under the hood.
     */
    fun submitList(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(itemLayoutResId, parent, false)!!
        val item = getItem(position)
        bindItem(view, item)
        return view
    }

    override fun getItem(position: Int): T = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

}
