package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationSummary

interface IOrganisationRepository {

    val allOrganisationSummary : LiveData<List<OrganisationSummary>>

    val allOrganisations: LiveData<List<Organisation>>

    @WorkerThread
    fun insert(organisation: Organisation)

    fun getOrganisation(organisationId: Long) : LiveData<Organisation>
}