@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MembershipRepositoryTests.When_A_Membership_Is_Created::class,
    MembershipRepositoryTests.When_All_Members_Of_An_Organisation_Are_Retrieved::class
)
class MembershipRepositoryTests {
    class When_A_Membership_Is_Created {
        private lateinit var subject: MembershipRepository
        private lateinit var membershipDao: MembershipDao

        @Before
        fun TestSetup() {
            membershipDao = mock()
            subject = MembershipRepository(membershipDao)
        }

        @Test
        fun It_Is_Inserted_Into_The_Membership_Dao() {
            val toInsert = OrganisationMembership(1, 2)
            subject.createMembership(toInsert)
            verify(membershipDao).createMembership(toInsert)
        }
    }

    class When_All_Members_Of_An_Organisation_Are_Retrieved {
        private lateinit var subject: MembershipRepository
        private lateinit var membershipDao: MembershipDao
        private lateinit var memberData: LiveData<List<CharacterEntity>>

        @Before
        fun TestSetup() {
            this.memberData = mock(stubOnly = true)

            membershipDao = mock {
                on { getMembers(1) } doReturn memberData
            }
            subject = MembershipRepository(membershipDao)
        }

        @Test
        fun The_Dao_Is_Queried() {
            subject.getMembers(1)
            verify(membershipDao).getMembers(1)
        }

        @Test
        fun The_Members_Are_Returned() {
            val x = subject.getMembers(1)
            assert(x == memberData)
        }
    }
}