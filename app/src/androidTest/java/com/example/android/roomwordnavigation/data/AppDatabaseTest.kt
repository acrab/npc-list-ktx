@file:Suppress("ClassName", "PrivatePropertyName")

package com.example.android.roomwordnavigation.data

import android.database.sqlite.SQLiteDatabase
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.content.contentValuesOf
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.roomwordnavigation.data.entities.CharacterEntity
import com.example.android.roomwordnavigation.data.entities.Organisation
import com.example.android.roomwordnavigation.data.entities.OrganisationMembership
import com.example.android.roomwordnavigation.observedValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class Test_Migration_From_One_To_Two {

    private val TEST_DB_NAME = "TestDatabase"

    @get:Rule
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun migrate() {
        val db = migrationTestHelper.createDatabase(TEST_DB_NAME, 1)

        //Create a character
        val characterValues = contentValuesOf(
            "name" to "Bob", "id" to 0
        )
        db.insert("character_table", SQLiteDatabase.CONFLICT_REPLACE, characterValues)

        //Create a character
        val characterTwo = contentValuesOf(
            "name" to "James", "id" to 1
        )
        db.insert("character_table", SQLiteDatabase.CONFLICT_REPLACE, characterTwo)

        //Create an organisation
        val organisationValues = contentValuesOf(
            "name" to "Bob's club", "description" to "A club for bobs", "id" to 0
        )
        db.insert("organisation_table", SQLiteDatabase.CONFLICT_REPLACE, organisationValues)

        //Set character as a member

        val membershipValues = contentValuesOf(
            "character" to 0, "organisation" to 0, "id" to 0
        )
        db.insert("characters_in_organisations_table", SQLiteDatabase.CONFLICT_REPLACE, membershipValues)

        db.close()

        migrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, AppDatabase.MIGRATION_1_2)

        val appDb = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java, TEST_DB_NAME
        ).addMigrations(AppDatabase.MIGRATION_1_2).build()

        val charDao = appDb.characterDao()
        val charLiveData = charDao.getAllCharacters()

        val c: List<CharacterEntity>? = charLiveData.observedValue()

        assertNotNull(c)
        val characters = c!!
        assertEquals(2, characters.count())
        assertEquals(0, characters[0].id)
        assertEquals("Bob", characters[0].name)
        assertEquals(1, characters[1].id)
        assertEquals("James", characters[1].name)

        val organisationDao = appDb.organisationDao()
        val organisationLiveData = organisationDao.getAllOrganisations()

        val o: List<Organisation>? = organisationLiveData.observedValue()

        assertNotNull(o)
        val organisations = o!!
        assertEquals(1, organisations.count())
        assertEquals(0, organisations[0].id)
        assertEquals("Bob's club", organisations[0].name)
        assertEquals("A club for bobs", organisations[0].description)

        val membershipDao = appDb.membershipDao()
        val membershipLiveData = membershipDao.getMembers(0)
        val m: List<CharacterEntity>? = membershipLiveData.observedValue()

        assertNotNull(c)
        val members = m!!
        assertEquals(1, members.count())
        assertEquals(0, members[0].id)
        assertEquals("Bob", members[0].name)

        val membershipStatusLiveData = membershipDao.getMembershipStatus(0)
        val ms = membershipStatusLiveData.observedValue()

        assertNotNull(ms)
        val membershipStatus = ms!!
        assertEquals(2, membershipStatus.count())
        assertEquals(0, membershipStatus[0].characterId)
        assertEquals("Bob", membershipStatus[0].characterName)
        assertEquals(true, membershipStatus[0].isMember)
        assertEquals(1, membershipStatus[1].characterId)
        assertEquals("James", membershipStatus[1].characterName)
        assertEquals(false, membershipStatus[1].isMember)

    }
}

class Test_Adding_Data {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDb: AppDatabase
    private lateinit var charDao: CharacterDao
    private lateinit var organisationDao: OrganisationDao
    private lateinit var membershipDao: MembershipDao

    @Before
    fun setup() {
        appDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java
        ).addMigrations(AppDatabase.MIGRATION_1_2).build()

        charDao = appDb.characterDao()
        organisationDao = appDb.organisationDao()
        membershipDao = appDb.membershipDao()
    }

    @After
    fun teardown() {
        appDb.clearAllTables()
        appDb.close()
    }

    @Test
    fun add_characters() {
        //Add characters
        val bob = CharacterEntity("Bob", id = 1)
        val james = CharacterEntity("James", id = 2)
        runBlocking {
            charDao.insert(bob)
            charDao.insert(james)
        }

        val allChars = charDao.getAllCharacters().observedValue()!!

        assertEquals(2, allChars.size)

        val probablyBob = allChars[0]
        assertEquals(bob.id, probablyBob.id)
        assertEquals(bob.name, probablyBob.name)

        val probablyJames = allChars[1]
        assertEquals(james.id, probablyJames.id)
        assertEquals(james.name, probablyJames.name)
    }

    @Test
    fun add_organisations() {
        val bc = Organisation("Bob's club", "A Test Club", 1)
        val js = Organisation("James' saloon", "A Test Saloon", 2)
        runBlocking {
            organisationDao.insert(bc)
            organisationDao.insert(js)
        }

        val allOrgs = organisationDao.getAllOrganisations().observedValue()!!

        assertEquals(2, allOrgs.size)

        val probablyBob = allOrgs[0]
        assertEquals(bc.id, probablyBob.id)
        assertEquals(bc.name, probablyBob.name)
        assertEquals(bc.description, probablyBob.description)

        val probablyJames = allOrgs[1]
        assertEquals(js.id, probablyJames.id)
        assertEquals(js.name, probablyJames.name)
        assertEquals(js.description, probablyJames.description)
    }

    @Test
    fun add_members() {

        //Create characters
        val bob = CharacterEntity("Bob", id = 1)
        val james = CharacterEntity("James", id = 2)

        runBlocking {
            charDao.insert(bob)
            charDao.insert(james)
        }

        //Create organisations
        val bc = Organisation("Bob's club", "A Test Club", 1)
        val bs = Organisation("Bob's saloon", "A Test Saloon", 2)
        runBlocking {
            organisationDao.insert(bc)
            organisationDao.insert(bs)
        }

        //Put Bob in his club
        membershipDao.createMembership(OrganisationMembership(bob.id, bc.id))
        //And his saloon
        membershipDao.createMembership(OrganisationMembership(bob.id, bs.id))

        //Check who's in the club
        val bcMembersLD = membershipDao.getMembers(bc.id)
        val bcMembers = bcMembersLD.observedValue()!!

        assertEquals(1, bcMembers.size)
        assertEquals(bob.id, bcMembers[0].id)

        //Check everyone's membership status
        val statusLD = membershipDao.getMembershipStatus(bc.id)
        val status = statusLD.observedValue()!!

        //Data for two characters
        assertEquals(2, status.size)
        //Bob is a member
        assertEquals(bob.id, status[0].characterId)
        assertEquals(true, status[0].isMember)
        //James isn't a member
        assertEquals(james.id, status[1].characterId)
        assertEquals(false, status[1].isMember)

        //Add James
        membershipDao.createMembership(OrganisationMembership(james.id, bc.id))
        val newStatus = statusLD.observedValue()!!
        //Data for two characters
        assertEquals(2, newStatus.size)
        //Bob is a member
        assertEquals(bob.id, newStatus[0].characterId)
        assertEquals(true, newStatus[0].isMember)
        //James isn't a member
        assertEquals(james.id, newStatus[1].characterId)
        assertEquals(true, newStatus[1].isMember)

    }

}