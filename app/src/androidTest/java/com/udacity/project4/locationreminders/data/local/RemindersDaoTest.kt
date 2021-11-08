package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun testInsertRetrieveData() = runBlockingTest {

        val data = ReminderDTO(
            "title abc",
            "description abc",
            "location abc",
            77.00,
            77.00)

        database.reminderDao().saveReminder(data)

        val loadedDataList = database.reminderDao().getReminders()

        MatcherAssert.assertThat(loadedDataList.size, `is`(1))

        val loadedData = loadedDataList[0]
        MatcherAssert.assertThat(loadedData.id, `is`(data.id))
        MatcherAssert.assertThat(loadedData.title, `is`(data.title))
        MatcherAssert.assertThat(loadedData.description, `is`(data.description))
        MatcherAssert.assertThat(loadedData.location, `is`(data.location))
        MatcherAssert.assertThat(loadedData.latitude, `is`(data.latitude))
        MatcherAssert.assertThat(loadedData.longitude, `is`(data.longitude))

    }
}