package com.example.android.roomwordnavigation.ui

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setupLinearWithAdapter(context: Context, adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context)
}

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