package com.example.android.roomwordnavigation.data

import androidx.annotation.WorkerThread

class OrganisationRepository(private val organisationDao: OrganisationDao) {
    val allOrganisations = organisationDao.getAllOrganisations()

    @WorkerThread
    fun insert(organisation: Organisation)
    {
        organisationDao.insert(organisation)
    }
}