package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrganisationDetailsViewModel @Inject constructor(
    private val membershipRepository: IMembershipRepository,
    private val organisationRepository: IOrganisationRepository
) : ViewModelWithCoroutineScope() {
    var organisationId = MutableLiveData<Int>()

    var allMembers: LiveData<List<Character>> = Transformations.switchMap(organisationId) { orgId ->
        orgId?.let { membershipRepository.getMembers(it) }
    }

    var organisation: LiveData<Organisation> = Transformations.switchMap(organisationId) { orgId ->
        orgId?.let { organisationRepository.getOrganisation(it) }
    }

    fun addToOrganisation(character: Character) {

        val orgId = organisationId.value ?: throw IllegalStateException("Must set organisationId before adding a character")

        scope.launch(Dispatchers.IO) {
            membershipRepository.createMembership(OrganisationMembership(character.id, orgId))
        }
    }
}
