package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Statistic

interface IStatsRepository
{
    fun getStatisticsForTemplate(templateID:Long) : LiveData<List<Statistic>>
}