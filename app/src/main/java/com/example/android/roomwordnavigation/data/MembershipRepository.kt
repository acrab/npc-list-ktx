package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

class MembershipRepository(private val membershipDao: MembershipDao) : IMembershipRepository {

    @WorkerThread
    override fun createMembership(membership: OrganisationMembership) = membershipDao.createMembership(membership)

    @WorkerThread
    override fun deleteMembership(membership: OrganisationMembership) = membershipDao.deleteMembership(membership)

    override fun getMembers(organisationId: Int): LiveData<List<CharacterEntity>> =
        membershipDao.getMembers(organisationId)

    override fun getMembershipStatuses(organisationId: Int): LiveData<List<MembershipStatus>> =
        membershipDao.getMembershipStatus(organisationId)
}