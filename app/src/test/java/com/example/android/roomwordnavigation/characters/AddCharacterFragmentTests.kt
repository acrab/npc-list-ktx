@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.TestApp
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.util.TestFragmentFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(AddCharacterFragmentTests.When_The_Submit_Button_Is_Pressed::class, AddCharacterFragmentTests.When_The_View_Is_Created::class)
class AddCharacterFragmentTests {

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Created
    {
        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<AddCharacterFragment>
        private lateinit var viewModel: CharacterListViewModel
        private lateinit var viewModelFactory : ViewModelProvider.Factory

        @Before
        fun setup() {
            navController = mock()

            viewModel = mock(name = "MockViewModel") {
                on { allCharacters } doReturn mock()
            }

            viewModelFactory = mock{
                on{create<CharacterListViewModel>(any())} doReturn viewModel
            }
            scenario = launchFragmentInContainer<AddCharacterFragment>(
                factory = TestFragmentFactory<AddCharacterFragment>(viewModelFactory)
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }.moveToState(Lifecycle.State.RESUMED)
        }

        @Test
        fun The_Text_Box_Should_Be_Empty()
        {
            onView(withId(R.id.editText)).check(matches(withText("")))
        }

    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Submit_Button_Is_Pressed {

        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<AddCharacterFragment>
        private lateinit var viewModel: CharacterListViewModel
        private lateinit var viewModelFactory : ViewModelProvider.Factory

        @Before
        fun setup() {
            navController = mock()
            viewModel = mock(name = "MockViewModel")

            viewModelFactory = mock{
                on{create<CharacterListViewModel>(com.nhaarman.mockitokotlin2.any())} doReturn viewModel
            }
            scenario = launchFragmentInContainer<AddCharacterFragment>(
                factory = TestFragmentFactory<AddCharacterFragment>(viewModelFactory)
            )
            scenario.onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
            scenario.moveToState(Lifecycle.State.RESUMED)
        }

        @Test
        fun It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(Character(""))
        }

        @Test
        fun It_Should_Navigate_Up() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(navController).navigateUp()
        }
    }
}