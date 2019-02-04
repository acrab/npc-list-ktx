package com.example.android.roomwordnavigation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Character::class, Organisation::class, OrganisationMembership::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao():CharacterDao
    abstract fun organisationDao():OrganisationDao
    abstract fun membershipDao():MembershipDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Word_database"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}