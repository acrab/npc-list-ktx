package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.OrganisationSummary
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class OrganisationListAdapter internal constructor(context: Context, private val onOrganisationSelected: (OrganisationSummary)->Unit)
    : RecyclerView.Adapter<OrganisationListAdapter.OrganisationViewHolder>(), BindingListAdapter<OrganisationSummary> {

    class OrganisationViewHolder(private val listItemBinding: CharacterListItemBinding, onOrganisationSelected: (OrganisationSummary)->Unit) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        private lateinit var organisation: OrganisationSummary

        init{
            listItemBinding.root.setOnClickListener{
                onOrganisationSelected(organisation)
            }
        }

        fun setOrganisation(organisation: OrganisationSummary) {
            listItemBinding.wordView.text = organisation.name
            this.organisation = organisation
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var organisations = emptyList<OrganisationSummary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisationViewHolder {
        return OrganisationViewHolder(CharacterListItemBinding.inflate(inflater), onOrganisationSelected)
    }

    override fun getItemCount(): Int = organisations.size

    override fun onBindViewHolder(holder: OrganisationViewHolder, position: Int) {
        holder.setOrganisation(organisations[position])
    }

    override fun setData(data: List<OrganisationSummary>) {
        this.organisations = data
        notifyDataSetChanged()
    }
}