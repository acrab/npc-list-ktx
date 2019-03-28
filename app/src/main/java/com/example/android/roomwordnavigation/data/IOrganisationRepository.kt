package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationSummary
import kotlinx.coroutines.CoroutineScope

interface IOrganisationRepository {

    val allOrganisationSummary : LiveData<List<OrganisationSummary>>

    val allOrganisations: LiveData<List<Organisation>>

    fun insert(organisation: Organisation, scope: CoroutineScope)

    fun getOrganisation(organisationId: Long) : LiveData<Organisation>
}