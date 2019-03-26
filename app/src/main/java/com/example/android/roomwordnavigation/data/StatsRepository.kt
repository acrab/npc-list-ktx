package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Statistic

class StatsRepository(private val dao:StatisticDao) :IStatsRepository
{
    override fun getStatisticsForTemplate(templateID: Long) = dao.getStatsForTemplate(templateID)
}