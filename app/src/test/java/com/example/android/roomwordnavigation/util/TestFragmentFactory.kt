package com.example.android.roomwordnavigation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.IWithInputMethodManagerFactory
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.InputMethodManagerFactory

open class FragmentWithViewModelFactory<T : IWithViewModelFactory>(private val viewModelFactory: ViewModelProvider.Factory) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)
        //Cast it
        val f = fragment as T
        //Assign things
        f.viewModelFactory = viewModelFactory
        //Return it
        return fragment
    }
}

open class FragmentWithInputMethodManagerFactory<T: IWithInputMethodManagerFactory>(private val immFactory: InputMethodManagerFactory) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)
        //Cast it
        val f = fragment as T
        //Assign things
        f.immFactory = immFactory
        //Return it
        return fragment
    }
}

open class FragmentWithBothFactory<T:IWithBoth>(private val immFactory: InputMethodManagerFactory, private val viewModelFactory: ViewModelProvider.Factory) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)
        //Cast it
        val f = fragment as T
        //Assign things
        f.viewModelFactory = viewModelFactory
        f.immFactory = immFactory
        //Return it
        return fragment
    }
}