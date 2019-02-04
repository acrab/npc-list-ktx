package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class OrganisationListAdapter internal constructor(context: Context, private val onOrganisationSelected: (Organisation)->Unit) : RecyclerView.Adapter<OrganisationListAdapter.OrganisationViewHolder>() {

    class OrganisationViewHolder(private val listItemBinding: CharacterListItemBinding, onOrganisationSelected: (Organisation)->Unit) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        private var organisation: Organisation? = null
        init{
            listItemBinding.root.setOnClickListener{
                onOrganisationSelected(organisation!!)
            }
        }

        fun setOrganisation(organisation: Organisation) {
            listItemBinding.wordView.text = organisation.name
            this.organisation = organisation
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var organisations = emptyList<Organisation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisationViewHolder {
        return OrganisationViewHolder(CharacterListItemBinding.inflate(inflater), onOrganisationSelected)
    }

    override fun getItemCount(): Int = organisations.size

    override fun onBindViewHolder(holder: OrganisationViewHolder, position: Int) {
        holder.setOrganisation(organisations[position])
    }

    fun setOrganisations(organisations: List<Organisation>) {
        this.organisations = organisations
        notifyDataSetChanged()
    }
}
