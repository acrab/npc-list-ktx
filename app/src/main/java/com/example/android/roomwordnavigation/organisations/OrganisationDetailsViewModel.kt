package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.*
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.data.IMembershipRepository
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.MembershipStatus
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class OrganisationDetailsViewModel @Inject constructor(
    private val membershipRepository: IMembershipRepository, private val organisationRepository: IOrganisationRepository
) : ViewModel() {
    val organisationId = MutableLiveData<Long>()

    val allMembers: LiveData<List<CharacterEntity>> by lazy {
        Transformations.switchMap(organisationId) { orgId ->
            orgId?.let { membershipRepository.getMembers(it) }
        }
    }

    val membershipStatuses: LiveData<List<MembershipStatus>> by lazy {
        Transformations.switchMap(organisationId) { orgId ->
            orgId?.let { membershipRepository.getMembershipStatuses(it) }
        }
    }

    val organisation: LiveData<Organisation>  by lazy {
        Transformations.switchMap(organisationId) { orgId ->
            orgId?.let { organisationRepository.getOrganisation(it) }
        }
    }

    val organisationDescription: LiveData<String?> by lazy {
        Transformations.map(organisation) { org ->
            org?.description
        }
    }

    fun addToOrganisation(characterEntity: CharacterEntity): Job {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        return viewModelScope.launch(Dispatchers.IO) {
            membershipRepository.createMembership(OrganisationMembership(characterEntity.id, orgId))
        }
    }

    fun addToOrganisation(character: Long): Job {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        return viewModelScope.launch(Dispatchers.IO) {
            membershipRepository.createMembership(OrganisationMembership(character, orgId))
        }
    }

    fun removeFromOrganisation(character: Long): Job {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        return viewModelScope.launch(Dispatchers.IO) {
            membershipRepository.deleteMembership(OrganisationMembership(character, orgId))
        }
    }
}
