package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.databinding.FragmentAddCharacterBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddCharacterFragment : DaggerFragment(), IWithBoth {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    override lateinit var immFactory : InputMethodManagerFactory

    private val characterListViewModel: CharacterListViewModel by viewModels{viewModelFactory}
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddCharacterBinding>(
            inflater,
            R.layout.fragment_add_character,
            container,
            false
        )

        inputMethodManager = immFactory.get(context!!)

        binding.button.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            characterListViewModel.insert(com.example.android.roomwordnavigation.data.CharacterEntity(binding.editText.text.toString()))
            it.findNavController().navigateUp()
        }

        return binding.root
    }
}