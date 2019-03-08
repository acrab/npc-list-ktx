package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity

interface ICharacterRepository {
    val allCharacters: LiveData<List<CharacterEntity>>

    fun insert(characterEntity: CharacterEntity)
}