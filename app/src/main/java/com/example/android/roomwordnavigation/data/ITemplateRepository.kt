package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Template

interface ITemplateRepository
{
    val templates: LiveData<List<Template>>
}