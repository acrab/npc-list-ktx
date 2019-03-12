package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.databinding.FragmentSelectableCharacterListBinding
import com.example.android.roomwordnavigation.ui.SelectableCharacterListAdapter
import com.example.android.roomwordnavigation.ui.WithSingleButton
import com.example.android.roomwordnavigation.ui.setupLinearWithAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddCharacterToOrganisationFragment : DaggerFragment(), IWithViewModelFactory, WithSingleButton {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationDetailsViewModel: OrganisationDetailsViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSelectableCharacterListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.view = this
        binding.viewModel = organisationDetailsViewModel

        val adapter = SelectableCharacterListAdapter(requireContext()) { characterId, status ->
            if (status) {
                organisationDetailsViewModel.addToOrganisation(characterId)
            } else {
                organisationDetailsViewModel.removeFromOrganisation(characterId)
            }
        }
        binding.characterList.setupLinearWithAdapter(requireContext(), adapter)

        return binding.root
    }

    override fun onButtonClicked(view: View) {
        view.findNavController()
            .navigate(AddCharacterToOrganisationFragmentDirections.actionAddCharacterToOrganisationFragmentToAddCharacterFragment())
    }
}
