package com.example.android.roomwordnavigation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.IWithViewModelFactory

class TestFragmentFactory<T : IWithViewModelFactory>(private val viewModel: ViewModel) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)

        //Cast it
        val f = fragment as T
        //Create a mock
        val vmf = MockViewModelFactory(viewModel)
        //Assign it
        f.viewModelFactory = vmf

        return fragment
    }
}

class MockViewModelFactory(private val viewModel: ViewModel) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }

}