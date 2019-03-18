package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.databinding.FragmentAddCharacterBinding
import com.example.android.roomwordnavigation.inputManager
import com.example.android.roomwordnavigation.ui.WithCustomButton
import com.example.android.roomwordnavigation.ui.asString
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EditCharacterFragment : DaggerFragment(), IWithBoth, WithCustomButton {
    @Inject
    override lateinit var immFactory: InputMethodManagerFactory

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val characterListViewModel: EditCharacterViewModel by viewModels { viewModelFactory }
    private val inputMethodManager: InputMethodManager by inputManager { immFactory }
    private val args: EditCharacterFragmentArgs by navArgs()

    private lateinit var binding: FragmentAddCharacterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListViewModel.characterId.value = args.characterId
        buttonText = resources.getString(R.string.save_changes)
        binding.view = this
        binding.lifecycleOwner = this

        characterListViewModel.characterDetails.observe(this, Observer {
            binding.apply {
                editText.setText(it.name)
                description.setText(it.description)
                notes.setText(it.notes)
            }
        })
    }

    override lateinit var buttonText: String

    override fun onButtonClicked(view: View) {

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        binding.apply {
            characterListViewModel.onCharacterEdited(editText.asString(), description.asString(), notes.asString())
        }
        view.findNavController().navigateUp()
    }
}