package com.example.android.roomwordnavigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.databinding.FragmentAddCharacterBinding

class AddCharacterFragment : Fragment() {
    private lateinit var characterListViewModel: CharacterListViewModel
    private lateinit var inputMethodManager: InputMethodManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddCharacterBinding>(
            inflater,
            R.layout.fragment_add_character,
            container,
            false
        )

        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)!! as InputMethodManager

        characterListViewModel = activity?.run { ViewModelProviders.of(this).get(CharacterListViewModel::class.java) }
            ?: throw Exception("Invalid Activity")

        binding.button.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            characterListViewModel.insert(com.example.android.roomwordnavigation.data.Character(binding.editText.text.toString()))
            it.findNavController().navigateUp()

        }
        return binding.root
    }
}