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
import com.example.android.roomwordnavigation.characters.CharacterListViewModel
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import com.example.android.roomwordnavigation.ui.WithFAB
import com.example.android.roomwordnavigation.ui.setupLinearWithAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddCharacterToOrganisationFragment : DaggerFragment(), IWithViewModelFactory, WithFAB {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationDetailsViewModel: OrganisationDetailsViewModel by activityViewModels { viewModelFactory }
    private val characterListViewModel: CharacterListViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.view = this
        binding.viewModel = characterListViewModel

        val adapter = CharacterListAdapter(requireContext()) {
            organisationDetailsViewModel.addToOrganisation(it)
            findNavController().navigateUp()
        }
        binding.characterList.setupLinearWithAdapter(requireContext(), adapter)

        return binding.root
    }

    override fun onFABClicked(view: View) {
        view.findNavController()
            .navigate(AddCharacterToOrganisationFragmentDirections.actionAddCharacterToOrganisationFragmentToAddCharacterFragment())
    }
}
