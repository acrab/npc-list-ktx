package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OrganisationRepository(private val organisationDao: OrganisationDao) : IOrganisationRepository {
    override val allOrganisations = organisationDao.getAllOrganisations()

    override val allOrganisationSummary: LiveData<List<OrganisationSummary>> = organisationDao.getOrganisationList()

    override fun insert(organisation: Organisation, scope: CoroutineScope) {
        scope.launch {
            organisationDao.insert(organisation)
        }

    }

    override fun getOrganisation(organisationId: Long) : LiveData<Organisation> {
        return organisationDao.getOrganisation(organisationId)
    }
}
