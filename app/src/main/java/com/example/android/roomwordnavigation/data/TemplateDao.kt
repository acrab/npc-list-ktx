package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.roomwordnavigation.data.entities.CharacterStatValue
import com.example.android.roomwordnavigation.data.entities.Statistic
import com.example.android.roomwordnavigation.data.entities.Template

@Dao
interface TemplateDao{
    @Insert
    fun insert(template: Template)

    @Query("SELECT * FROM system_template ORDER BY name ASC")
    fun getAllTemplates() : LiveData<List<Template>>
}

@Dao
interface StatisticDao{
    @Insert
    fun insert(statistic: Statistic)

    @Query("SELECT * FROM statistic WHERE template = :template")
    fun getStatsForTemplate(template: Long) : LiveData<List<Statistic>>
}

//@Dao
//interface CalculatedStatisticDao{
//    @Insert
//    fun insert(statistic: CalculatedStatistic)
//
//    @Query("SELECT * FROM calculated_statistic WHERE template = :template")
//    fun getStatsForTemplate(template: Int) : LiveData<List<CalculatedStatistic>>
//}

@Dao
interface CharacterStatDao{
    @Insert
    fun insert(characterStatValue: CharacterStatValue)

    @Update
    fun update(characterStatValue: CharacterStatValue)

    @Query("SELECT * FROM character_stat_value WHERE character = :character")
    fun getStatsForCharacter(character:Int) : LiveData<List<CharacterStatValue>>
}