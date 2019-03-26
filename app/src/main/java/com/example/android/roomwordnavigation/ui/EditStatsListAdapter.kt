package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.entities.Statistic
import com.example.android.roomwordnavigation.databinding.EditableStatisticItemBinding

class EditStatsListAdapter(
    context: Context, private val onValueChangeListener: (Int, Int) -> Unit
) : ListAdapter<Statistic, EditStatsListAdapter.ViewHolder>(Statistic.diffCallback),
    BindingListAdapter<Statistic> {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EditStatsListAdapter.ViewHolder(
        EditableStatisticItemBinding.inflate(inflater),
        onValueChangeListener
    )

    override fun onBindViewHolder(holder: EditStatsListAdapter.ViewHolder, position: Int) =
        holder.setCharacter(getItem(position))

    class ViewHolder(
        private val characterListItemBinding: EditableStatisticItemBinding,
        onValueChangeListener: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(characterListItemBinding.root) {
        private lateinit var statistic: Statistic

        init {

        }

        fun setCharacter(statistic: Statistic) {
            this.statistic = statistic
            characterListItemBinding.stat = statistic

        }
    }
}