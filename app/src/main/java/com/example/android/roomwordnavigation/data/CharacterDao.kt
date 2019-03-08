package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android.roomwordnavigation.data.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY name ASC")
    fun getAllCharacters() : LiveData<List<CharacterEntity>>

    @Insert
    fun insert(characterEntity:CharacterEntity)

    @Query("DELETE FROM character_table")
    fun deleteAll()

    @Delete
    fun delete(characterEntity:CharacterEntity)
}