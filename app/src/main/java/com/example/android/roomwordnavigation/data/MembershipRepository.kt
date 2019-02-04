package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MembershipRepository(private val membershipDao: MembershipDao, private val organisationDao: OrganisationDao){

    @WorkerThread
    fun createMembership(membership: OrganisationMembership)
    {
        membershipDao.createMembership(membership)
    }

    fun getOrganisation(organisationId: Int) : LiveData<Organisation>
    {
        return organisationDao.getOrganisation(organisationId)
    }

    fun getMembers(organisationId:Int) : LiveData<List<Character>>
    {
        return membershipDao.getMembers(organisationId)
    }
}