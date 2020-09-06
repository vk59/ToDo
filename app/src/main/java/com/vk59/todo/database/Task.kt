package com.vk59.todo.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class Task(
    @NonNull var taskName: String,
    taskDescription: String?,
    taskStatus: Int,
    taskImportance: Int
) {
    companion object {
        const val TASK_STATUS_NOT_FINISHED = 0
        const val TASK_STATUS_FINISHED = 1
        const val TASK_IMPORTANCE_LOW = 0
        const val TASK_IMPORTANCE_NORMAL = 1
        const val TASK_IMPORTANCE_HIGH = 2
    }
    @PrimaryKey(autoGenerate = true) @NonNull val id: Long = 0

    var taskDescription = taskDescription
        set(value) {
            if (value != null) {
                field = value
            } else {
                field = ""
            }
        }

    var taskStatus = taskStatus
        set(value) {
            if (value != TASK_STATUS_FINISHED) {
                field = TASK_STATUS_NOT_FINISHED
            } else {
                field = TASK_STATUS_FINISHED
            }
        }

    var taskImportance = taskImportance
        set(value) {
            if (value in TASK_IMPORTANCE_LOW..TASK_IMPORTANCE_HIGH) {
                field = value
            } else {
                field = TASK_IMPORTANCE_NORMAL
            }
        }
}