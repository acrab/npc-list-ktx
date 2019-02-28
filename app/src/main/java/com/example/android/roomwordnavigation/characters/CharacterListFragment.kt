package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import com.example.android.roomwordnavigation.ui.WithFAB
import com.example.android.roomwordnavigation.ui.setupLinearWithAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CharacterListFragment : DaggerFragment(), IWithViewModelFactory, WithFAB {
    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val characterListViewModel: CharacterListViewModel by activityViewModels { viewModelFactory }
    private lateinit var binding: FragmentCharacterListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.view = this
        binding.viewModel = characterListViewModel
        binding.characterList.setupLinearWithAdapter(requireContext(), CharacterListAdapter(requireContext()))
    }

    override fun onFABClicked(view: View) {
        view.findNavController()
            .navigate(CharacterListFragmentDirections.actionCharacterListFragmentToAddCharacterFragment())
    }
}
