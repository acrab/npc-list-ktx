@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.android.roomwordnavigation.afterObserve
import com.example.android.roomwordnavigation.data.IMembershipRepository
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import com.example.android.roomwordnavigation.observedValue
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    OrganisationDetailsViewModelTests.When_The_ViewModel_Is_Created::class,
    OrganisationDetailsViewModelTests.When_The_Organisation_ID_Is_Set::class,
    OrganisationDetailsViewModelTests.When_A_Character_Entity_Is_Added_To_The_Organisation_And_The_Organsiation_ID_Has_Not_Been_Set::class,
    OrganisationDetailsViewModelTests.When_A_Character_Entity_Is_Added_To_The_Organisation_And_The_Organsiation_ID_Has_Been_Set::class
)
class OrganisationDetailsViewModelTests {
    class When_The_ViewModel_Is_Created {
        private lateinit var subject: OrganisationDetailsViewModel
        @Before
        fun setup() {
            val membershipRepository = mock<IMembershipRepository>()
            val organisationRepository = mock<IOrganisationRepository>()
            subject = OrganisationDetailsViewModel(membershipRepository, organisationRepository)
        }

        @Test
        fun The_Members_List_Should_Be_Empty() {
            Assert.assertNull(subject.allMembers.value)
        }

        @Test
        fun The_Organisation_Details_Should_Be_Empty() {
            Assert.assertNull(subject.organisation.value)
        }

        @Test
        fun The_Organisation_Description_Should_Be_Null() {
            Assert.assertNull(subject.organisationDescription.value)
        }
    }

    class When_The_Organisation_ID_Is_Set {

        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        private lateinit var subject: OrganisationDetailsViewModel
        private lateinit var membershipRepository: IMembershipRepository
        private lateinit var organisationRepository: IOrganisationRepository

        private lateinit var membershipLiveData: MutableLiveData<List<CharacterEntity>>
        private lateinit var membershipData: List<CharacterEntity>

        private lateinit var organisationLiveData: MutableLiveData<Organisation>
        private lateinit var organisationData: Organisation

        @Before
        fun setup() {
            //Construct mock membership data
            membershipData = mock()

            membershipLiveData = MutableLiveData()
            membershipLiveData.value = membershipData

            membershipRepository = mock {
                on { getMembers(1) } doReturn membershipLiveData
            }

            //Construct mock organisation data
            organisationData = Organisation("Test Organisation", "Organisation for testing")

            organisationLiveData = MutableLiveData()
            organisationLiveData.value = organisationData

            organisationRepository = mock {
                on { getOrganisation(1) } doReturn organisationLiveData
            }

            //Construct subject
            subject = OrganisationDetailsViewModel(membershipRepository, organisationRepository)

            //Trigger subject to update data
            subject.organisationId.value = 1
        }

        @Test
        fun The_Membership_Repository_Should_Be_Queried() {
            subject.allMembers.afterObserve {
                verify(membershipRepository).getMembers(1)
            }
        }

        @Test
        fun The_Members_Should_Be_Updated() {
            assert(subject.allMembers.observedValue() == membershipData)
        }

        @Test
        fun The_Organisation_Repository_Should_Be_Queried() {
            subject.organisation.afterObserve {
                verify(organisationRepository).getOrganisation(1)
            }
        }

        @Test
        fun The_Organisation_Details_Should_Be_Updated() {
            assert(subject.organisation.observedValue() == organisationData)
        }

        @Test
        fun The_Organisation_Description_Should_Be_Updated() {
            assert(subject.organisationDescription.observedValue() == organisationData.description)
        }
    }

    class When_A_Character_Entity_Is_Added_To_The_Organisation_And_The_Organsiation_ID_Has_Not_Been_Set {

        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        private lateinit var subject: OrganisationDetailsViewModel

        @Before
        fun setup() {
            subject = OrganisationDetailsViewModel(mock(), mock())
        }

        @Test(expected = IllegalStateException::class)
        fun It_Should_Fail() {
            runBlocking {
                subject.addToOrganisation(CharacterEntity("Test CharacterEntity"))
            }
            //No exception thrown
            assert(true)
        }
    }

    class When_A_Character_Entity_Is_Added_To_The_Organisation_And_The_Organsiation_ID_Has_Been_Set {

        @get:Rule
        val instantExecutor = InstantTaskExecutorRule()

        private lateinit var subject: OrganisationDetailsViewModel
        private lateinit var membershipRepository: IMembershipRepository
        @Before
        fun setup() {
            membershipRepository = mock()
            subject = OrganisationDetailsViewModel(membershipRepository, mock())
            subject.organisationId.postValue(1)
        }

        @ExperimentalCoroutinesApi
        @Test
        fun It_Should_Create_A_Membership_In_The_Repository() = runBlocking {
            val charToAdd = CharacterEntity("Test CharacterEntity")
            subject.addToOrganisation(charToAdd)
            verify(membershipRepository).createMembership(eq(OrganisationMembership(charToAdd.id, 1)), any())
        }
    }
}