package com.example.android.roomwordnavigation

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.android.example.roomwordnavigation.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class InputMethodManagerFactory @Inject constructor()
{
    fun get(context: Context) = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}

fun Fragment.inputManager(factory:()->InputMethodManagerFactory) : Lazy<InputMethodManager>
{
    return lazy {
        factory().get(this.requireContext())
    }
}