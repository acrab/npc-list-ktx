package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.CharacterEntity
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class CharacterListAdapter internal constructor(
    context: Context, private val onCharacterSelected: (CharacterEntity) -> Unit = {}
) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>(), BindingListAdapter<CharacterEntity> {

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

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var characters = emptyList<CharacterEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacterListItemBinding.inflate(inflater), onCharacterSelected)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setName(characters[position])
    }

    override fun setData(data: List<CharacterEntity>) {
        this.characters = data
        notifyDataSetChanged()
    }
}
