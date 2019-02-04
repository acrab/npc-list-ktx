package com.example.android.roomwordnavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.AppDatabase
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.data.OrganisationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OrganisationListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository:OrganisationRepository

    val allOrganisations:LiveData<List<Organisation>>

    //TODO: consider moving to a base class
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init{
        val dao = AppDatabase.getDatabase(application).organisationDao()
        repository = OrganisationRepository(dao)
        allOrganisations = repository.allOrganisations
    }

    fun insert(organisation: Organisation) = scope.launch(Dispatchers.IO){
        repository.insert(organisation)
    }
}