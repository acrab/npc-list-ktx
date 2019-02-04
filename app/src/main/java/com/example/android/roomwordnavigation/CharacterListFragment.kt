package com.example.android.roomwordnavigation

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.databinding.FragmentCharacterListBinding
import com.example.android.roomwordnavigation.injection.ViewModelFactory
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CharacterListFragment : Fragment()
{
    private lateinit var characterListViewModel: CharacterListViewModel

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentCharacterListBinding>(inflater, R.layout.fragment_character_list, container, false)
        binding.fab.setOnClickListener{
            it.findNavController().navigate(CharacterListFragmentDirections.actionWordListFragmentToAddWordFragment())
        }
        val recyclerView = binding.characterList
        val adapter = CharacterListAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        characterListViewModel = activity?.run { ViewModelProviders.of(this, viewModelFactory).get(CharacterListViewModel::class.java)} ?: throw Exception("Invalid Activity")
        characterListViewModel.allWords.observe(this, Observer { characters->
            characters?.let{
                adapter.setCharacters(it)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
}