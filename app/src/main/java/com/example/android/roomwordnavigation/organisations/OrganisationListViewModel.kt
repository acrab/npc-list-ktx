package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.Organisation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@OpenForTesting
class OrganisationListViewModel @Inject constructor(private val repository: IOrganisationRepository) : ViewModelWithCoroutineScope() {

    val allOrganisations: LiveData<List<Organisation>> = repository.allOrganisations

    fun insert(organisation: Organisation, context:CoroutineContext = Dispatchers.IO) = scope.launch(context) {
        repository.insert(organisation)

    }
}