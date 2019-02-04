package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.Character
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class CharacterListAdapter internal constructor(context: Context, private val onCharacterSelected: (Character)->Unit = {}) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val wordListItemBinding: CharacterListItemBinding, onCharacterSelected: (Character)->Unit) :
        RecyclerView.ViewHolder(wordListItemBinding.root) {
        init{
            wordListItemBinding.root.setOnClickListener{
                onCharacterSelected(character!!)
            }
        }

        private var character : Character? = null

        fun setName(character: Character) {
            this.character = character
            wordListItemBinding.wordView.text = character.name
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var characters = emptyList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacterListItemBinding.inflate(inflater), onCharacterSelected)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setName(characters[position])
    }

    fun setCharacters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged()
    }
}
