@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.data.OrganisationRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class OrganisationListViewModel_When_The_View_Model_Is_Created {

    private lateinit var subject: OrganisationListViewModel
    private lateinit var organisationRepository: IOrganisationRepository
    private lateinit var data: LiveData<List<Organisation>>
    @Before
    fun setup() {
        data = mock()

        organisationRepository = mock {
            on { allOrganisations } doReturn data
        }
        subject = OrganisationListViewModel(organisationRepository)
    }

    @Test
    fun It_Should_Get_The_List_Of_Organisations() {
        verify(organisationRepository).allOrganisations
    }

    @Test
    fun The_Data_Should_Be_Available() {
        assert(subject.allOrganisations == data)
    }
}

@RunWith(AndroidJUnit4::class)
class OrganisationListViewModel_When_An_Organsiation_Is_Inserted {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var subject: OrganisationListViewModel
    private lateinit var organisationRepository: IOrganisationRepository

    @Before
    fun setup() {
        organisationRepository = mock()
        subject = OrganisationListViewModel(organisationRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun It_Should_Be_Inserted_Into_The_Repository() = runBlocking {

        val data = Organisation("Test Organsiation", "Test description")
        subject.insert(data, Dispatchers.Unconfined)
        verify(organisationRepository).insert(data)
    }
}
