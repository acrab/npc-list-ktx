package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.CharacterEntity
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class CharacterListAdapter(context: Context, private val onCharacterSelected: (CharacterEntity) -> Unit = {}) :
    ListAdapter<CharacterEntity, CharacterListAdapter.CharacterViewHolder>(diffCallback),
    BindingListAdapter<CharacterEntity> {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacterListItemBinding.inflate(inflater), onCharacterSelected)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setName(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) = oldItem == newItem

            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity) = oldItem.id == newItem.id
        }
    }

    class CharacterViewHolder(
        private val characterListItemBinding: CharacterListItemBinding, onCharacterSelected: (CharacterEntity) -> Unit
    ) : RecyclerView.ViewHolder(characterListItemBinding.root) {
        private lateinit var characterEntity: CharacterEntity

        init {
            characterListItemBinding.root.setOnClickListener {
                onCharacterSelected(characterEntity)
            }
        }

        fun setName(characterEntity: CharacterEntity) {
            this.characterEntity = characterEntity
            characterListItemBinding.wordView.text = characterEntity.name
        }
    }
}
