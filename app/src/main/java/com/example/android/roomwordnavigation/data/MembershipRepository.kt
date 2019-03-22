package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MembershipRepository(private val membershipDao: MembershipDao) : IMembershipRepository {

    override fun createMembership(membership: OrganisationMembership, scope: CoroutineScope) {
        scope.launch { membershipDao.createMembership(membership) }
    }

    override fun deleteMembership(membership: OrganisationMembership, scope: CoroutineScope) {
        scope.launch { membershipDao.deleteMembership(membership) }
    }

    override fun getMembers(organisationId: Int): LiveData<List<CharacterEntity>> =
        membershipDao.getMembers(organisationId)

    override fun getMembershipStatuses(organisationId: Int): LiveData<List<MembershipStatus>> =
        membershipDao.getMembershipStatus(organisationId)
}