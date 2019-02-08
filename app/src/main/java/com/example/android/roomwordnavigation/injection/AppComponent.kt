package com.example.android.roomwordnavigation.injection

import android.app.Application
import com.example.android.roomwordnavigation.CharacterApp
import com.example.android.roomwordnavigation.MainActivity
import com.example.android.roomwordnavigation.data.AppDatabase
import com.example.android.roomwordnavigation.data.CharacterDao
import com.example.android.roomwordnavigation.data.CharacterRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    DBModule::class,
    FragmentModule::class])
interface AppComponent{

    @Component.Builder
    interface Builder {
        // provide Application instance into DI
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: CharacterApp)

    fun inject(activity: MainActivity)

    fun characterDao() : CharacterDao

    fun appDataBase() : AppDatabase

    fun characterRepository() : CharacterRepository

    fun application() : Application
}