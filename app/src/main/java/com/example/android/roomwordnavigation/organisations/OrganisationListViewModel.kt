package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.data.OrganisationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrganisationListViewModel @Inject constructor(private val repository: OrganisationRepository) : ViewModel() {

    val allOrganisations: LiveData<List<Organisation>> = repository.allOrganisations

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    fun insert(organisation: Organisation) = scope.launch(Dispatchers.IO) {
        repository.insert(organisation)
    }
}