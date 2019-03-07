package com.example.android.roomwordnavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

abstract class ViewModelWithCoroutineScope : ViewModel()
{
    protected val scope = viewModelScope
}