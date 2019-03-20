package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import javax.inject.Inject

@OpenForTesting
class CharacterListViewModel @Inject constructor(private val repository: ICharacterRepository) : ViewModel() {
    val allCharacters: LiveData<List<CharacterEntity>> = repository.allCharacters

    fun insert(characterEntity: CharacterEntity) = repository.insert(characterEntity, viewModelScope)

    fun get(characterId: Int) = repository.get(characterId)

    fun update(characterEntity: CharacterEntity) = repository.update(characterEntity, viewModelScope)

}