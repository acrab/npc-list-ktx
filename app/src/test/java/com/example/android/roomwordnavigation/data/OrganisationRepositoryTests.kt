@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    OrganisationRepositoryTests.When_The_Organisation_Repository_Is_Created::class,
    OrganisationRepositoryTests.When_An_Organisation_Is_Created::class,
    OrganisationRepositoryTests.When_An_Organisation_Is_Requested::class
)
class OrganisationRepositoryTests {
    class When_The_Organisation_Repository_Is_Created {
        private lateinit var dao: OrganisationDao
        private lateinit var data: LiveData<List<Organisation>>
        @Before
        fun setup() {
            data = mock()
            dao = mock {
                on { getAllOrganisations() } doReturn data
            }
        }

        @Test
        fun It_Gets_All_Organisations_From_The_Dao() {
            OrganisationRepository(dao)
            verify(dao).getAllOrganisations()
        }

        @Test
        fun The_Organisations_Are_Available() {
            val repo = OrganisationRepository(dao)
            val x = repo.allOrganisations
            assert(x == data)
        }
    }

    class When_An_Organisation_Is_Created {
        private lateinit var dao: OrganisationDao
        private lateinit var subject: OrganisationRepository

        @Before
        fun setup() {
            dao = mock()
            subject = OrganisationRepository(dao)
        }

        @Test
        fun It_Is_Inserted_Into_The_DAO() {
            val toInsert = Organisation("Test Org", "Test description")
            subject.insert(toInsert)
            verify(dao).insert(toInsert)
        }
    }

    class When_An_Organisation_Is_Requested {
        private lateinit var dao: OrganisationDao
        private lateinit var subject: OrganisationRepository
        private lateinit var data: LiveData<Organisation>

        @Before
        fun setup() {
            data = mock()
            dao = mock {
                on { getOrganisation(1) } doReturn data
            }
            subject = OrganisationRepository(dao)
        }

        @Test
        fun The_Dao_Is_Queried() {
            subject.getOrganisation(1)
            verify(dao).getOrganisation(1)
        }

        @Test
        fun The_Data_Is_Returned() {
            val x = subject.getOrganisation(1)
            assert(x == data)
        }
    }
}