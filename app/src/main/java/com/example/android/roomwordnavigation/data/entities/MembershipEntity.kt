package com.example.android.roomwordnavigation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "characters_in_organisations_table", foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["character"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Organisation::class,
        parentColumns = ["id"],
        childColumns = ["organisation"],
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["character", "organisation"],
    indices = [Index("character"), Index("organisation")]
)
data class OrganisationMembership(
    @ColumnInfo(name = "character") val character: Int, @ColumnInfo(name = "organisation") val organisation: Int
)
