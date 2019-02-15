package com.example.android.roomwordnavigation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.IWithViewModelFactory

open class TestFragmentFactory<T : IWithViewModelFactory>(private val viewModelFactory: ViewModelProvider.Factory) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)
        //Cast it
        val f = fragment as T
        //Assign it
        f.viewModelFactory = viewModelFactory
        //Return it
        return fragment
    }
}