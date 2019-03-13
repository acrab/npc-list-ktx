package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity

interface ICharacterRepository {
    val allCharacters: LiveData<List<CharacterEntity>>

    fun get(characterId: Int) : LiveData<CharacterEntity>

    @WorkerThread
    fun insert(characterEntity: CharacterEntity)

    @WorkerThread
    fun update(characterEntity: CharacterEntity)
}