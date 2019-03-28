@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.observedValue
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EditCharacterViewModelTests.When_The_View_Model_Is_Created::class,
    EditCharacterViewModelTests.When_The_Character_Id_Is_Updated::class,
    EditCharacterViewModelTests.When_A_Character_Is_Edited::class
)
class EditCharacterViewModelTests {

    class When_The_View_Model_Is_Created {

        private lateinit var subject: EditCharacterViewModel
        private lateinit var repository: ICharacterRepository

        @Before
        fun setup() {
            repository = mock()
            subject = EditCharacterViewModel(repository)
        }

        @Test
        fun The_Character_Id_Should_Be_Zero() {
            assertEquals(0L, subject.characterId.value)
        }

        @Test
        fun The_Character_Details_Should_Be_Null() {
            assertNull(subject.characterDetails.value)
            verifyZeroInteractions(repository)
        }
    }

    class When_The_Character_Id_Is_Updated {

        @get:Rule
        val instantTaskExecutorRule = InstantTaskExecutorRule()

        private lateinit var subject: EditCharacterViewModel
        private lateinit var repository: ICharacterRepository
        private val bob = CharacterEntity("Bob", id = 1)
        private val characterData = MutableLiveData(bob)
        @Before
        fun setup() {

            repository = mock {
                on { get(1) } doReturn characterData
            }

            subject = EditCharacterViewModel(repository)

            subject.characterId.value = 1
        }

        @Test
        fun The_Data_Should_Be_Available() {
            //Verify it's the right data
            val data = subject.characterDetails.observedValue()
            assertEquals(bob, data)

            //Verify where the data came from
            //Has to be done after, otherwise the lazy access hasn't been accessed yet.
            verify(repository).get(1)
        }
    }

    class When_A_Character_Is_Edited {

        @get:Rule
        val instantTaskExecutorRule = InstantTaskExecutorRule()

        private lateinit var subject: EditCharacterViewModel
        private lateinit var repository: ICharacterRepository
        private val bob = CharacterEntity("Bob", id = 1)
        private val characterData = MutableLiveData(bob)
        @Before
        fun setup() {

            repository = mock {
                on { get(1) } doReturn characterData
            }

            subject = EditCharacterViewModel(repository)

            subject.characterId.value = 1
        }

        @Test
        fun It_Should_Update_The_Repository() {

            subject.onCharacterEdited("Bobby", "Up", "Date")
            verify(repository).update(eq(CharacterEntity("Bobby", "Up", "Date", id = 1)), any())
        }
    }
}