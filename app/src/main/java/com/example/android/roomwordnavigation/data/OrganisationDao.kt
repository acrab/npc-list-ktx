package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrganisationDao {
    @Query("SELECT * FROM organisation_table ORDER BY name ASC")
    fun getAllOrganisations() : LiveData<List<Organisation>>

    @Query("SELECT name, id FROM organisation_table ORDER BY name ASC")
    fun getOrganisationList() : LiveData<List<OrganisationSummary>>

    @Query("SELECT * FROM organisation_table WHERE id = :id")
    fun getOrganisation(id:Int) : LiveData<Organisation>

    @Insert
    fun insert(organisation: Organisation)

    @Query("DELETE FROM organisation_table")
    fun deleteAll()

    @Delete
    fun delete(organisation: Organisation)
}