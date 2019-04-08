package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val CharacterDao: CharacterDao) : ICharacterRepository {
    override val allCharacters: LiveData<List<CharacterEntity>> = CharacterDao.getAllCharacters()

    override fun get(characterId: Long) = CharacterDao.getCharacter(characterId)

    override fun insert(characterEntity: CharacterEntity, coroutineScope: CoroutineScope, onCharacterInserted:(characterId:Long)->Unit) {
        coroutineScope.launch {
            val id = CharacterDao.insert(characterEntity)
            onCharacterInserted(id)
        }
    }

    override fun update(characterEntity: CharacterEntity, coroutineScope: CoroutineScope) {
        coroutineScope.launch { CharacterDao.update(characterEntity) }
    }
}