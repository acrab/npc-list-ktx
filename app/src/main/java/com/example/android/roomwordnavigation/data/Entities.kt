package com.example.android.roomwordnavigation.data

import androidx.room.*

@Entity(tableName = "character_table", indices = [Index("id", unique = true)])
data class CharacterEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0
)

@Entity(tableName = "organisation_table", indices = [Index("id", unique = true)])
data class Organisation(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0
)

data class OrganisationSummary(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id") val id: Int = 0
)

@Entity(
    tableName = "characters_in_organisations_table",
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["character"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Organisation::class,
        parentColumns = ["id"],
        childColumns = ["organisation"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("character"), Index("organisation")]
)
data class OrganisationMembership(
    @ColumnInfo(name = "character") val character: Int,
    @ColumnInfo(name = "organisation") val organisation: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
)