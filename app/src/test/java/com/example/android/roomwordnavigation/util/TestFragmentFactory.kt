package com.example.android.roomwordnavigation.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.IWithInputMethodManagerFactory
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock

inline fun <reified F : IWithViewModelFactory, reified V : ViewModel> fragmentFactoryWithMockViewModel(viewModel: V): FragmentWithViewModelFactory<F> {

    val factory = mock<ViewModelProvider.Factory> {
        on { create<V>(any()) } doReturn viewModel
    }

    return FragmentWithViewModelFactory(factory)
}

inline fun <reified F : IWithViewModelFactory, reified V : ViewModel> fragmentFactoryWithMockViewModel(): Pair<V, FragmentWithViewModelFactory<F>> {
    val viewModel = mock<V>()
    val factory = mock<ViewModelProvider.Factory> {
        on { create<V>(any()) } doReturn viewModel
    }
    val fragFactory = FragmentWithViewModelFactory<F>(factory)

    return viewModel to fragFactory
}

open class FragmentWithViewModelFactory<T : IWithViewModelFactory>(private val viewModelFactory: ViewModelProvider.Factory) :
    FragmentFactory() {
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