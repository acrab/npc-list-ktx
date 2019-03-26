package com.example.android.roomwordnavigation.data

import com.example.android.roomwordnavigation.data.entities.CharacterStatValue

class CharStatsRepository(private val dao: CharacterStatDao) : ICharStatsRepository {
    override fun insertStatistic(stat: CharacterStatValue) {
        dao.insert(stat)
    }
}