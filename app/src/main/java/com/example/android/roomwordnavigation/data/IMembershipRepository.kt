package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import kotlinx.coroutines.CoroutineScope

interface IMembershipRepository {
    fun createMembership(membership: OrganisationMembership, scope: CoroutineScope)

    fun deleteMembership(membership: OrganisationMembership, scope: CoroutineScope)

    fun getMembers(organisationId: Long) : LiveData<List<CharacterEntity>>

    fun getMembershipStatuses(organisationId:Long) : LiveData<List<MembershipStatus>>
}
