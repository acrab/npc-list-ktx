@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.TestApp
import com.example.android.roomwordnavigation.data.MembershipStatus
import com.example.android.roomwordnavigation.util.FragmentWithViewModelFactory
import com.example.android.roomwordnavigation.util.withRecyclerView
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AddCharacterEntityToOrganisationFragmentTests.When_The_View_Is_Created::class,
    AddCharacterEntityToOrganisationFragmentTests.When_The_List_Of_Characters_Is_Populated::class,
    AddCharacterEntityToOrganisationFragmentTests.When_An_Unselected_Character_Entity_Is_Selected::class,
    AddCharacterEntityToOrganisationFragmentTests.When_The_Add_Character_Entity_Button_Is_Selected::class,
    AddCharacterEntityToOrganisationFragmentTests.When_A_Selected_Character_Entity_Is_Selected::class
)
class AddCharacterEntityToOrganisationFragmentTests {

    abstract class BaseTestSetup {
        protected lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<AddCharacterToOrganisationFragment>
        protected lateinit var membershipStatuses: LiveData<List<MembershipStatus>>
        protected lateinit var organisationDetailsViewModel: OrganisationDetailsViewModel
        private lateinit var viewModelFactory: ViewModelProvider.Factory

        fun baseSetup(membershipStatuses: LiveData<List<MembershipStatus>>) {
            this.membershipStatuses = membershipStatuses
            internalSetup()
        }

        fun baseSetup() {
            membershipStatuses = mock()
            internalSetup()
        }

        private fun internalSetup() {
            navController = mock()

            organisationDetailsViewModel = mock {
                on { organisationId } doReturn mock()
                on { membershipStatuses } doReturn membershipStatuses
            }

            viewModelFactory = mock {
                onGeneric { create<ViewModel>(any()) } doAnswer {
                    when (it.getArgument<Class<ViewModel>>(0)) {
                        OrganisationDetailsViewModel::class.java -> organisationDetailsViewModel
                        else -> null
                    }
                }
            }

            scenario = launchFragmentInContainer<AddCharacterToOrganisationFragment>(
                factory = FragmentWithViewModelFactory(viewModelFactory)
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }.moveToState(Lifecycle.State.RESUMED)
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Created : AddCharacterEntityToOrganisationFragmentTests.BaseTestSetup() {
        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        @Before
        fun setup() {
            super.baseSetup()
            //Doesn't need any special setup
        }

        @Test
        fun It_Should_Observe_MembershipStatuses() {
            verify(organisationDetailsViewModel).membershipStatuses
            verify(membershipStatuses).observe(any(), any())
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_List_Of_Characters_Is_Populated : BaseTestSetup() {

        private lateinit var mutableCharacterData: MutableLiveData<List<MembershipStatus>>

        @Before
        fun setup() {
            mutableCharacterData = MutableLiveData()
            baseSetup(mutableCharacterData)

            mutableCharacterData.postValue(
                listOf(
                    MembershipStatus(1, "Bob", true),
                    MembershipStatus(2, "Dick", false),
                    MembershipStatus(3, "Harry", false)
                )
            )
        }

        @Test
        fun The_Adapter_Should_Contain_Them() {

            onView(withId(R.id.character_list)).check { view, _ ->
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter!!
                assert(adapter.itemCount == 3)
            }
        }

        @Test
        fun They_Should_Be_Displayed() {

            onView(
                withRecyclerView(R.id.character_list).atPositionOnView(
                    0, R.id.checkBox
                )
            ).check(matches(allOf(withText("Bob"), isChecked())))

            onView(
                withRecyclerView(R.id.character_list).atPositionOnView(
                    1, R.id.checkBox
                )
            ).check(matches(allOf(withText("Dick"), isNotChecked())))
            onView(
                withRecyclerView(R.id.character_list).atPositionOnView(
                    2, R.id.checkBox
                )
            ).check(matches(allOf(withText("Harry"), isNotChecked())))
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_An_Unselected_Character_Entity_Is_Selected : BaseTestSetup() {

        private lateinit var mutableCharacterData: MutableLiveData<List<MembershipStatus>>

        private lateinit var harry : MembershipStatus

        @Before
        fun setup() {

            harry = MembershipStatus(1, "Harry", false)

            mutableCharacterData = MutableLiveData()
            baseSetup(mutableCharacterData)

            mutableCharacterData.postValue(listOf(harry))
            onView(withRecyclerView(R.id.character_list).atPosition(0)).perform(ViewActions.click())
        }

        @Test
        fun It_Should_Add_The_Character_To_The_Organisation() {
            verify(organisationDetailsViewModel).addToOrganisation(eq(harry.characterId))
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_A_Selected_Character_Entity_Is_Selected : BaseTestSetup() {

        private lateinit var mutableCharacterData: MutableLiveData<List<MembershipStatus>>

        private lateinit var harry : MembershipStatus

        @Before
        fun setup() {

            harry = MembershipStatus(1, "Harry", true)

            mutableCharacterData = MutableLiveData()
            baseSetup(mutableCharacterData)

            mutableCharacterData.postValue(listOf(harry))
            onView(withRecyclerView(R.id.character_list).atPosition(0)).perform(ViewActions.click())
        }

        @Test
        fun It_Should_Remove_The_Character_From_The_Organisation() {
            verify(organisationDetailsViewModel).removeFromOrganisation(eq(harry.characterId))
        }
    }


    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Add_Character_Entity_Button_Is_Selected :
        AddCharacterEntityToOrganisationFragmentTests.BaseTestSetup() {

        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        @Before
        fun setup() {
            baseSetup()
            //Doesn't need any special setup
        }

        @Test
        fun It_Should_Navigate_To_The_Add_Character_Screen() {
            onView(withId(R.id.fab)).perform(ViewActions.click())
            verify(navController).navigate(AddCharacterToOrganisationFragmentDirections.actionAddCharacterToOrganisationFragmentToAddCharacterFragment())
        }
    }
}
