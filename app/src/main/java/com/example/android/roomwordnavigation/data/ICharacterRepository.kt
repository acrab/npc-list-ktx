package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import kotlinx.coroutines.CoroutineScope

interface ICharacterRepository {
    val allCharacters: LiveData<List<CharacterEntity>>

    fun get(characterId: Long) : LiveData<CharacterEntity>

    /**
     * @param onCharacterInserted An optional callback providing the ID of the newly inserted character
     */
    fun insert(characterEntity: CharacterEntity, coroutineScope: CoroutineScope, onCharacterInserted:(characterId:Long)->Unit = {})

    fun update(characterEntity: CharacterEntity, coroutineScope: CoroutineScope)
}
