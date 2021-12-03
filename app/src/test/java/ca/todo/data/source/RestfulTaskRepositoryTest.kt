package ca.todo.data.source

import ca.todo.MainCoroutineRule
import ca.todo.data.Result
import ca.todo.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestfulTaskRepositoryTest {

    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }

    private lateinit var taskService: TaskService

    // Class under test
    private lateinit var tasksRepository: RestfulTaskRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        taskService = FakeTaskService()
        // Get a reference to the class under test
        tasksRepository = RestfulTaskRepository(
            taskService, Dispatchers.Main
        )
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // Then tasks are loaded from the remote data source
        MatcherAssert.assertThat(tasks.data, IsEqual(remoteTasks))
    }
}