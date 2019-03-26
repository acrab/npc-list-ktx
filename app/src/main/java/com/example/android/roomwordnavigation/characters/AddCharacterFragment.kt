package com.example.android.roomwordnavigation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.example.android.roomwordnavigation.R
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Template
import com.example.android.roomwordnavigation.databinding.FragmentAddCharacterBinding
import com.example.android.roomwordnavigation.inputManager
import com.example.android.roomwordnavigation.ui.EditStatsListAdapter
import com.example.android.roomwordnavigation.ui.WithCustomButton
import com.example.android.roomwordnavigation.ui.asString
import com.example.android.roomwordnavigation.ui.setupLinearWithAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddCharacterFragment : DaggerFragment(), IWithBoth, WithCustomButton {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    override lateinit var immFactory: InputMethodManagerFactory

    private val addCharacterViewModel: AddCharacterViewModel by viewModels { viewModelFactory }
    private val inputMethodManager: InputMethodManager by inputManager { immFactory }

    private lateinit var binding: FragmentAddCharacterBinding

    private var selectedTemplate: Long = 0

    private val statMap = mutableMapOf<Int, Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonText = resources.getString(R.string.add_character)
        binding.view = this
        addCharacterViewModel.templates.observe(this, Observer {
            binding.templateSelector.adapter = object : ArrayAdapter<Template>(
                requireContext(), android.R.layout.simple_spinner_item, it!!
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val textView = super.getView(position, convertView, parent) as TextView
                    textView.text = getItem(position)?.name
                    return textView
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val textView = super.getView(position, convertView, parent) as TextView
                    textView.text = getItem(position)?.name
                    return textView
                }
            }
        })

        binding.templateSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                createViewsForTemplate(binding.templateSelector.adapter.getItem(position) as Template)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

    fun createViewsForTemplate(template: Template) {
        selectedTemplate = template.id
        addCharacterViewModel.selectedTemplate.value = template.id
        addCharacterViewModel.stats.observe(this, Observer {
            binding.templateSelector.visibility = View.GONE
            binding.statList.visibility = View.VISIBLE

            val adapter = EditStatsListAdapter(requireContext()) { stat, value ->
                statMap[stat] = value
            }

            binding.statList.setupLinearWithAdapter(requireContext(), adapter)
        })

    }

    override lateinit var buttonText: String

    override fun onButtonClicked(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        binding.apply {

            addCharacterViewModel.insert(
                CharacterEntity(
                    editText.asString(), description.asString(), notes.asString(), template = selectedTemplate
                )
            )

        }
        view.findNavController().navigateUp()
    }
}