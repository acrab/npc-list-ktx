package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

interface IMembershipRepository {
    @WorkerThread
    fun createMembership(membership: OrganisationMembership)

    @WorkerThread
    fun deleteMembership(membership: OrganisationMembership)

    fun getMembers(organisationId: Int) : LiveData<List<CharacterEntity>>

    fun getMembershipStatuses(organisationId:Int) : LiveData<List<MembershipStatus>>
}