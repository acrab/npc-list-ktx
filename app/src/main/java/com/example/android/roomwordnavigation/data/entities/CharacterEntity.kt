package com.example.android.roomwordnavigation.data.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*

@Entity(
    tableName = "character_table",
    indices = [
        Index("id", unique = true),
        Index("template")
    ],
    foreignKeys = [
        ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template"])
    ]
)
data class CharacterEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "notes") val notes: String = "",
    @ColumnInfo(name = "template") val template: Long,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) = oldItem == newItem

            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) = oldItem.id == newItem.id
        }
    }
}
