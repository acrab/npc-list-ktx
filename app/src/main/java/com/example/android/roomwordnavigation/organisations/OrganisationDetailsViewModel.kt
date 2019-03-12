package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.IMembershipRepository
import com.example.android.roomwordnavigation.data.IOrganisationRepository
import com.example.android.roomwordnavigation.data.MembershipStatus
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@OpenForTesting
class OrganisationDetailsViewModel @Inject constructor(
    private val membershipRepository: IMembershipRepository, private val organisationRepository: IOrganisationRepository
) : ViewModelWithCoroutineScope() {
    val organisationId = MutableLiveData<Int>()

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

    fun addToOrganisation(characterEntity: CharacterEntity, coroutineContext: CoroutineContext = Dispatchers.IO) {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        scope.launch(coroutineContext) {
            membershipRepository.createMembership(OrganisationMembership(characterEntity.id, orgId))
        }
    }

    fun addToOrganisation(character: Int, coroutineContext: CoroutineContext = Dispatchers.IO) {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        scope.launch(coroutineContext) {
            membershipRepository.createMembership(OrganisationMembership(character, orgId))
        }
    }

    fun removeFromOrganisation(character: Int, coroutineContext: CoroutineContext = Dispatchers.IO) {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        scope.launch(coroutineContext) {
            membershipRepository.deleteMembership(OrganisationMembership(character, orgId))
        }
    }
}
