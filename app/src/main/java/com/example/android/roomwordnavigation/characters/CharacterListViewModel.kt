package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class CharacterListViewModel @Inject constructor(private val repository: ICharacterRepository) :
    ViewModelWithCoroutineScope() {
    val allCharacters: LiveData<List<CharacterEntity>> = repository.allCharacters

    fun insert(characterEntity: CharacterEntity) = scope.launch(Dispatchers.IO) {
        repository.insert(characterEntity)
    }

    fun get(characterId: Int) = repository.get(characterId)

    fun update(characterEntity: CharacterEntity) = scope.launch(Dispatchers.IO) {
        repository.update(characterEntity)
    }
}