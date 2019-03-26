package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class AddCharacterViewModel @Inject constructor(
    private val characterRepository: ICharacterRepository
) : ViewModel() {
    fun insert(characterEntity: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.insert(characterEntity)
    }
}