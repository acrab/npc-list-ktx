@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.util.TestCoroutineScopeRule
import com.example.android.roomwordnavigation.util.testCoroutineScope
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CharacterEntityRepositoryTests.When_The_Character_Entity_Repository_Is_Created::class,
    CharacterEntityRepositoryTests.When_A_Character_Entity_Is_Created::class,
    CharacterEntityRepositoryTests.When_A_Character_Entity_Is_Requested::class,
    CharacterEntityRepositoryTests.When_A_Character_Entity_Is_Updated::class
)
class CharacterEntityRepositoryTests {
    class When_The_Character_Entity_Repository_Is_Created {

        private lateinit var characterDao: CharacterDao

        private lateinit var data: LiveData<List<CharacterEntity>>

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

    class When_A_Character_Entity_Is_Created {
        private lateinit var characterDao: CharacterDao
        private lateinit var subject: CharacterRepository

        @get:Rule
        val scopeRule = TestCoroutineScopeRule()
        
        @ExperimentalCoroutinesApi
        @Before
        fun Setup() {
            characterDao = mock()
            subject = CharacterRepository(characterDao)
        }

        @Test
        fun It_Is_Inserted_Into_The_Dao() = runBlocking {
            val toInsert = CharacterEntity("Test CharacterEntity")
            subject.insert(toInsert, scopeRule.scope)
            verify(characterDao).insert(toInsert)
        }
    }

    class When_A_Character_Entity_Is_Requested {
        private lateinit var characterDao: CharacterDao
        private lateinit var subject: CharacterRepository
        private lateinit var data: LiveData<CharacterEntity>
        @Before
        fun Setup() {

            data = mock()

            characterDao = mock {
                on { getCharacter(0) } doReturn data
            }
            subject = CharacterRepository(characterDao)
        }

        @Test
        fun It_Is_Requested_From_The_Dao() {
            subject.get(0)
            verify(characterDao).getCharacter(0)
        }

        @Test
        fun The_Data_Is_Returned() {
            val x = subject.get(0)
            assertEquals(data, x)
        }
    }

    class When_A_Character_Entity_Is_Updated {
        private lateinit var characterDao: CharacterDao
        private lateinit var subject: CharacterRepository

        @get:Rule
        val scopeRule = TestCoroutineScopeRule()

        @ExperimentalCoroutinesApi
        @Before
        fun Setup() {

            characterDao = mock()
            subject = CharacterRepository(characterDao)

        }

        @Test
        fun The_Updated_Data_Is_Passed_To_The_Dao() = runBlocking {
            val toUpdate = CharacterEntity("Bob", id = 1)
            subject.update(toUpdate, scopeRule.scope)
            verify(characterDao).update(toUpdate)
        }
    }
}