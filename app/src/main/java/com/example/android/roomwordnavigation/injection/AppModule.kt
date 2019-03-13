package com.example.android.roomwordnavigation.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordnavigation.MainActivity
import com.example.android.roomwordnavigation.characters.*
import com.example.android.roomwordnavigation.organisations.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ActivityModule
{
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}

@Module
abstract class FragmentModule
{
    @ContributesAndroidInjector
    internal abstract fun contributeCharacterListFragment() : CharacterListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeAddCharacterFragment() : AddCharacterFragment

    @ContributesAndroidInjector
    internal abstract fun contributeEditCharacterFragment() : EditCharacterFragment

    @ContributesAndroidInjector
    internal abstract fun contributeAddCharacterToOrganisationFragment() : AddCharacterToOrganisationFragment

    @ContributesAndroidInjector
    internal abstract fun contributeAddOrganisationFragment() : AddOrganisationFragment

    @ContributesAndroidInjector
    internal abstract fun contributeOrganisationListFragment() : OrganisationListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeViewOrganisationFragment() : ViewOrganisationFragment

}

@Module
internal class ViewModelFactoryModule{
    @Provides
    fun providesViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory = viewModelFactory
}

@Module
internal abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    abstract fun bindCharacterListViewModel(viewModel: CharacterListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrganisationListViewModel::class)
    abstract fun bindOrganisationListViewModel(viewModel: OrganisationListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrganisationDetailsViewModel::class)
    abstract fun bindOrganisationDetailsViewModel(viewModel: OrganisationDetailsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditCharacterViewModel::class)
    abstract fun bindEditCharacterViewModel(viewModel: EditCharacterViewModel) : ViewModel
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)