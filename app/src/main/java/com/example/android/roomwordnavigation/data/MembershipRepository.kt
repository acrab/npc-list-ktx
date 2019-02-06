package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MembershipRepository(private val membershipDao: MembershipDao) : IMembershipRepository {

    @WorkerThread
    override fun createMembership(membership: OrganisationMembership)
    {
        membershipDao.createMembership(membership)
    }

    override fun getMembers(organisationId:Int) : LiveData<List<Character>>
    {
        return membershipDao.getMembers(organisationId)
    }
}