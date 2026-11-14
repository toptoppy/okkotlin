package toptoppy.kotlin.training.practiceProject

import java.time.LocalDate

/**
 * Intermediate Bridge Project: Task & Project Manager
 *
 * This mini-project bridges Level 2 (Object-Oriented Basics) and Level 3/4 (Functional & Concurrency).
 * It demonstrates how to combine:
 * 1. Data classes with immutable fields and default arguments.
 * 2. Enums for priority classification.
 * 3. Encapsulated class state (`TaskManager`).
 * 4. Kotlin standard collections (`filter`, `sortedBy`, `groupBy`, `count`).
 * 5. Null safety with safe calls (`?.`) and the Elvis operator (`?:`).
 */

enum class Priority { LOW, MEDIUM, HIGH }

data class Task(
    val id: String,
    val title: String,
    val priority: Priority = Priority.MEDIUM,
    val isCompleted: Boolean = false,
    val dueDate: LocalDate? = null,
)

class TaskManager {
    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task): Boolean {
        if (tasks.any { it.id == task.id }) {
            return false // ID must be unique
        }
        tasks.add(task)
        return true
    }

    fun completeTask(id: String): Boolean {
        val index = tasks.indexOfFirst { it.id == id }
        if (index == -1) return false

        val current = tasks[index]
        tasks[index] = current.copy(isCompleted = true)
        return true
    }

    fun findTaskById(id: String): Task? = tasks.find { it.id == id }

    fun getPendingTasks(): List<Task> = tasks.filter { !it.isCompleted }

    fun getCompletedTasks(): List<Task> = tasks.filter { it.isCompleted }

    fun getTasksGroupedByPriority(): Map<Priority, List<Task>> = tasks.groupBy { it.priority }

    fun getTasksSortedByDueDate(): List<Task> = tasks.sortedWith(compareBy(nullsLast()) { it.dueDate })

    fun totalTaskCount(): Int = tasks.size

    fun completionRatePercentage(): Double {
        if (tasks.isEmpty()) return 0.0
        val completedCount = tasks.count { it.isCompleted }
        return (completedCount.toDouble() / tasks.size) * 100.0
    }
}

fun main() {
    println("==================================================")
    println("📋 Task Manager (Intermediate Practice Project)")
    println("==================================================")

    val manager = TaskManager()

    // Adding sample tasks
    manager.addTask(Task("t1", "Complete Kotlin Basics", Priority.HIGH, dueDate = LocalDate.now().plusDays(1)))
    manager.addTask(Task("t2", "Read Null Safety Guide", Priority.HIGH, dueDate = LocalDate.now().plusDays(2)))
    manager.addTask(Task("t3", "Refactor Legacy Java Code", Priority.MEDIUM, dueDate = null))
    manager.addTask(Task("t4", "Explore Coroutines & Flow", Priority.MEDIUM, dueDate = LocalDate.now().plusDays(5)))
    manager.addTask(Task("t5", "Brew Artisan Coffee", Priority.LOW, dueDate = null))

    println("Total Tasks: ${manager.totalTaskCount()}")

    // Mark task complete
    manager.completeTask("t1")
    println("Completed t1! Current completion rate: %.1f%%".format(manager.completionRatePercentage()))

    // Group by Priority
    println("\n--- Tasks Grouped By Priority ---")
    manager.getTasksGroupedByPriority().forEach { (priority, taskList) ->
        println("$priority Priority (${taskList.size} tasks):")
        taskList.forEach { task ->
            val status = if (task.isCompleted) "✅" else "⏳"
            val due = task.dueDate?.let { "due: $it" } ?: "no deadline"
            println("  $status [${task.id}] ${task.title} ($due)")
        }
    }

    // Null safety demonstration: safe lookup with fallback
    println("\n--- Safe Lookup with Elvis Operator ---")
    val searchId = "t99"
    val foundTask = manager.findTaskById(searchId)
    val displayTitle = foundTask?.title ?: "Task '$searchId' was not found"
    println("Search Result: $displayTitle")
}
