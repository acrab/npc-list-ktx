@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(When_The_Character_Repository_Is_Created::class, When_A_Character_Is_Created::class)
class CharacterRepositoryTests

class When_The_Character_Repository_Is_Created {

    private lateinit var characterDao: CharacterDao

    private lateinit var data: LiveData<List<Character>>

    @Before
    fun Setup() {
        data = mock()
        characterDao = mock {
            on { getAllCharacters() } doReturn data
        }
    }

    @Test
    fun It_Gets_All_Characters_From_The_Dao() {
        CharacterRepository(characterDao)
        verify(characterDao).getAllCharacters()
    }

    @Test
    fun The_Data_Is_Available() {
        val repo = CharacterRepository(characterDao)
        val x = repo.allCharacters
        assert(x == data)
    }
}

class When_A_Character_Is_Created {
    private lateinit var characterDao: CharacterDao
    private lateinit var subject: CharacterRepository
    @Before
    fun Setup() {
        characterDao = mock()
        subject = CharacterRepository(characterDao)
    }

    @Test
    fun It_Is_Inserted_Into_The_Dao() {
        val toInsert = Character("Test Character")
        subject.insert(toInsert)
        verify(characterDao).insert(toInsert)
    }
}