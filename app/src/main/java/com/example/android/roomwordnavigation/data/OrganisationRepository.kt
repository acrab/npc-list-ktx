package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class OrganisationRepository(private val organisationDao: OrganisationDao) {
    val allOrganisations = organisationDao.getAllOrganisations()

    @WorkerThread
    fun insert(organisation: Organisation)
    {
        organisationDao.insert(organisation)
    }

    fun getOrganisation(organisationId: Int) : LiveData<Organisation>
    {
        return organisationDao.getOrganisation(organisationId)
    }
}