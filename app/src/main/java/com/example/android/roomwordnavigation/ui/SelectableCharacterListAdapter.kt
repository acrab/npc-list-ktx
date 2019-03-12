package com.example.android.roomwordnavigation.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordnavigation.data.MembershipStatus
import com.example.android.roomwordnavigation.databinding.SelectableCharacterListItemBinding

class SelectableCharacterListAdapter(
    context: Context, private val onSelectionChanged: (Int, Boolean) -> Unit
) : ListAdapter<MembershipStatus, SelectableCharacterListAdapter.ViewHolder>(MembershipStatus.diffCallback),
    BindingListAdapter<MembershipStatus> {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectableCharacterListAdapter.ViewHolder(
        SelectableCharacterListItemBinding.inflate(inflater),
        onSelectionChanged
    )

    override fun onBindViewHolder(holder: SelectableCharacterListAdapter.ViewHolder, position: Int) =
        holder.setCharacter(getItem(position))

    class ViewHolder(
        private val characterListItemBinding: SelectableCharacterListItemBinding,
        onSelectionChanged: (Int, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(characterListItemBinding.root) {
        private lateinit var membershipStatus: MembershipStatus

        init {
            characterListItemBinding.checkBox.setOnClickListener {
                onSelectionChanged(membershipStatus.characterId, characterListItemBinding.checkBox.isChecked)
            }
        }

        fun setCharacter(membershipStatus: MembershipStatus) {
            this.membershipStatus = membershipStatus
            characterListItemBinding.checkBox.text = membershipStatus.characterName
            characterListItemBinding.checkBox.isChecked = membershipStatus.isMember
        }
    }
}