package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(private val repository:CharacterRepository):ViewModelWithCoroutineScope()
{
    val allCharacters:LiveData<List<Character>> = repository.allCharacters

    fun insert(character:Character) = scope.launch(Dispatchers.IO) {
        repository.insert(character)
    }
}