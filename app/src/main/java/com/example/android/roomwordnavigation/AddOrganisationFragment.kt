package com.example.android.roomwordnavigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.data.Organisation
import com.example.android.roomwordnavigation.databinding.FragmentAddOrganisationBinding

class AddOrganisationFragment : Fragment() {
    private lateinit var organisationListViewModel: OrganisationListViewModel
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddOrganisationBinding>(
            inflater,
            R.layout.fragment_add_organisation,
            container,
            false
        )

        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)!! as InputMethodManager
        organisationListViewModel =
            activity?.run { ViewModelProviders.of(this).get(OrganisationListViewModel::class.java) } ?: throw Exception(
                "Invalid Activity"
            )

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