@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import org.junit.Before
import org.junit.Test

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
    fun It_Should_Request_All_Words() {
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

class When_A_Character_Is_Inserted {
    private lateinit var characterRepository: ICharacterRepository
    private lateinit var subject: CharacterListViewModel

    @Before
    fun setup() {
        characterRepository = mock()
        subject = CharacterListViewModel(characterRepository)
    }

    @Test
    fun It_Is_Added_To_The_Repository() {
        val data = Character("Test name")
        subject.insert(data)
        verifyBlocking(characterRepository) { insert(data) }
    }
}