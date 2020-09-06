package com.vk59.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk59.todo.database.Task
import com.vk59.todo.database.TaskDao
import com.vk59.todo.database.TaskRoomDatabase

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private var db: TaskRoomDatabase
    private var taskDao: TaskDao

    var task: LiveData<Task>? = null

    fun getTaskById(id: Long) {
        task = MutableLiveData(taskDao.getById(id))
    }

    var eventTaskAdd: MutableLiveData<Boolean>
    var eventTaskUpdate: MutableLiveData<Boolean>

    init {
        db = TaskRoomDatabase.getTaskDB(application)
        taskDao = db.taskDao()

        eventTaskAdd = MutableLiveData(false)
        eventTaskUpdate = MutableLiveData(false)
    }

    fun eventTaskAddFinished() {
        eventTaskAdd.value = false
    }

    fun eventTaskUpdateFinished() {
        eventTaskUpdate.value = false
    }

    fun insertTask(task: Task) {
        TaskRoomDatabase.databaseWriteExecutor.execute {
            taskDao.insert(task)
        }
    }


    fun updateTask() {
        TaskRoomDatabase.databaseWriteExecutor.execute { taskDao.update(task!!.value!!) }
        eventTaskUpdate.setValue(true)
    }
}
