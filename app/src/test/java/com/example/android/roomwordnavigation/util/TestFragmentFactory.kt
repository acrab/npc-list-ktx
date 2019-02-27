package com.example.android.roomwordnavigation.util

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.IWithBoth
import com.example.android.roomwordnavigation.IWithInputMethodManagerFactory
import com.example.android.roomwordnavigation.IWithViewModelFactory
import com.example.android.roomwordnavigation.InputMethodManagerFactory
import com.nhaarman.mockitokotlin2.*

data class FragmentFactoryData<V : ViewModel>(
    val fragmentFactory: FragmentWithViewModelFactory, val viewModel: V, val viewModelFactory: ViewModelProvider.Factory
)

inline fun <reified V : ViewModel> fragmentFactoryWithMockViewModel(): FragmentFactoryData<V> =
    fragmentFactoryWithMockViewModel(mock<V>())

inline fun <reified V : ViewModel> fragmentFactoryWithMockViewModel(mockViewModel: KStubbing<V>.(V) -> Unit): FragmentFactoryData<V> =
    fragmentFactoryWithMockViewModel(mock(stubbing = mockViewModel))

inline fun <reified V : ViewModel> fragmentFactoryWithMockViewModel(viewModel: V): FragmentFactoryData<V> {

    val factory = mock<ViewModelProvider.Factory> {
        on { create<V>(any()) } doReturn viewModel
    }
    val fragFactory = FragmentWithViewModelFactory(factory)

    return FragmentFactoryData(fragFactory, viewModel, factory)
}

data class ExtendedFragmentFactoryData<V : ViewModel>(
    val fragmentFactory: FragmentWithBothFactory,
    val viewModel: V,
    val inputMethodManager: InputMethodManager,
    val viewModelFactory: ViewModelProvider.Factory
)

inline fun <reified V : ViewModel> fragmentFactoryWithMockViewModelAndIMM(): ExtendedFragmentFactoryData<V> =
    fragmentFactoryWithMockViewModelAndIMM(mock<V>())

inline fun <reified V : ViewModel> fragmentFactoryWithMockViewModelAndIMM(mockViewModel: KStubbing<V>.(V) -> Unit): ExtendedFragmentFactoryData<V> =
    fragmentFactoryWithMockViewModelAndIMM(mock(stubbing = mockViewModel))

inline fun <reified V : ViewModel> fragmentFactoryWithMockViewModelAndIMM(viewModel: V): ExtendedFragmentFactoryData<V> {

    val viewModelFactory = mock<ViewModelProvider.Factory> {
        on { create<V>(any()) } doReturn viewModel
    }
    val inputMethodManager = mock<InputMethodManager>()

    val immFactory: InputMethodManagerFactory = mock {
        on { get(any()) } doReturn inputMethodManager
    }

    val fragFactory = FragmentWithBothFactory(immFactory, viewModelFactory)

    return ExtendedFragmentFactoryData(fragFactory, viewModel, inputMethodManager, viewModelFactory)
}

open class FragmentWithViewModelFactory(private val viewModelFactory: ViewModelProvider.Factory) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)
        //Cast it
        val f = fragment as IWithViewModelFactory
        //Assign things
        f.viewModelFactory = viewModelFactory
        //Return it
        return fragment
    }
}

open class FragmentWithInputMethodManagerFactory(private val immFactory: InputMethodManagerFactory) :
    FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)

        //Cast it
        val f = fragment as IWithInputMethodManagerFactory
        //Assign things
        f.immFactory = immFactory
        //Return it
        return fragment
    }
}

open class FragmentWithBothFactory(
    private val immFactory: InputMethodManagerFactory, private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
        //Spawn the fragment
        val fragment = super.instantiate(classLoader, className, args)

        //Cast it.
        val f = fragment as IWithBoth
        //Assign things
        f.viewModelFactory = viewModelFactory
        f.immFactory = immFactory
        //Return it
        return fragment
    }
}