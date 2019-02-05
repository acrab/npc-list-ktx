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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.AddCharacterToOrganisationFragmentArgs
import com.example.android.roomwordnavigation.AddCharacterToOrganisationFragmentDirections
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.characters.CharacterListViewModel
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.injection.ViewModelFactory
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AddCharacterToOrganisationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var organisationDetailsViewModel: OrganisationDetailsViewModel
    private lateinit var characterListViewModel: CharacterListViewModel

    private val args: AddCharacterToOrganisationFragmentArgs by navArgs()

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentCharacterListBinding>(
            inflater,
            R.layout.fragment_character_list,
            container,
            false
        )

        organisationDetailsViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(OrganisationDetailsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        characterListViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(CharacterListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        organisationDetailsViewModel.setOrganisationId(args.organisationId)

        val recyclerView = binding.characterList
        val adapter = CharacterListAdapter(context!!) {
            organisationDetailsViewModel.addToOrganisation(it)
            findNavController().navigateUp()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        characterListViewModel.allWords.observe(this, Observer { characters ->
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