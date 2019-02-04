package com.example.android.roomwordnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.data.MembershipRepository
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.data.OrganisationMembership
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrganisationDetailsViewModel @Inject constructor(private val membershipRepository: MembershipRepository):ViewModel()
{
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

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
        organisation = membershipRepository.getOrganisation(organisationId)
    }
}