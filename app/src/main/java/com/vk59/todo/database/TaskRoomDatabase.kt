package com.vk59.todo.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Task::class], version = 1)
abstract class TaskRoomDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        val DB_NAME = "tasks.db"
        @Volatile private var INSTANCE: TaskRoomDatabase? = null
        private val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getTaskDB(context: Context): TaskRoomDatabase {
            if (INSTANCE != null) {
                synchronized(this){
                    INSTANCE = create(context)
                }
            }
            return INSTANCE!!
        }

        fun create(context: Context, memoryOnly: Boolean = true): TaskRoomDatabase {
            var builder = if (memoryOnly) {
                Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java
                )
            } else {
                Room.databaseBuilder(
                    context.applicationContext, TaskRoomDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(sRoomDatabaseCallback)
            }
            return builder.build()
        }

        private var sRoomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onOpen(db)

                TaskRoomDatabase.databaseWriteExecutor.execute(Runnable {
                    val dao = INSTANCE!!.taskDao()
                    dao.deleteAll()
                    var task: Task
                    for (i in 0..29) {
                        task = Task(
                            "Task $i",
                            "Task description $i",
                            Task.TASK_STATUS_NOT_FINISHED,
                            Random().nextInt(3)
                        )
                        dao.insert(task)
                    }
                })
            }
        }

    }
}