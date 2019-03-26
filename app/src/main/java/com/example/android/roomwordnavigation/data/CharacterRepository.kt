package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val CharacterDao: CharacterDao) : ICharacterRepository {
    override val allCharacters: LiveData<List<CharacterEntity>> = CharacterDao.getAllCharacters()

    override fun get(characterId: Long) = CharacterDao.getCharacter(characterId)

    @WorkerThread
    override fun insert(characterEntity: CharacterEntity) = CharacterDao.insert(characterEntity)

    @WorkerThread
    override fun update(characterEntity: CharacterEntity) = CharacterDao.update(characterEntity)
}