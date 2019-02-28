package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.CharacterEntity
import com.example.android.roomwordnavigation.databinding.CharacterListItemBinding

class CharacterListAdapter internal constructor(
    context: Context, private val onCharacterSelected: (CharacterEntity) -> Unit = {}
) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>(), BindingListAdapter<CharacterEntity> {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val listDiffer = AsyncListDiffer<CharacterEntity>(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacterListItemBinding.inflate(inflater), onCharacterSelected)
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setName(listDiffer.currentList[position])
    }

    override fun setData(data: List<CharacterEntity>) {
        listDiffer.submitList(data)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CharacterEntity>()
        {
            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem.id == newItem.id
            }
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