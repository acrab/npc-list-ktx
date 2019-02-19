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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.characters.CharacterListViewModel
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddCharacterToOrganisationFragment : DaggerFragment(), IWithViewModelFactory {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationDetailsViewModel: OrganisationDetailsViewModel by activityViewModels { viewModelFactory }
    private val characterListViewModel: CharacterListViewModel by activityViewModels { viewModelFactory }

    private val args: AddCharacterToOrganisationFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        organisationDetailsViewModel.organisationId.postValue(args.organisationId)

        val recyclerView = binding.characterList
        val adapter = CharacterListAdapter(context!!) {
            organisationDetailsViewModel.addToOrganisation(it)
            findNavController().navigateUp()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        characterListViewModel.allCharacters.observe(this, Observer { characters ->
            characters?.let {
                adapter.setCharacters(it)
            }
        })

        binding.fab.setOnClickListener {
            it.findNavController()
                .navigate(AddCharacterToOrganisationFragmentDirections.actionAddCharacterToOrganisationFragmentToAddCharacterFragment())
        }

        return binding.root
    }
}