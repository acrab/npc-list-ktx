package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import kotlinx.coroutines.CoroutineScope

interface ICharacterRepository {
    val allCharacters: LiveData<List<CharacterEntity>>

    fun get(characterId: Int) : LiveData<CharacterEntity>

    fun insert(characterEntity: CharacterEntity, coroutineScope: CoroutineScope)

    fun update(characterEntity: CharacterEntity, coroutineScope: CoroutineScope)
}