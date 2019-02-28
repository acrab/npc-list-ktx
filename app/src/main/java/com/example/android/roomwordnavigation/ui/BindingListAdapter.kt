package com.example.android.roomwordnavigation.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("data")
fun <T> setData(recyclerView: RecyclerView, data: List<T>?) {
    if (data == null) return
    val adapter = recyclerView.adapter
    if (adapter is BindingListAdapter<*>) {
        @Suppress("UNCHECKED_CAST") (adapter as BindingListAdapter<T>).setData(data)
    }
}

interface BindingListAdapter<T> {
    fun setData(data: List<T>)
}