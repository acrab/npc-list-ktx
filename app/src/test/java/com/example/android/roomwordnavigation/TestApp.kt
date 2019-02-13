package com.example.android.roomwordnavigation

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector

class TestApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.AppTheme)
    }

    override fun activityInjector() = AndroidInjector<Activity> {   }

    override fun supportFragmentInjector() = AndroidInjector<Fragment> {    }

}