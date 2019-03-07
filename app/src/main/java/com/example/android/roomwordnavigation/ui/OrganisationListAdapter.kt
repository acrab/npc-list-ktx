package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.OrganisationSummary
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class OrganisationListAdapter internal constructor(
    context: Context, private val onOrganisationSelected: (OrganisationSummary) -> Unit
) : ListAdapter<OrganisationSummary, OrganisationListAdapter.OrganisationViewHolder>(OrganisationListAdapter.diffCallback),
    BindingListAdapter<OrganisationSummary> {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrganisationViewHolder(CharacterListItemBinding.inflate(inflater), onOrganisationSelected)

    override fun onBindViewHolder(holder: OrganisationViewHolder, position: Int) =
        holder.setOrganisation(getItem(position))

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<OrganisationSummary>() {

            override fun areContentsTheSame(oldItem: OrganisationSummary, newItem: OrganisationSummary) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: OrganisationSummary, newItem: OrganisationSummary) =
                oldItem.id == newItem.id
        }
    }

    class OrganisationViewHolder(
        private val listItemBinding: CharacterListItemBinding, onOrganisationSelected: (OrganisationSummary) -> Unit
    ) : RecyclerView.ViewHolder(listItemBinding.root) {
        private lateinit var organisation: OrganisationSummary

        init {
            listItemBinding.root.setOnClickListener {
                onOrganisationSelected(organisation)
            }
        }

        fun setOrganisation(organisation: OrganisationSummary) {
            listItemBinding.wordView.text = organisation.name
            this.organisation = organisation
        }
    }
}