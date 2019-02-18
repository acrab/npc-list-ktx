package com.example.android.roomwordnavigation.organisations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.databinding.FragmentAddOrganisationBinding
import com.example.android.roomwordnavigation.injection.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddOrganisationFragment : DaggerFragment(), IWithBoth {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    override lateinit var immFactory: InputMethodManagerFactory

    private lateinit var inputMethodManager: InputMethodManager
    private val organisationListViewModel: OrganisationListViewModel by activityViewModels{viewModelFactory}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddOrganisationBinding>(
            inflater,
            R.layout.fragment_add_organisation,
            container,
            false
        )

        inputMethodManager = immFactory.get(context!!)

        binding.button.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

            val newOrg = Organisation(
                binding.organisationTitle.text.toString(),
                binding.organisationDescription.text.toString()
            )

            organisationListViewModel.insert(newOrg)
            it.findNavController().navigateUp()
        }

        return binding.root
    }
}