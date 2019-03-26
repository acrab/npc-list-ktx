package com.example.android.roomwordnavigation.data.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "character_table", indices = [Index("id", unique = true)])
data class CharacterEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "notes") val notes: String = "",
    //@ColumnInfo(name = "avatar") val avatar: String="",
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) = oldItem == newItem

            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) = oldItem.id == newItem.id
        }
    }
}
