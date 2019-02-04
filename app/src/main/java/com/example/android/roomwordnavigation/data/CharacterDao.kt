package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY name ASC")
    fun getAllWords() : LiveData<List<Character>>

    @Insert
    fun insert(character:Character)

    @Query("DELETE FROM character_table")
    fun deleteAll()

    @Delete
    fun delete(character:Character)
}