package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.databinding.FragmentViewOrganisationBinding
import com.example.android.roomwordnavigation.ui.CharacterListAdapter
import com.example.android.roomwordnavigation.ui.WithSingleButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ViewOrganisationFragment : DaggerFragment(), IWithViewModelFactory, WithSingleButton {

    private val args: ViewOrganisationFragmentArgs by navArgs()

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val organisationDetailsViewModel: OrganisationDetailsViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: FragmentViewOrganisationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewOrganisationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = organisationDetailsViewModel
        binding.view = this

        val recyclerView = binding.memberList
        val adapter = CharacterListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        organisationDetailsViewModel.organisationId.postValue(args.organisationId)

        organisationDetailsViewModel.organisation.observe(this, Observer { data ->
            (activity as? AppCompatActivity)?.supportActionBar?.title = data.name
        })
    }

    override fun onButtonClicked(view: View) {
        view.findNavController().navigate(
            ViewOrganisationFragmentDirections.actionViewOrganisationFragmentToAddCharacterToOrganisationFragment()
        )
    }
}
