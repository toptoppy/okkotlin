package toptoppy.kotlin.training.practiceProject

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import java.time.LocalDate
import kotlin.test.Test

class TaskManagerTest {

    @Test
    fun `test adding task and enforcing unique ID`() {
        val manager = TaskManager()

        val task1 = Task(id = "t1", title = "Setup Kotlin IDE", priority = Priority.HIGH)
        val duplicate = Task(id = "t1", title = "Duplicate Task", priority = Priority.LOW)

        manager.addTask(task1) shouldBe true
        manager.addTask(duplicate) shouldBe false // Duplicate ID rejected

        manager.totalTaskCount() shouldBe 1
    }

    @Test
    fun `test complete task and completion rate calculation`() {
        val manager = TaskManager()

        manager.addTask(Task("t1", "Task 1", Priority.MEDIUM))
        manager.addTask(Task("t2", "Task 2", Priority.LOW))

        manager.completionRatePercentage() shouldBe 0.0

        val completed = manager.completeTask("t1")
        completed shouldBe true

        manager.completionRatePercentage() shouldBe 50.0

        // Completing non-existent task returns false
        manager.completeTask("unknown_id") shouldBe false
    }

    @Test
    fun `test filtering pending vs completed tasks`() {
        val manager = TaskManager()

        manager.addTask(Task("t1", "Task 1", isCompleted = false))
        manager.addTask(Task("t2", "Task 2", isCompleted = true))
        manager.addTask(Task("t3", "Task 3", isCompleted = false))

        val pending = manager.getPendingTasks()
        val completed = manager.getCompletedTasks()

        pending.map { it.id } shouldContainExactly listOf("t1", "t3")
        completed.map { it.id } shouldContainExactly listOf("t2")
    }

    @Test
    fun `test grouping tasks by priority`() {
        val manager = TaskManager()

        manager.addTask(Task("t1", "Critical Bug", Priority.HIGH))
        manager.addTask(Task("t2", "Security Patch", Priority.HIGH))
        manager.addTask(Task("t3", "Update README", Priority.LOW))

        val grouped = manager.getTasksGroupedByPriority()

        grouped[Priority.HIGH]?.size shouldBe 2
        grouped[Priority.LOW]?.size shouldBe 1
        grouped[Priority.MEDIUM].shouldBeNull()
    }

    @Test
    fun `test finding task with null safety fallback`() {
        val manager = TaskManager()
        manager.addTask(Task("t1", "Learn Coroutines", Priority.HIGH))

        val found = manager.findTaskById("t1")
        found.shouldNotBeNull()
        found.title shouldBe "Learn Coroutines"

        val notFound = manager.findTaskById("missing_id")
        notFound.shouldBeNull()

        // Demonstrating safe Elvis operator fallback:
        val taskName = notFound?.title ?: "Default Task"
        taskName shouldBe "Default Task"
    }
}
