package ca.todo.data.source

import ca.todo.data.Result
import ca.todo.data.Task
import java.util.*

class FakeTaskService(var tasks: MutableList<Task>? = mutableListOf()) : TaskService {
    override suspend fun getTasks(): List<Task> {
        return Collections.unmodifiableList(tasks)
    }



    override suspend fun findTask(id: String): Task {
        TODO("Not yet implemented")
    }

}