package com.vk59.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.vk59.todo.database.Task
import com.vk59.todo.database.TaskDao
import com.vk59.todo.database.TaskRoomDatabase

class TasksListViewModel(application: Application) : AndroidViewModel(application) {
    private var db: TaskRoomDatabase
    private var taskDao: TaskDao

    var tasksList: LiveData<List<Task>>

    init {
        db = TaskRoomDatabase.getTaskDB(application)
        taskDao = db.taskDao()
        tasksList = taskDao.getAllTasks()
    }

    // View ask ViewModel to insert OR update OR delete Task to db. ViewModel do it

    fun insertTask(task: Task) {
        TaskRoomDatabase.databaseWriteExecutor.execute { taskDao.insert(task) }
    }

    fun updateTask(task: Task) {
        TaskRoomDatabase.databaseWriteExecutor.execute { taskDao.update(task) }
    }

    fun deleteAllTasks() {
        TaskRoomDatabase.databaseWriteExecutor.execute { taskDao.deleteAll() }
    }

}