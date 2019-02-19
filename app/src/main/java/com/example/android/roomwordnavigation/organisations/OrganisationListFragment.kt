package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.databinding.FragmentOrganisationListBinding
import com.example.android.roomwordnavigation.ui.OrganisationListAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OrganisationListFragment : DaggerFragment(), IWithViewModelFactory {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationListViewModel: OrganisationListViewModel by activityViewModels {viewModelFactory}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentOrganisationListBinding>(
            inflater,
            R.layout.fragment_organisation_list,
            container,
            false
        )

        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(OrganisationListFragmentDirections.actionOrganisationListFragmentToAddOrganisationFragment())
        }

        val recyclerView = binding.organisationList
        val adapter = OrganisationListAdapter(context!!) {
            val action =
                OrganisationListFragmentDirections.actionOrganisationListFragmentToViewOrganisationFragment(it.id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        organisationListViewModel.allOrganisations.observe(this, Observer { organisations ->
            organisations?.let {
                adapter.setOrganisations(it)
            }
        })

        return binding.root
    }
}