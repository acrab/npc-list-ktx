package com.example.android.roomwordnavigation.injection

import android.app.Application
import com.example.android.roomwordnavigation.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule{

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application) = AppDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun providesCharacterDao(database : AppDatabase) = database.characterDao()

    @Singleton
    @Provides
    fun providesMembershipDao(database : AppDatabase) = database.membershipDao()

    @Singleton
    @Provides
    fun providesOrganisationDao(database : AppDatabase) = database.organisationDao()

    @Singleton
    @Provides
    fun providesCharacterRepository(characterDao: CharacterDao) : ICharacterRepository = CharacterRepository(characterDao)

    @Singleton
    @Provides
    fun providesMembershipRepository(membershipDao: MembershipDao, organisationDao: OrganisationDao) = MembershipRepository(membershipDao, organisationDao)

    @Singleton
    @Provides
    fun providesOrganisationRepository(organisationDao: OrganisationDao) = OrganisationRepository(organisationDao)
}