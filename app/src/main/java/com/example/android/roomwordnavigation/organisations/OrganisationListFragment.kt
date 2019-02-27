package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.databinding.FragmentOrganisationListBinding
import com.example.android.roomwordnavigation.ui.OrganisationListAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OrganisationListFragment : DaggerFragment(), IWithViewModelFactory {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationListViewModel: OrganisationListViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentOrganisationListBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(OrganisationListFragmentDirections.actionOrganisationListFragmentToAddOrganisationFragment())
        }

        val recyclerView = binding.organisationList

        recyclerView.adapter = OrganisationListAdapter(context!!) {
            findNavController().navigate(OrganisationListFragmentDirections.actionShowSelectedOrganisation(it.id))
        }
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        organisationListViewModel.allOrganisations.observe(this, Observer { organisations ->
            organisations?.let {
                adapter.setOrganisations(it)
            }
        })

        return binding.root
    }
}