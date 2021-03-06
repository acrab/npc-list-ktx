package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.databinding.FragmentOrganisationListBinding
import com.example.android.roomwordnavigation.ui.OrganisationListAdapter
import com.example.android.roomwordnavigation.ui.WithSingleButton
import com.example.android.roomwordnavigation.ui.setupLinearWithAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OrganisationListFragment : DaggerFragment(), IWithViewModelFactory, WithSingleButton {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationListViewModel: OrganisationListViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: FragmentOrganisationListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrganisationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = organisationListViewModel
        binding.view = this

        binding.organisationList.setupLinearWithAdapter(requireContext(), OrganisationListAdapter(context!!) {
            findNavController().navigate(OrganisationListFragmentDirections.actionShowSelectedOrganisation(it.id))
        })
    }

    override fun onButtonClicked(view: View) {
        view.findNavController()
            .navigate(OrganisationListFragmentDirections.actionOrganisationListFragmentToAddOrganisationFragment())
    }
}