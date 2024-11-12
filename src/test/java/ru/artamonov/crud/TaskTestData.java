package ru.artamonov.crud;

import ru.artamonov.crud.model.Task;

import java.time.LocalDate;
import java.util.List;
import static ru.artamonov.crud.model.AbstractBaseEntity.START_SEQ;

public class TaskTestData {
	public static final int NOT_FOUND = 240;
	public static final int TASK1_ID = START_SEQ + 5;
	public static final int NEW_TASK_ID = 100011;

	public static final Task task1 = new Task(TASK1_ID, LocalDate.of(2020, 1, 30), "очень интересно");
	public static final Task task2 = new Task(TASK1_ID + 1, LocalDate.of(2020, 1, 31), "хахахах");
	public static final Task task3 = new Task(TASK1_ID + 2, LocalDate.of(2020, 1, 23), "не хахахах");
	public static final Task task4 = new Task(TASK1_ID + 3, LocalDate.of(2020, 2, 24), "очень интересно");
	public static final Task task5 = new Task(TASK1_ID + 4, LocalDate.of(2020, 2, 11), "хахахах");
	public static final Task task6 = new Task(TASK1_ID + 5, LocalDate.of(2020, 2, 23), "не хахахах");
	public static final Task newTask = new Task(LocalDate.of(2020, 11, 22), "new task");

	public static final List<Task> allTasks = List.of(task1, task2, task3, task4, task5, task6);
	public static final List<Task> employer1Tasks = List.of(task1, task2, task3);
	public static final List<Task> employer2Tasks = List.of(task1, task3);
	public static final List<Task> employer3Tasks = List.of(task3);

	public static final List<Task> company1Tasks = List.of(task1, task2, task3);

}
