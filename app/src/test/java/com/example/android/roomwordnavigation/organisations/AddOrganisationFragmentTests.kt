@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
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
import com.example.android.roomwordnavigation.util.fragmentFactoryWithMockViewModelAndIMM
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AddOrganisationFragmentTests.When_The_View_Is_Created::class,
    AddOrganisationFragmentTests.When_The_Submit_Button_Is_Clicked::class
)
class AddOrganisationFragmentTests {
    abstract class BaseTestSetup {
        private lateinit var scenario: FragmentScenario<AddOrganisationFragment>
        protected lateinit var organisationListViewModel: OrganisationListViewModel
        protected lateinit var navController: NavController
        protected lateinit var inputMethodManager: InputMethodManager

        fun baseSetup() {

            val (ff, vm, _, imm) = fragmentFactoryWithMockViewModelAndIMM<OrganisationListViewModel>()

            organisationListViewModel = vm
            navController = mock()
            inputMethodManager = imm
            scenario = launchFragmentInContainer<AddOrganisationFragment>(factory = ff)
            scenario.onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Created : BaseTestSetup() {
        @Before
        fun setup() {
            baseSetup()
        }

        @Test
        fun The_Name_Field_Should_Be_Blank() {
            onView(withId(R.id.organisation_title)).check(matches(withText("")))
        }

        @Test
        fun The_Description_Field_Should_Be_Blank() {
            onView(withId(R.id.organisation_description)).check(matches(withText("")))
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Submit_Button_Is_Clicked : BaseTestSetup() {
        @Before
        fun setup() {
            baseSetup()
            onView(withId(R.id.button)).perform(ViewActions.click())
        }

        @Test
        fun It_Should_Create_The_Organisation() {
            verify(organisationListViewModel).insert(any(), any())
        }

        @Test
        fun It_Should_Navigate_Up() {
            verify(navController).navigateUp()
        }

        @Test
        fun It_Should_Close_The_Keyboard() {
            verify(inputMethodManager).hideSoftInputFromWindow(any(), eq(0))
        }
    }
}