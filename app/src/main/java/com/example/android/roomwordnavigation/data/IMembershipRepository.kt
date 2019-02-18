package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

interface IMembershipRepository {
    @WorkerThread
    fun createMembership(membership: OrganisationMembership)

    fun getMembers(organisationId: Int) : LiveData<List<CharacterEntity>>
}