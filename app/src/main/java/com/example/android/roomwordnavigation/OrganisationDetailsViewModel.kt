package com.example.android.roomwordnavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OrganisationDetailsViewModel(application: Application, private val organisationId:Int):AndroidViewModel(application)
{
    class Factory(private val application: Application, private val organisationId:Int) : ViewModelProvider.AndroidViewModelFactory(application)
    {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return OrganisationDetailsViewModel(application, organisationId) as T
        }
    }

    //TODO: consider moving to a base class
    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val membershipRepository: MembershipRepository
    val allMembers:LiveData<List<Character>>
    val organisation:LiveData<Organisation>

    init{
        val db = AppDatabase.getDatabase(application)
        val membershipDao = db.membershipDao()
        val organisationDao = db.organisationDao()
        membershipRepository = MembershipRepository(membershipDao, organisationDao)
        allMembers = membershipRepository.getMembers(organisationId)
        organisation = membershipRepository.getOrganisation(organisationId)
    }

    fun addToOrganisation(character: Character) = scope.launch(Dispatchers.IO){
        membershipRepository.createMembership(OrganisationMembership(character.id, organisationId))
    }
}