package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.*
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

    val organisation: LiveData<Organisation>  by lazy {
        Transformations.switchMap(organisationId) { orgId ->
            orgId?.let { organisationRepository.getOrganisation(it) }
        }
    }

    fun addToOrganisation(characterEntity: CharacterEntity, coroutineContext: CoroutineContext = Dispatchers.IO) {

        val orgId = organisationId.value
            ?: throw IllegalStateException("Must set organisationId before adding a characterEntity")

        scope.launch(coroutineContext) {
            membershipRepository.createMembership(OrganisationMembership(characterEntity.id, orgId))
        }
    }
}
