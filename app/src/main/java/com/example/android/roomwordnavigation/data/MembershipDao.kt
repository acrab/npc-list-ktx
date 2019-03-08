package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

@Dao
interface MembershipDao {
    @Insert
    fun createMembership(membership: OrganisationMembership)

    @Query("SELECT character_table.* FROM character_table" +
            " INNER JOIN characters_in_organisations_table" +
            " ON character_table.id = characters_in_organisations_table.character " +
            "WHERE characters_in_organisations_table.organisation = :organisationId")
    fun getMembers(organisationId: Int) : LiveData<List<CharacterEntity>>

    @Query("""
        SELECT character_table.id characterId, character_table.name characterName, characters_in_organisations_table.organisation = :organisationId isMember from character_table
        LEFT JOIN characters_in_organisations_table
        ON character_table.id = characters_in_organisations_table.character
    """)
    fun getMembershipStatus(organisationId: Int) : LiveData<List<MembershipStatus>>
}

data class MembershipStatus(val characterId:Int, val characterName: String, val isMember:Boolean)
