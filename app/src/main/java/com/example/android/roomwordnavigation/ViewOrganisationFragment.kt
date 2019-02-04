package com.example.android.roomwordnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.databinding.FragmentViewOrganisationBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter

class ViewOrganisationFragment : Fragment()
{
    private val args: ViewOrganisationFragmentArgs by navArgs()

    private lateinit var organisationDetailsViewModel: OrganisationDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentViewOrganisationBinding>(inflater, R.layout.fragment_view_organisation, container, false)

        val orgId = args.organisationId

        val recyclerView = binding.memberList
        val adapter = CharacterListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        organisationDetailsViewModel = activity?.run { ViewModelProviders.of(this, OrganisationDetailsViewModel.Factory(application,orgId)).get(OrganisationDetailsViewModel::class.java)} ?: throw Exception("Invalid Activity")
        organisationDetailsViewModel.allMembers.observe(this, Observer {members ->
            members?.let {
                adapter.setCharacters(it)
            }
        })

        organisationDetailsViewModel.organisation.observe(this, Observer { org->
            org?.let {
                binding.textView.text = it.description
            }
        })

        binding.fab.setOnClickListener{
            it.findNavController().navigate(ViewOrganisationFragmentDirections.actionViewOrganisationFragmentToAddCharacterToOrganisationFragment(orgId))
        }

        return binding.root
    }
}