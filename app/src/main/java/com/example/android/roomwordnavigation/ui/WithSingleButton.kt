package com.example.android.roomwordnavigation.ui

import android.view.View

interface WithSingleButton{
    fun onButtonClicked(view:View)
}

interface WithCustomButton{
    fun onButtonClicked(view:View)
    val buttonText:String
}