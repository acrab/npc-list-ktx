package com.example.android.roomwordnavigation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.roomwordnavigation.data.entities.*
import java.util.concurrent.Executors

@Database(
    entities = [CharacterEntity::class,
        Organisation::class,
        OrganisationMembership::class,
        Template::class,
        Statistic::class,
        CharacterStatValue::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun organisationDao(): OrganisationDao
    abstract fun membershipDao(): MembershipDao
    abstract fun templateDao(): TemplateDao
    abstract fun statisticsDao(): StatisticDao
    abstract fun characterStatDao(): CharacterStatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        Executors.newSingleThreadScheduledExecutor()
                            .execute {
                                val appDb = getDatabase(context)
                                //Create the DnD template
                                val templateId = 1L
                                val templates = appDb.templateDao()
                                templates.insert(Template("DnD 5e", templateId))
                                //Add the stats it uses
                                val stats = appDb.statisticsDao()
                                val ac = 1L
                                stats.insert(Statistic("AC", templateId, ac))
                                val hp = 2L
                                stats.insert(Statistic("HP", templateId, hp))
                                val speed = 3L
                                stats.insert(Statistic("Speed", templateId, speed))
                                val str = 4L
                                stats.insert(Statistic("STR", templateId, str))
                                val dex = 5L
                                stats.insert(Statistic("DEX", templateId, dex))
                                val int = 6L
                                stats.insert(Statistic("INT", templateId, int))

                                //Create a character, using that template
                                val characters = appDb.characterDao()
                                val charId = 1L
                                characters.insert(CharacterEntity("Bob", "Looks like a bob", id = charId, template = templateId))

                                //Set the values of the stats for that character
                                val characterStatDao = appDb.characterStatDao()
                                characterStatDao.insert(CharacterStatValue(18, ac, charId))
                                characterStatDao.insert(CharacterStatValue(36, hp, charId))
                                characterStatDao.insert(CharacterStatValue(25, speed, charId))
                                characterStatDao.insert(CharacterStatValue(16, str, charId))
                                characterStatDao.insert(CharacterStatValue(12, dex, charId))
                                characterStatDao.insert(CharacterStatValue(14, int, charId))
                            }

                    }
                })
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}