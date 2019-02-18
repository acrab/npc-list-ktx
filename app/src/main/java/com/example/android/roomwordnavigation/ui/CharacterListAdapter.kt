package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.CharacterEntity
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class CharacterListAdapter internal constructor(context: Context, private val onCharacterSelected: (CharacterEntity)->Unit = {}) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val wordListItemBinding: CharacterListItemBinding, onCharacterSelected: (CharacterEntity)->Unit) :
        RecyclerView.ViewHolder(wordListItemBinding.root) {
        init{
            wordListItemBinding.root.setOnClickListener{
                onCharacterSelected(characterEntity!!)
            }
        }

        private var characterEntity : CharacterEntity? = null

        fun setName(characterEntity: CharacterEntity) {
            this.characterEntity = characterEntity
            wordListItemBinding.wordView.text = characterEntity.name
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

    fun setCharacters(characterEntities: List<CharacterEntity>) {
        this.characters = characterEntities
        notifyDataSetChanged()
    }
}
