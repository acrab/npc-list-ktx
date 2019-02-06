package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

interface IOrganisationRepository {
    val allOrganisations: LiveData<List<Organisation>>

    @WorkerThread
    fun insert(organisation: Organisation)

    fun getOrganisation(organisationId: Int) : LiveData<Organisation>
}