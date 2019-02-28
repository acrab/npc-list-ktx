package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import com.example.android.roomwordnavigation.ui.WithFAB
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CharacterListFragment : DaggerFragment(), IWithViewModelFactory, WithFAB {
    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val characterListViewModel: CharacterListViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        binding.view = this
        val recyclerView = binding.characterList
        val adapter = CharacterListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        characterListViewModel.allCharacters.observe(this, Observer { characters ->
            characters?.let {
                adapter.setCharacters(it)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onFABClicked(view: View) {
        view.findNavController()
            .navigate(CharacterListFragmentDirections.actionCharacterListFragmentToAddCharacterFragment())
    }
}