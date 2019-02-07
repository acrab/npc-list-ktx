@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.MembershipDao
import com.example.android.roomwordnavigation.data.MembershipRepository
import com.example.android.roomwordnavigation.data.OrganisationDao
import com.example.android.roomwordnavigation.data.OrganisationMembership
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class When_A_Membership_Is_Created {
    private lateinit var subject: MembershipRepository
    private lateinit var membershipDao: MembershipDao
    private lateinit var organisationDao: OrganisationDao

    @Before
    fun TestSetup() {
        membershipDao = mock()
        organisationDao = mock()
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
    private lateinit var organisationDao: OrganisationDao
    private lateinit var memberData: LiveData<List<Character>>

    @Before
    fun TestSetup() {
        this.memberData = mock()

        membershipDao = mock {
            on { getMembers(1) } doReturn memberData
        }
        organisationDao = mock()
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