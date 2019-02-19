package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.databinding.FragmentViewOrganisationBinding
import com.example.android.roomwordnavigation.injection.ViewModelFactory
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewOrganisationFragment : DaggerFragment() {
    private val args: ViewOrganisationFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val organisationDetailsViewModel: OrganisationDetailsViewModel by activityViewModels{viewModelFactory}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentViewOrganisationBinding.inflate(inflater, container, false)

        val orgId = args.organisationId

        val recyclerView = binding.memberList
        val adapter = CharacterListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        organisationDetailsViewModel.organisationId.postValue(orgId)

        organisationDetailsViewModel.allMembers.observe(this, Observer { members ->
            members?.let {
                adapter.setCharacters(it)
            }
        })

        organisationDetailsViewModel.organisation.observe(this, Observer { org ->
            org?.let {
                binding.textView.text = it.description
            }
        })

        binding.fab.setOnClickListener {
            it.findNavController().navigate(
                ViewOrganisationFragmentDirections.actionViewOrganisationFragmentToAddCharacterToOrganisationFragment(
                    orgId
                )
            )
        }

        return binding.root
    }
}