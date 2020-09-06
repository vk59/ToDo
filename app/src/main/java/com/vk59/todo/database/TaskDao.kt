package com.vk59.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Update
    fun update(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAll()

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Long) : Task
}