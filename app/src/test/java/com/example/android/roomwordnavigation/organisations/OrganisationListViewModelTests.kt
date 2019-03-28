@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationSummary
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    OrganisationListViewModelTests.When_An_Organsiation_Is_Inserted::class,
    OrganisationListViewModelTests.When_The_View_Model_Is_Created::class
)
class OrganisationListViewModelTests {
    class When_The_View_Model_Is_Created {

        private lateinit var subject: OrganisationListViewModel
        private lateinit var organisationRepository: IOrganisationRepository
        private lateinit var data: LiveData<List<OrganisationSummary>>
        @Before
        fun setup() {
            data = mock()

            organisationRepository = mock {
                on { allOrganisationSummary } doReturn data
            }
            subject = OrganisationListViewModel(organisationRepository)
        }

        @Test
        fun It_Should_Get_The_List_Of_Organisations() {
            verify(organisationRepository).allOrganisationSummary
        }

        @Test
        fun The_Data_Should_Be_Available() {
            assert(subject.allOrganisations == data)
        }
    }

    class When_An_Organsiation_Is_Inserted {

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
        fun It_Should_Be_Inserted_Into_The_Repository() {
            val data = Organisation("Test Organsiation", "Test description")

            subject.insert(data)

            verify(organisationRepository).insert(eq(data), any())
        }
    }
}