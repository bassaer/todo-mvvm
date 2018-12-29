package com.github.bassaer.todomvvm.data.source

import com.github.bassaer.todomvvm.data.Task

class TasksRespository (
    val tasksRemoteDataSource: TasksDataSource,
    val tasksLocalDataSource: TasksDataSource) : TasksDataSource {


    var cachedTasks: LinkedHashMap<String, Task> = LinkedHashMap()

    var cachedIdDirty = false

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        if (cachedTasks.isNotEmpty() && !cachedIdDirty) {
            callback.onTasksLoaded(ArrayList(cachedTasks.values))
            return
        }

        if (cachedIdDirty) {
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getTaskFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback) {
        tasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {

            }
        })
    }

    private fun refreshCache(tasks: List<Task>) {
        cachedTasks.clear()
        tasks.forEach(

        )
    }

    private fun refreshLocalDataSource(tasks: List<Task>) {
        tasksLocalDataSource.deleteAllTasks()
        for (task in tasks) {
            tasksLocalDataSource.saveTask(task)
        }
    }

    private inline fun cacheAndPerform(task: Task, perform: (Task) -> Unit) {
        val cachedTask = Task(task.title, task.description, task.id).apply {
            isCompleted = task.isCompleted
        }
        cachedTasks[cachedTask.id] = cachedTask
        perform(cachedTask)
    }


    companion object {
        privsate var INSTANCE: TasksRespository? = null

        @JvmStatic fun getInstance(tasksRemoteDataSource: TasksDataSource,
                                   tasksLocalDataSource: TasksDataSource) =
                INSTANCE ?: synchronized(TasksRespository::class.java) {
                    INSTANCE ?: TasksRespository(tasksRemoteDataSource, tasksLocalDataSource)
                        .also { INSTANCE = it }
                }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}