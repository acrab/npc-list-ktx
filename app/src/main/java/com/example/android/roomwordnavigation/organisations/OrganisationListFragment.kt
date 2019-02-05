package com.example.android.roomwordnavigation.organisations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.OrganisationListFragmentDirections
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.databinding.FragmentOrganisationListBinding
import com.example.android.roomwordnavigation.injection.ViewModelFactory
import com.example.android.roomwordnavigation.ui.OrganisationListAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class OrganisationListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var organisationListViewModel: OrganisationListViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


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

        organisationListViewModel =
            activity?.run { ViewModelProviders.of(this, viewModelFactory).get(OrganisationListViewModel::class.java) } ?: throw Exception(
                "Invalid Activity"
            )
        organisationListViewModel.allOrganisations.observe(this, Observer { organisations ->
            organisations?.let {
                adapter.setOrganisations(it)
            }
        })

        return binding.root
    }
}