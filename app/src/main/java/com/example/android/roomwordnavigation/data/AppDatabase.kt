package com.example.android.roomwordnavigation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

@Database(entities = [CharacterEntity::class, Organisation::class, OrganisationMembership::class], version = 1)
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
                    "app_database"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}