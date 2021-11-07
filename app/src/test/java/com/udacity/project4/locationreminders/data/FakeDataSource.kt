package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

    var reminderDTOList = mutableListOf<ReminderDTO>()

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if(reminderDTOList.isEmpty()) {
            return Result.Error("Reminders not found")
        } else {
            return Result.Success(ArrayList(reminderDTOList))
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminderDTOList.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        val reminder = reminderDTOList.find { it.id == id }
        return if (reminder == null) {
            Result.Error("Not found $id")
        } else {
            Result.Success(reminder)
        }
    }

    override suspend fun deleteAllReminders() {
        reminderDTOList.clear()
    }
}