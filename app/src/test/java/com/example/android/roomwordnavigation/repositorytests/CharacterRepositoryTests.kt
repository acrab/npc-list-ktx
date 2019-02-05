@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.repositorytests

import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.CharacterDao
import com.example.android.roomwordnavigation.data.CharacterRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class When_The_Character_Repository_Is_Created{

    private lateinit var characterDao: CharacterDao

    @Before
    fun Setup(){
        characterDao = mock()
    }

    @Test
    fun It_Gets_All_Characters_From_The_Dao()
    {
        CharacterRepository(characterDao)

        verify(characterDao).getAllCharacters()
    }
}

class When_A_Character_Is_Inserted_Into_The_Repository{
    private lateinit var characterDao: CharacterDao
    private lateinit var subject: CharacterRepository
    @Before
    fun Setup(){
        characterDao = mock()
        subject = CharacterRepository(characterDao)
    }

    @Test
    fun It_Is_Inserted_Into_The_Dao()
    {
        val toInsert = Character("Test Character")
        subject.insert(toInsert)
        verify(characterDao).insert(toInsert)
    }
}