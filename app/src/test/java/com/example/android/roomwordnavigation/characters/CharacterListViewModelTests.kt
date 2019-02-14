@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(When_The_View_Model_Is_Created::class, When_A_Character_Is_Inserted::class)
class CharacterListViewModelTests

class When_The_View_Model_Is_Created {
    private lateinit var data: LiveData<List<Character>>
    private lateinit var characterRepository: ICharacterRepository

    @Before
    fun setup() {
        data = mock()

        characterRepository = mock {
            on { allCharacters } doReturn data
        }
    }

    @Test
    fun It_Should_Request_All_Characters() {
        CharacterListViewModel(characterRepository)
        verify(characterRepository).allCharacters
    }

    @Test
    fun The_Data_Is_Available() {
        val vm = CharacterListViewModel(characterRepository)
        val x = vm.allCharacters
        assert(data == x)
    }
}

@RunWith(AndroidJUnit4::class)
class When_A_Character_Is_Inserted {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var characterRepository: ICharacterRepository
    private lateinit var subject: CharacterListViewModel

    @Before
    fun setup() {
        characterRepository = mock()
        subject = CharacterListViewModel(characterRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun It_Is_Added_To_The_Repository() = runBlocking {
        val data = Character("Test name")
        subject.insert(data, Dispatchers.Unconfined)
        verify(characterRepository).insert(data)
    }
}