package com.example.android.roomwordnavigation

import androidx.lifecycle.ViewModelProvider

interface IWithViewModelFactory {

    var viewModelFactory: ViewModelProvider.Factory
}
interface IWithInputMethodManagerFactory{
    var immFactory: InputMethodManagerFactory
}

interface IWithBoth : IWithInputMethodManagerFactory, IWithViewModelFactory