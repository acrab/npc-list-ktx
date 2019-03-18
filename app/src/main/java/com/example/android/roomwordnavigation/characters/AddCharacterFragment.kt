package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.databinding.FragmentAddCharacterBinding
import com.example.android.roomwordnavigation.inputManager
import com.example.android.roomwordnavigation.ui.WithCustomButton
import com.example.android.roomwordnavigation.ui.asString
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddCharacterFragment : DaggerFragment(), IWithBoth, WithCustomButton {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    override lateinit var immFactory: InputMethodManagerFactory

    private val characterListViewModel: CharacterListViewModel by viewModels { viewModelFactory }
    private val inputMethodManager: InputMethodManager by inputManager { immFactory }

    private lateinit var binding: FragmentAddCharacterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonText = resources.getString(R.string.add_character)
        binding.view = this
    }

    override lateinit var buttonText: String

    override fun onButtonClicked(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        binding.apply {

            characterListViewModel.insert(CharacterEntity(editText.asString(), description.asString(), notes.asString()))
        }
        view.findNavController().navigateUp()
    }
}