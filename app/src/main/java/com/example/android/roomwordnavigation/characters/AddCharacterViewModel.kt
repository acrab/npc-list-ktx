package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.roomwordnavigation.testing.OpenForTesting
import com.example.android.roomwordnavigation.data.ICharStatsRepository
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.IStatsRepository
import com.example.android.roomwordnavigation.data.ITemplateRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.CharacterStatValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class AddCharacterViewModel @Inject constructor(
    private val characterRepository: ICharacterRepository,
    templateRepository: ITemplateRepository,
    private val statsRepository: IStatsRepository,
    private val charStatsRepository: ICharStatsRepository
) : ViewModel() {
    fun insert(characterEntity: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        characterRepository.insert(characterEntity)
    }

    fun create(characterEntity: CharacterEntity, stats: Map<Long, Int>) = viewModelScope.launch(Dispatchers.IO) {
        val newCharId = characterRepository.insert(characterEntity)
        for(stat in stats)
        {
            charStatsRepository.insertStatistic(CharacterStatValue(stat.value, stat.key, newCharId))
        }
    }

    val templates = templateRepository.templates

    val selectedTemplate: MutableLiveData<Long> = MutableLiveData(0)

    val stats by lazy {
        Transformations.switchMap(selectedTemplate) {
            when (it) {
                0L -> null
                else -> statsRepository.getStatisticsForTemplate(it)
            }
        }
    }
}