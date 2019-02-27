package com.example.android.roomwordnavigation.organisations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.databinding.FragmentAddOrganisationBinding
import com.example.android.roomwordnavigation.inputManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddOrganisationFragment : DaggerFragment(), IWithBoth {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    override lateinit var immFactory: InputMethodManagerFactory

    private val inputMethodManager: InputMethodManager by inputManager { immFactory }
    private val organisationListViewModel: OrganisationListViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAddOrganisationBinding.inflate(inflater, container, false)

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