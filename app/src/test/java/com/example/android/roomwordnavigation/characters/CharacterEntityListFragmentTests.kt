@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
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
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.util.fragmentFactoryWithMockViewModel
import com.example.android.roomwordnavigation.util.withRecyclerView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CharacterEntityListFragmentTests.When_The_View_Is_Created::class,
    CharacterEntityListFragmentTests.When_The_Characters_List_Is_Updated_With_One_Item::class,
    CharacterEntityListFragmentTests.When_The_Characters_List_Is_Updated_With_Many_Items::class,
    CharacterEntityListFragmentTests.When_The_Add_Button_Is_Clicked::class
)
class CharacterEntityListFragmentTests {
    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_View_Is_Created {
        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<CharacterListFragment>
        private lateinit var viewModel: CharacterListViewModel
        private lateinit var viewModelFactory: ViewModelProvider.Factory

        @Before
        fun setup() {
            navController = mock()

            val(ff, vm, vmf) = fragmentFactoryWithMockViewModel<CharacterListViewModel>{
                on { allCharacters } doReturn mock()
            }

            viewModel = vm

            viewModelFactory = vmf

            scenario = launchFragmentInContainer<CharacterListFragment>(
                factory = ff
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }.moveToState(Lifecycle.State.RESUMED)
        }

        @Test
        fun It_Should_Create_The_View_Model() {
            verify(viewModelFactory).create(CharacterListViewModel::class.java)
        }

        @Test
        fun It_Should_Request_All_Characters() {
            verify(viewModel).allCharacters
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Characters_List_Is_Updated_With_One_Item {

        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<CharacterListFragment>
        private lateinit var characterEntityData: MutableLiveData<List<CharacterEntity>>
        private lateinit var viewModel: CharacterListViewModel
        private lateinit var viewModelFactory: ViewModelProvider.Factory

        @Before
        fun setup() {
            navController = mock()
            characterEntityData = MutableLiveData(emptyList())

            val(ff, vm, vmf) = fragmentFactoryWithMockViewModel<CharacterListViewModel>{
                on { allCharacters } doReturn characterEntityData
            }

            viewModel = vm

            viewModelFactory = vmf

            scenario = launchFragmentInContainer<CharacterListFragment>(
                factory = ff
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }.moveToState(Lifecycle.State.RESUMED)
        }

        @Test
        fun It_Should_Be_Displayed() {
            characterEntityData.postValue(listOf(CharacterEntity("Bob")))
            onView(withId(R.id.character_list)).check { view, _ ->
                val recyclerView = view as RecyclerView
                assert(recyclerView.adapter?.itemCount == 1)
            }
            //Should this be tested here, or should it be moved to a test of CharacterListAdapter?
            onView(withId(R.id.word_view)).check { view, _ ->
                val textView = view as TextView
                assert(textView.text == "Bob")
            }
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Characters_List_Is_Updated_With_Many_Items {

        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        private lateinit var scenario: FragmentScenario<CharacterListFragment>
        private lateinit var characterEntityData: MutableLiveData<List<CharacterEntity>>
        private lateinit var viewModel: CharacterListViewModel
        private lateinit var viewModelFactory: ViewModelProvider.Factory

        @Before
        fun setup() {
            characterEntityData = MutableLiveData(emptyList())

            val(ff, vm, vmf) = fragmentFactoryWithMockViewModel<CharacterListViewModel>{
                on { allCharacters } doReturn characterEntityData
            }

            viewModel = vm

            viewModelFactory = vmf

            scenario = launchFragmentInContainer<CharacterListFragment>(
                factory = ff
            ).moveToState(Lifecycle.State.RESUMED)

            characterEntityData.postValue(listOf(CharacterEntity("Bob"), CharacterEntity("Dick"), CharacterEntity("Harry")))
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
                    0, R.id.word_view
                )
            ).check(matches(withText("Bob")))
            onView(
                withRecyclerView(R.id.character_list).atPositionOnView(
                    1, R.id.word_view
                )
            ).check(matches(withText("Dick")))
            onView(
                withRecyclerView(R.id.character_list).atPositionOnView(
                    2, R.id.word_view
                )
            ).check(matches(withText("Harry")))
        }
    }

    @RunWith(AndroidJUnit4::class)
    @Config(application = TestApp::class)
    class When_The_Add_Button_Is_Clicked {
        private lateinit var navController: NavController
        private lateinit var scenario: FragmentScenario<CharacterListFragment>
        private lateinit var viewModel: CharacterListViewModel
        private lateinit var viewModelFactory: ViewModelProvider.Factory

        @Before
        fun setup() {
            navController = mock()
            val(ff, vm, vmf) = fragmentFactoryWithMockViewModel<CharacterListViewModel>{
                on { allCharacters } doReturn mock()
            }

            viewModel = vm

            viewModelFactory = vmf

            scenario = launchFragmentInContainer<CharacterListFragment>(
                factory = ff
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
}