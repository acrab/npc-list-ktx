package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CharacterRepository(private val CharacterDao: CharacterDao) {
    val allWords: LiveData<List<Character>> = CharacterDao.getAllWords()

    @WorkerThread
    fun insert(character: Character)
    {
        CharacterDao.insert(character)
    }
}