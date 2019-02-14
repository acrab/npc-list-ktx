@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.characters

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.TestApp
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import com.example.android.roomwordnavigation.util.TestFragmentFactory
import com.example.android.roomwordnavigation.util.withRecyclerView
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.mockito.internal.matchers.Matches
import org.robolectric.annotation.Config

@RunWith(Suite::class)
@Suite.SuiteClasses(
    When_The_View_Is_Created::class,
    When_The_Characters_List_Is_Updated_With_One_Item::class,
    When_The_Characters_List_Is_Updated_With_Many_Items::class,
    When_The_Add_Button_Is_Clicked::class
)
class CharacterListFragmentTests

@RunWith(AndroidJUnit4::class)
@Config(application = TestApp::class)
class When_The_View_Is_Created {
    private lateinit var navController: NavController
    private lateinit var scenario: FragmentScenario<CharacterListFragment>
    private lateinit var viewModel: CharacterListViewModel
    private lateinit var testFragmentFactory: TestFragmentFactory<CharacterListFragment>

    @Before
    fun setup() {
        navController = mock()

        viewModel = mock(name = "MockViewModel") {
            on { allCharacters } doReturn mock()
        }

        testFragmentFactory = spy(TestFragmentFactory(viewModel))

        scenario = launchFragmentInContainer<CharacterListFragment>(
            factory = testFragmentFactory
        ).onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun It_Should_Create_The_View_Model() {
        verify(testFragmentFactory).instantiate(any(), any(), anyOrNull())
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
    private lateinit var characterData: MutableLiveData<List<Character>>
    private lateinit var viewModel: CharacterListViewModel
    private lateinit var testFragmentFactory: TestFragmentFactory<CharacterListFragment>

    @Before
    fun setup() {
        navController = mock()
        characterData = MutableLiveData(emptyList())
        viewModel = mock(name = "MockViewModel") {
            on { allCharacters } doReturn characterData
        }

        testFragmentFactory = spy(TestFragmentFactory(viewModel))

        scenario = launchFragmentInContainer<CharacterListFragment>(
            factory = testFragmentFactory
        ).onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun It_Should_Be_Displayed() {
        characterData.postValue(listOf(Character("Bob")))
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

    private lateinit var navController: NavController
    private lateinit var scenario: FragmentScenario<CharacterListFragment>
    private lateinit var characterData: MutableLiveData<List<Character>>
    private lateinit var viewModel: CharacterListViewModel
    private lateinit var testFragmentFactory: TestFragmentFactory<CharacterListFragment>

    @Before
    fun setup() {
        navController = mock()
        characterData = MutableLiveData(emptyList())
        viewModel = mock(name = "MockViewModel") {
            on { allCharacters } doReturn characterData
        }

        testFragmentFactory = spy(TestFragmentFactory(viewModel))

        scenario = launchFragmentInContainer<CharacterListFragment>(
            factory = testFragmentFactory
        ).onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }.moveToState(Lifecycle.State.RESUMED)

        characterData.postValue(listOf(Character("Bob"), Character("Dick"), Character("Harry")))
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

        onView(withRecyclerView(R.id.character_list).atPositionOnView(0, R.id.word_view)).check(matches(withText("Bob")))
        onView(withRecyclerView(R.id.character_list).atPositionOnView(1, R.id.word_view)).check(matches(withText("Dick")))
        onView(withRecyclerView(R.id.character_list).atPositionOnView(2, R.id.word_view)).check(matches(withText("Harry")))

    }
}

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