package com.example.android.roomwordnavigation.data

import com.example.android.roomwordnavigation.data.entities.CharacterStatValue

interface ICharStatsRepository
{
    fun insertStatistic(stat: CharacterStatValue)
}