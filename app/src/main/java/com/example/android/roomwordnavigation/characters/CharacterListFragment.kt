package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import com.example.android.roomwordnavigation.ui.WithSingleButton
import com.example.android.roomwordnavigation.ui.setupLinearWithAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CharacterListFragment : DaggerFragment(), IWithViewModelFactory, WithSingleButton {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val characterListViewModel: CharacterListViewModel by activityViewModels { viewModelFactory }
    private lateinit var binding: FragmentCharacterListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = characterListViewModel
        binding.characterList.setupLinearWithAdapter(requireContext(), CharacterListAdapter(requireContext())
        {
            view.findNavController().navigate(CharacterListFragmentDirections.editCharacter(it.id))
        })
    }

    override fun onButtonClicked(view: View) {
        view.findNavController()
            .navigate(CharacterListFragmentDirections.addCharacter())
    }
}
