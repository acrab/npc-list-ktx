@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.TestApp
import com.example.android.roomwordnavigation.data.entities.OrganisationSummary
import com.example.android.roomwordnavigation.util.fragmentFactoryWithMockViewModel
import com.example.android.roomwordnavigation.util.withRecyclerView
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
@Suite.SuiteClasses(OrganisationListFragmentTests.When_The_View_Is_Loaded::class,
    OrganisationListFragmentTests.When_The_Add_Button_Is_Clicked::class,
    OrganisationListFragmentTests.When_An_Organisation_Is_Selected::class,
    OrganisationListFragmentTests.When_The_Organisation_List_Is_Updated::class)
class OrganisationListFragmentTests
{
    abstract class CommonSetup
    {
        private lateinit var subject: FragmentScenario<OrganisationListFragment>
        protected lateinit var navController: NavController
        protected lateinit var viewModel: OrganisationListViewModel
        protected lateinit var allOrganisations : LiveData<List<OrganisationSummary>>

        protected fun commonSetup()
        {
            navController = mock()

            allOrganisations = mock()

            if(!this::viewModel.isInitialized) {
                viewModel = mock {
                    on { allOrganisations } doReturn allOrganisations
                }
            }

            val (ff) = fragmentFactoryWithMockViewModel(viewModel)

            subject = launchFragmentInContainer<OrganisationListFragment>(
                factory = ff
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Loaded : OrganisationListFragmentTests.CommonSetup()
    {
        @Before
        fun setup()
        {
            commonSetup()
        }

        @Test
        fun It_Should_Observe_The_Organisation_List()
        {
            verify(viewModel).allOrganisations
            verify(allOrganisations).observe(any(), any())

        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Organisation_List_Is_Updated : CommonSetup()
    {
        private lateinit var organisationData : MutableLiveData<List<OrganisationSummary>>

        @Before
        fun setup()
        {
            organisationData = MutableLiveData()

            viewModel = mock{
                on { allOrganisations } doReturn organisationData
            }

            commonSetup()

            organisationData.postValue(listOf(OrganisationSummary("Bob's club",1)))
        }

        @Test
        fun The_Adapter_Should_Have_The_Item()
        {
            onView(withId(R.id.organisation_list)).check { view, _ ->
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter!!
                assert(adapter.itemCount == 1)
            }
        }

        @Test
        fun It_Should_Display_The_Organisations()
        {
            onView(withRecyclerView(R.id.organisation_list).atPositionOnView(0, R.id.word_view)).check(matches(withText("Bob's club")))
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Add_Button_Is_Clicked : CommonSetup()
    {
        @Before
        fun setup()
        {
            commonSetup()
            onView(withId(R.id.fab)).perform(ViewActions.click())
        }

        @Test
        fun It_Should_Navigate_To_The_Add_Organisation_View()
        {
            verify(navController).navigate(OrganisationListFragmentDirections.actionOrganisationListFragmentToAddOrganisationFragment())
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_An_Organisation_Is_Selected : CommonSetup()
    {
        @Before
        fun setup()
        {
            val organisationData = MutableLiveData<List<OrganisationSummary>>()

            viewModel = mock{
                on { allOrganisations } doReturn organisationData
            }

            commonSetup()

            organisationData.postValue(listOf(OrganisationSummary("Bob's club", 1)))

            onView(withRecyclerView(R.id.organisation_list).atPosition(0)).perform(ViewActions.click())
        }

        @Test
        fun It_Should_Navigate_To_The_Organisation_Details_View()
        {
            verify(navController).navigate(OrganisationListFragmentDirections.actionShowSelectedOrganisation(1))
        }
    }
}