package com.example.android.roomwordnavigation.injection

import android.app.Application
import com.example.android.roomwordnavigation.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application) = AppDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun providesCharacterDao(database: AppDatabase) = database.characterDao()

    @Singleton
    @Provides
    fun providesMembershipDao(database: AppDatabase) = database.membershipDao()

    @Singleton
    @Provides
    fun providesOrganisationDao(database: AppDatabase) = database.organisationDao()

    @Singleton
    @Provides
    fun providesTemplateDao(database: AppDatabase) = database.templateDao()

    @Singleton
    @Provides
    fun providesStatisticsDao(database: AppDatabase) = database.statisticsDao()

    @Singleton
    @Provides
    fun providesCharacterStatDao(database: AppDatabase) = database.characterStatDao()

    @Singleton
    @Provides
    fun providesCharacterRepository(characterDao: CharacterDao): ICharacterRepository =
        CharacterRepository(characterDao)

    @Singleton
    @Provides
    fun providesMembershipRepository(membershipDao: MembershipDao): IMembershipRepository =
        MembershipRepository(membershipDao)

    @Singleton
    @Provides
    fun providesOrganisationRepository(organisationDao: OrganisationDao): IOrganisationRepository =
        OrganisationRepository(organisationDao)

    @Singleton
    @Provides
    fun providesTemplateRepository(templateDao: TemplateDao): ITemplateRepository = TemplateRepository(templateDao)

    @Singleton
    @Provides
    fun providesStatisticsRepository(statisticDao: StatisticDao): IStatsRepository = StatsRepository(statisticDao)

    @Singleton
    @Provides
    fun providesCharStatisticsRepository(charStatDao: CharacterStatDao): ICharStatsRepository = CharStatsRepository(charStatDao)
}