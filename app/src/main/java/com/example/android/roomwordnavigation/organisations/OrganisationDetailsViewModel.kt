package com.example.android.roomwordnavigation.organisations

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrganisationDetailsViewModel @Inject constructor(private val membershipRepository: MembershipRepository, private val organisationRepository: OrganisationRepository):ViewModelWithCoroutineScope()
{
    private var organisationId = 0

    lateinit var allMembers:LiveData<List<Character>>
    lateinit var organisation:LiveData<Organisation>

    fun addToOrganisation(character: Character) = scope.launch(Dispatchers.IO){
        membershipRepository.createMembership(OrganisationMembership(character.id, organisationId))
    }

    fun setOrganisationId(organisationId:Int)
    {
        this.organisationId = organisationId
        allMembers = membershipRepository.getMembers(organisationId)
        organisation = organisationRepository.getOrganisation(organisationId)
    }
}