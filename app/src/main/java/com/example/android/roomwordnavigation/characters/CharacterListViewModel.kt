package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CharacterListViewModel @Inject constructor(private val repository:CharacterRepository):ViewModelWithCoroutineScope()
{
    val allWords:LiveData<List<Character>> = repository.allWords

    fun insert(character:Character) = scope.launch(Dispatchers.IO) {
        repository.insert(character)
    }
}