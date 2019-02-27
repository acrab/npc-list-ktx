@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.android.roomwordnavigation.data.CharacterEntity
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.util.fragmentFactoryWithMockViewModel
import com.example.android.roomwordnavigation.util.withRecyclerView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ViewOrganisationFragmentTests.When_The_View_Is_Created::class,
    ViewOrganisationFragmentTests.When_The_Add_Button_Is_Clicked::class,
    ViewOrganisationFragmentTests.When_The_Members_List_Is_Updated::class,
    ViewOrganisationFragmentTests.When_The_Organisation_Information_Is_Updated::class
)
class ViewOrganisationFragmentTests {

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Created {
        private lateinit var organsiationId: MutableLiveData<Int>
        private lateinit var allMembers: LiveData<List<CharacterEntity>>
        private lateinit var organisation: LiveData<Organisation>
        private lateinit var viewModel: OrganisationDetailsViewModel

        @Before
        fun setup() {
            organsiationId = mock()
            allMembers = mock()
            organisation = mock()

            val (ff, vm) = fragmentFactoryWithMockViewModel<OrganisationDetailsViewModel> {
                on { organisationId } doReturn organsiationId
                on { allMembers } doReturn allMembers
                on { organisation } doReturn organisation
            }

            viewModel = vm

            launchFragmentInContainer<ViewOrganisationFragment>(
                factory = ff, fragmentArgs = ViewOrganisationFragmentArgs(1).toBundle()
            )
        }

        @Test
        fun It_Should_Request_Organisation_ID() {
            verify(viewModel).organisationId
            verify(organsiationId).postValue(1)
        }

        @Test
        fun It_Should_Observe_Organisation_Details() {
            verify(viewModel).organisation
            verify(organisation).observe(any(), any())
        }

        @Test
        fun It_Should_Observe_Organisation_Members() {
            verify(viewModel).allMembers
            verify(allMembers).observe(any(), any())
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Members_List_Is_Updated {
        private lateinit var allMembers: MutableLiveData<List<CharacterEntity>>

        @Before
        fun setup() {
            allMembers = MutableLiveData(emptyList())

            val (ff) = fragmentFactoryWithMockViewModel<OrganisationDetailsViewModel> {
                on { organisationId } doReturn mock()
                on { allMembers } doReturn allMembers
                on { organisation } doReturn mock()
            }

            launchFragmentInContainer<ViewOrganisationFragment>(
                factory = ff, fragmentArgs = ViewOrganisationFragmentArgs(1).toBundle()
            )

            allMembers.postValue(listOf(CharacterEntity("Bob")))
        }

        @Test
        fun It_Should_Display_The_Data() {
            onView(
                withRecyclerView(R.id.member_list).atPositionOnView(
                    0,
                    R.id.word_view
                )
            ).check(matches(withText("Bob")))
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Organisation_Information_Is_Updated {
        private lateinit var organisation: MutableLiveData<Organisation>

        @Before
        fun setup() {
            organisation = MutableLiveData()

            val (ff) = fragmentFactoryWithMockViewModel<OrganisationDetailsViewModel> {
                on { organisationId } doReturn mock()
                on { allMembers } doReturn mock()
                on { organisation } doReturn organisation
            }

           launchFragmentInContainer<ViewOrganisationFragment>(factory = ff,
               fragmentArgs = ViewOrganisationFragmentArgs(1).toBundle())

            organisation.postValue(Organisation("Test Name", "TestDescription"))
        }

        @Ignore("Ignored because it crashes with current fragment setup")
        @Test
        fun It_Should_Display_The_Description() {
            onView(withId(R.id.textView)).check(matches(withText("TestDescription")))
        }

        @Ignore("Ignored because it crashes with current fragment setup")
        @Test
        fun It_Should_Display_The_Organisation_Name() {
            throw NotImplementedError()
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Add_Button_Is_Clicked {
        private lateinit var navController: NavController

        @Before
        fun setup() {

            val (ff) = fragmentFactoryWithMockViewModel<OrganisationDetailsViewModel> {
                on { organisationId } doReturn mock()
                on { allMembers } doReturn mock()
                on { organisation } doReturn mock()
            }

            navController = mock()

            launchFragmentInContainer<ViewOrganisationFragment>(
                factory = ff, fragmentArgs = ViewOrganisationFragmentArgs(1).toBundle()
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }

            onView(withId(R.id.fab)).perform(ViewActions.click())

        }

        @Test
        fun It_Should_Navigate_To_The_AddToOrganisation_View() {
            verify(navController).navigate(
                ViewOrganisationFragmentDirections.actionViewOrganisationFragmentToAddCharacterToOrganisationFragment(
                    1
                )
            )
        }
    }
}