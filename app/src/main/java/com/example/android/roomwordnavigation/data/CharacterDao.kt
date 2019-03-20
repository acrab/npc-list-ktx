package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.roomwordnavigation.data.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY name ASC")
    fun getAllCharacters(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM character_table WHERE id = :id")
    fun getCharacter(id: Int): LiveData<CharacterEntity>

    @Insert
    suspend fun insert(characterEntity: CharacterEntity)

    @Update
    suspend fun update(characterEntity: CharacterEntity)

    @Query("DELETE FROM character_table")
    fun deleteAll()

    @Delete
    fun delete(characterEntity: CharacterEntity)
}