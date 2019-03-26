package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

@Dao
interface MembershipDao {
    @Insert
    fun createMembership(membership: OrganisationMembership)

    @Delete
    fun deleteMembership(membership: OrganisationMembership)

    @Query(
        """
        SELECT character_table.* FROM character_table
        INNER JOIN characters_in_organisations_table
        ON character_table.id = characters_in_organisations_table.character
        WHERE characters_in_organisations_table.organisation = :organisationId
        ORDER BY name"""
    )
    fun getMembers(organisationId: Long): LiveData<List<CharacterEntity>>

    @Query(
        """
        SELECT character_table.id characterId, character_table.name characterName, organisation IS :organisationId isMember FROM character_table
        LEFT JOIN (select * from characters_in_organisations_table WHERE organisation = :organisationId)
        ON character_table.id = character
    """
    )
    fun getMembershipStatus(organisationId: Long): LiveData<List<MembershipStatus>>
}

data class MembershipStatus(val characterId: Long, val characterName: String, val isMember: Boolean) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MembershipStatus>() {

            override fun areContentsTheSame(oldItem: MembershipStatus, newItem: MembershipStatus) = oldItem == newItem

            override fun areItemsTheSame(oldItem: MembershipStatus, newItem: MembershipStatus) =
                oldItem.characterId == newItem.characterId
        }
    }
}