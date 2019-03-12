package com.example.android.roomwordnavigation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership

@Database(entities = [CharacterEntity::class, Organisation::class, OrganisationMembership::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun organisationDao(): OrganisationDao
    abstract fun membershipDao(): MembershipDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //language=RoomSql

                database.execSQL(
                    """
                        PRAGMA foreign_keys = OFF
                    """
                )

                database.execSQL(
                    """
                        ALTER TABLE `characters_in_organisations_table`
                        RENAME TO `ciot_temp`
                    """
                )

                database.execSQL(
                    """CREATE TABLE IF NOT EXISTS `characters_in_organisations_table`
                        (`character` INTEGER NOT NULL,
                        `organisation` INTEGER NOT NULL,
                        PRIMARY KEY(`character`, `organisation`),
                        FOREIGN KEY(`character`) REFERENCES `character_table`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`organisation`) REFERENCES `organisation_table`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)"""
                )

                database.execSQL("DROP  INDEX `index_characters_in_organisations_table_character`")
                database.execSQL("CREATE  INDEX `index_characters_in_organisations_table_character` ON `characters_in_organisations_table` (`character`)")

                database.execSQL("DROP  INDEX `index_characters_in_organisations_table_organisation`")
                database.execSQL("CREATE  INDEX `index_characters_in_organisations_table_organisation` ON `characters_in_organisations_table` (`organisation`)")

                database.execSQL(
                    """
                        INSERT INTO `characters_in_organisations_table` (`character`, `organisation`)
                        SELECT DISTINCT `character`, `organisation` FROM `ciot_temp`
                    """
                )
                database.execSQL(
                    """
                        DROP TABLE `ciot_temp`
                    """
                )

                database.execSQL(
                    """
                        PRAGMA foreign_keys = ON
                    """
                )

            }

        }


        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                ).addMigrations(MIGRATION_1_2).build()

                INSTANCE = instance
                instance
            }
        }
    }
}