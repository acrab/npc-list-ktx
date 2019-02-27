package com.example.android.roomwordnavigation.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //Try and find type by simple lookup
        var creator: Provider<out ViewModel>? = creators[modelClass]

        if (creator == null) {
            //If not found, try and find a sub-class
            try {
                creator = creators.entries.first { modelClass.isAssignableFrom(it.key) }.value
            } catch (e: NoSuchElementException) {
                //Still not found: die
                throw IllegalArgumentException("unknown model class $modelClass", e)
            }
        }

        try {
            //Return it as the type requested
            @Suppress("UNCHECKED_CAST") return creator.get() as T
        } catch (e: Exception) {
            //Or fail if somehow it's not castable.
            throw RuntimeException(e)
        }
    }
}