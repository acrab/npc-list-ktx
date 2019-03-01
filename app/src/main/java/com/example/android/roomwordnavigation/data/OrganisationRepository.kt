package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class OrganisationRepository(private val organisationDao: OrganisationDao) : IOrganisationRepository {
    override val allOrganisations = organisationDao.getAllOrganisations()

    override val allOrganisationSummary: LiveData<List<OrganisationSummary>> = organisationDao.getOrganisationList()

    @WorkerThread
    override fun insert(organisation: Organisation)
    {
        organisationDao.insert(organisation)

    }

    override fun getOrganisation(organisationId: Int) : LiveData<Organisation>
    {
        return organisationDao.getOrganisation(organisationId)
    }
}