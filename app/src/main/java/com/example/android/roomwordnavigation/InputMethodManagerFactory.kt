package com.example.android.roomwordnavigation

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.android.example.roomwordnavigation.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class InputMethodManagerFactory @Inject constructor()
{
    fun get(context: Context) = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}