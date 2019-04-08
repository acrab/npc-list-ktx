package com.example.android.roomwordnavigation.data

import com.example.android.roomwordnavigation.data.entities.CharacterStatValue
import kotlinx.coroutines.CoroutineScope

interface ICharStatsRepository
{
    fun insertStatistic(stat: CharacterStatValue, scope: CoroutineScope)
}