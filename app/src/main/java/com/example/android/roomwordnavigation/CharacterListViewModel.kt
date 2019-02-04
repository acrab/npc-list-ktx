package com.example.android.roomwordnavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.AppDatabase
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CharacterListViewModel (application: Application):AndroidViewModel(application)
{
    private val repository:CharacterRepository
    val allWords:LiveData<List<Character>>

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init{
        val dao = AppDatabase.getDatabase(application).characterDao()
        repository = CharacterRepository(dao)
        allWords = repository.allWords
    }

    fun insert(character:Character) = scope.launch(Dispatchers.IO) {
        repository.insert(character)
    }
}