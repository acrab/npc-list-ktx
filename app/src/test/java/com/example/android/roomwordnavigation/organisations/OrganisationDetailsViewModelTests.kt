@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.organisations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.roomwordnavigation.data.*
import com.example.android.roomwordnavigation.util.afterObserve
import com.example.android.roomwordnavigation.util.observedValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
}

class When_The_Organisation_ID_Is_Set {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var subject: OrganisationDetailsViewModel
    private lateinit var membershipRepository: IMembershipRepository
    private lateinit var organisationRepository: IOrganisationRepository

    private lateinit var membershipLiveData: MutableLiveData<List<Character>>
    private lateinit var membershipData: List<Character>

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
}

@Suppress("unused") //Subclasses contain tests, superclass isn't explicitly used.
class When_A_Character_Is_Added_To_The_Organisation {

    @RunWith(AndroidJUnit4::class)
    class And_The_Organsiation_ID_Has_Not_Been_Set {

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
                subject.addToOrganisation(Character("Test Character"))
            }
            //No exception thrown
            assert(true)
        }
    }

    @RunWith(AndroidJUnit4::class)
    class And_The_Organsiation_ID_Has_Been_Set {

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

        @Test
        fun It_Should_Create_A_Membership_In_The_Repository() = runBlocking {
            val charToAdd = Character("Test Character")
            subject.addToOrganisation(charToAdd)
            verify(membershipRepository).createMembership(OrganisationMembership(charToAdd.id, 1))
        }
    }
}