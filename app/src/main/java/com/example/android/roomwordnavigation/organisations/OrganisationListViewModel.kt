package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationSummary
import javax.inject.Inject

@OpenForTesting
class OrganisationListViewModel @Inject constructor(private val repository: IOrganisationRepository) : ViewModel() {

    val allOrganisations: LiveData<List<OrganisationSummary>> = repository.allOrganisationSummary

    fun insert(organisation: Organisation) {
        repository.insert(organisation, viewModelScope)
    }
}