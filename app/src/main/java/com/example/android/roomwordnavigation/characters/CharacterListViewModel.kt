package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.ICharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CharacterListViewModel @Inject constructor(private val repository: ICharacterRepository):ViewModelWithCoroutineScope()
{
    val allCharacters:LiveData<List<Character>> = repository.allCharacters

    fun insert(character:Character, context:CoroutineContext = Dispatchers.IO) = scope.launch(context) {
        repository.insert(character)
    }
}