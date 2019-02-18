package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData

interface ICharacterRepository {
    val allCharacters: LiveData<List<CharacterEntity>>

    fun insert(characterEntity: CharacterEntity)
}