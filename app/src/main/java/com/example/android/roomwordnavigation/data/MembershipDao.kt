package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MembershipDao {
    @Insert
    fun createMembership(membership: OrganisationMembership)

    @Query("SELECT character_table.* FROM character_table INNER JOIN characters_in_organisations_table ON character_table.id = characters_in_organisations_table.character WHERE characters_in_organisations_table.organisation = :organisationId")
    fun getMembers(organisationId: Int) : LiveData<List<Character>>
}