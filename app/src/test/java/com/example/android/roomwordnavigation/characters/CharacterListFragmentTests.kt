@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.TestApp
import com.example.android.roomwordnavigation.util.TestFragmentFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.mockito.internal.matchers.Matches
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(When_The_Add_Button_Is_Clicked::class)
class CharacterListFragmentTests
@RunWith(AndroidJUnit4::class)
@Config(application = TestApp::class)
class When_The_Add_Button_Is_Clicked {
    private lateinit var navController: NavController
    private lateinit var scenario: FragmentScenario<CharacterListFragment>
    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setup() {
        navController = mock()
        viewModel = mock(name = "MockViewModel") {
            on { allCharacters } doReturn mock()
        }
        scenario = launchFragmentInContainer<CharacterListFragment>(
            factory = TestFragmentFactory<CharacterListFragment>(viewModel)
        )
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun It_Should_Navigate_To_The_Add_Character_View() {
        onView(withId(R.id.fab)).perform(ViewActions.click())
        verify(navController).navigate(CharacterListFragmentDirections.actionCharacterListFragmentToAddCharacterFragment())
    }
}