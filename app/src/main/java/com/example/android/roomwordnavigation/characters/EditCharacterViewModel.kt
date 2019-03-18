package com.example.android.roomwordnavigation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.roomwordnavigation.ViewModelWithCoroutineScope
import com.example.android.roomwordnavigation.data.ICharacterRepository
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditCharacterViewModel @Inject constructor(private val repository: ICharacterRepository) :
    ViewModelWithCoroutineScope() {
    val characterId: MutableLiveData<Int> = MutableLiveData(0)

    val characterDetails: LiveData<CharacterEntity> by lazy {
        Transformations.switchMap(characterId) { charID ->
            charID?.let { repository.get(it) }
        }
    }

    fun onCharacterEdited(name: String) = scope.launch(Dispatchers.IO) {
        repository.update(CharacterEntity(name, characterId.value!!))
    }
}