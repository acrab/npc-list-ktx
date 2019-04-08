package com.example.android.roomwordnavigation.data

import com.example.android.roomwordnavigation.data.entities.CharacterStatValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharStatsRepository(private val dao: CharacterStatDao) : ICharStatsRepository {
    override fun insertStatistic(stat: CharacterStatValue, scope:CoroutineScope) {
        scope.launch {
            dao.insert(stat)
        }
    }
}