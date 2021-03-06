package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.*
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import javax.inject.Inject

class EditCharacterViewModel @Inject constructor(private val repository: ICharacterRepository) : ViewModel() {
    val characterId: MutableLiveData<Long> = MutableLiveData(0)

    val characterDetails: LiveData<CharacterEntity> by lazy {
        Transformations.switchMap(characterId) { charID ->
            charID?.let { repository.get(it) }
        }
    }

    fun onCharacterEdited(name: String, description: String, notes: String) =
        repository.update(CharacterEntity(name, description, notes, characterId.value!!), viewModelScope)

}
