package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

class MembershipRepository(private val membershipDao: MembershipDao) : IMembershipRepository {

    @WorkerThread
    override fun createMembership(membership: OrganisationMembership)
    {
        membershipDao.createMembership(membership)
    }

    override fun getMembers(organisationId:Int) : LiveData<List<CharacterEntity>>
    {
        return membershipDao.getMembers(organisationId)
    }
}