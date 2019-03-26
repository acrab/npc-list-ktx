@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
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
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.util.fragmentFactoryWithMockViewModelAndIMM
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AddCharacterEntityFragmentTests.When_The_Submit_Button_Is_Pressed::class,
    AddCharacterEntityFragmentTests.When_The_View_Is_Created::class
)
class AddCharacterEntityFragmentTests {

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Created {
        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<AddCharacterFragment>

        @Before
        fun setup() {
            navController = mock()

            val (ff) = fragmentFactoryWithMockViewModelAndIMM<AddCharacterViewModel>()

            scenario = launchFragmentInContainer<AddCharacterFragment>(
                factory = ff
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }.moveToState(Lifecycle.State.RESUMED)
        }

        @Test
        fun The_Text_Boxes_Should_Be_Empty() {
            onView(withId(R.id.editText)).check(matches(withText("")))
            onView(withId(R.id.notes)).check(matches(withText("")))
            onView(withId(R.id.description)).check(matches(withText("")))
        }

    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Submit_Button_Is_Pressed {

        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<AddCharacterFragment>
        private lateinit var viewModel: AddCharacterViewModel
        private lateinit var inputMethodManager: InputMethodManager

        @Before
        fun setup() {

            navController = mock()

            val (ff, vm, imm) = fragmentFactoryWithMockViewModelAndIMM<AddCharacterViewModel>()
            viewModel = vm
            inputMethodManager = imm

            scenario = launchFragmentInContainer<AddCharacterFragment>(
                factory = ff
            )
            scenario.onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
            scenario.moveToState(Lifecycle.State.RESUMED)

        }

        @Test
        fun If_The_Name_Is_Blank_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("", template = 0))
        }

        @Test
        fun If_The_Name_Has_Contents_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.editText)).perform(ViewActions.typeText("Mixed Case Text"))
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("Mixed Case Text", template = 0))
        }

        @Test
        fun If_The_Description_Is_Blank_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("", description =  "", template = 0))
        }

        @Test
        fun If_The_Description_Has_Contents_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.description)).perform(ViewActions.typeText("Mixed Case Text"))
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("", description =  "Mixed Case Text", template = 0))
        }

        @Test
        fun If_The_Notes_Are_Blank_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("", notes = "", template = 0))
        }

        @Test
        fun If_The_Notes_Have_Contents_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.notes)).perform(ViewActions.typeText("Mixed Case Text"))
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("", notes = "Mixed Case Text", template = 0))
        }

        @Test
        fun If_All_Fields_Have_Contents_It_Should_Create_A_Character_In_The_View_Model() {
            onView(withId(R.id.editText)).perform(ViewActions.typeText("Name Test Text"))

            onView(withId(R.id.description)).perform(ViewActions.typeText("Description Text"))

            onView(withId(R.id.notes)).perform(ViewActions.typeText("Notes Text"))
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(viewModel).insert(CharacterEntity("Name Test Text", "Description Text", "Notes Text", template = 0))
        }

        @Test
        fun It_Should_Navigate_Up() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(navController).navigateUp()
        }

        @Test
        fun It_Should_Close_The_Keyboard() {
            onView(withId(R.id.button)).perform(ViewActions.click())
            verify(inputMethodManager).hideSoftInputFromWindow(any(), any())
        }
    }
}
