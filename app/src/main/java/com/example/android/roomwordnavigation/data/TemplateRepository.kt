package com.example.android.roomwordnavigation.data

import androidx.lifecycle.LiveData
import com.example.android.roomwordnavigation.data.entities.Template
import javax.inject.Inject

class TemplateRepository @Inject constructor(templateDao:TemplateDao) :ITemplateRepository
{
    override val templates: LiveData<List<Template>> = templateDao.getAllTemplates()

}