package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val CharacterDao: CharacterDao) : ICharacterRepository {
    override val allCharacters: LiveData<List<Character>> = CharacterDao.getAllCharacters()

    @WorkerThread
    override fun insert(character: Character)
    {
        CharacterDao.insert(character)
    }
}