package com.example.android.roomwordnavigation.data.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*

/**
 * A [Template] is made up of multiple [Statistic]s
 * It is used to provide a quick set up method for a given system.
 */
@Entity(tableName = "system_template")
data class Template(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
)

/**
 * A [Statistic] is a simple value representing one part of a character
 */
@Entity(
    tableName = "statistic",
    foreignKeys = [ForeignKey(
        entity = Template::class, parentColumns = ["id"], childColumns = ["template"], onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("template")]
)
data class Statistic(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "template") val template: Long,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Statistic>() {
            override fun areContentsTheSame(oldItem: Statistic, newItem: Statistic) = oldItem == newItem

            override fun areItemsTheSame(oldItem: Statistic, newItem: Statistic) = oldItem.id == newItem.id

        }
    }
}

///**
// * A calculated statistic depends on a formula, and one or more [Statistic]s
// */
//@Entity(tableName = "calculated_statistic", foreignKeys = [ForeignKey(
//    entity = Template::class,
//    parentColumns = ["id"],
//    childColumns = ["template"],
//    onDelete = ForeignKey.CASCADE
//)])
//data class CalculatedStatistic(
//    @ColumnInfo(name ="name") val name: String,
//    @ColumnInfo(name ="calculation") val calculation: String,
//    @ColumnInfo(name ="template")val template: Int,
//    @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) val id:Int = 0
//)

/**
 * The value of a non-calculated [Statistic] associated with a specific [CharacterEntity]
 */
@Entity(
    tableName = "character_stat_value",
    foreignKeys = [ForeignKey(
        entity = Statistic::class,
        parentColumns = ["id"],
        childColumns = ["statistic"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["character"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("character")],
    primaryKeys = ["statistic", "character"]
)
data class CharacterStatValue(
    @ColumnInfo(name = "value") val value: Int,
    @ColumnInfo(name = "statistic") val statistic: Long,
    @ColumnInfo(name = "character") val character: Long
)