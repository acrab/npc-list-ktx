package com.example.android.roomwordnavigation.data.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "organisation_table", indices = [Index("id", unique = true)])
data class Organisation(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
)

data class OrganisationSummary(
    @ColumnInfo(name = "name") val name: String, @ColumnInfo(name = "id") val id: Long = 0
) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<OrganisationSummary>() {

            override fun areContentsTheSame(oldItem: OrganisationSummary, newItem: OrganisationSummary) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: OrganisationSummary, newItem: OrganisationSummary) =
                oldItem.id == newItem.id
        }
    }
}