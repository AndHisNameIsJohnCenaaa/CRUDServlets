package ru.artamonov.crud.repository.jdbc;

import org.junit.jupiter.api.Test;
import ru.artamonov.crud.model.Task;
import java.util.ArrayList;
import java.util.List;

import static ru.artamonov.crud.CompanyTestData.COMPANY1_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.artamonov.crud.EmployeeTestData.EMPLOYEE1_ID;
import static ru.artamonov.crud.EmployeeTestData.EMPLOYEE3_ID;
import static ru.artamonov.crud.TaskTestData.*;

public class JdbcTaskRepositoryTest extends AbstractRepositoryTest {
	private final JdbcTaskRepository repository = JdbcTaskRepository.getInstance();

	@Test
	void save() {
		repository.save(newTask);
		Task actual = repository.get(NEW_TASK_ID);
		Task expected = new Task(newTask);
		expected.setId(NEW_TASK_ID);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void findAll() {}

	@Test
	void assignTaskToCompany() {
		repository.save(newTask);
		repository.assignTaskToCompany(NEW_TASK_ID, COMPANY1_ID);
		List<Task> actual = repository.getByCompanyId(COMPANY1_ID);
		List<Task> expected = new ArrayList<>(company1Tasks);
		Task expectedTask = new Task(newTask);
		expectedTask.setId(NEW_TASK_ID);
		expected.add(expectedTask);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void assignTaskToEmployee() {
		repository.assignTaskToEmployee(TASK1_ID + 5, EMPLOYEE3_ID);
		List<Task> actual = repository.getByEmployeeId(EMPLOYEE3_ID);
		List<Task> expected = new ArrayList<>(employer3Tasks);
		expected.add(task6);
		assertThat(actual).hasSize(2).isEqualTo(expected);
	}

	@Test
	void delete() {
		repository.delete(TASK1_ID);
		List<Task> actual = repository.getByCompanyId(COMPANY1_ID);
		List<Task> expected = new ArrayList<>(company1Tasks);
		expected.remove(task1);
		assertThat(actual).hasSize(2).isEqualTo(expected);
	}

	@Test
	void get() {
		Task actual = repository.get(TASK1_ID);
		assertThat(actual).isEqualTo(task1);
	}

	@Test
	void getByEmployeeId() {
		List<Task> actual = repository.getByEmployeeId(EMPLOYEE1_ID);
		assertThat(actual).hasSize(3).isEqualTo(employer1Tasks);
	}

	@Test
	void getByCompanyId() {
		List<Task> actual = repository.getByCompanyId(COMPANY1_ID);
		assertThat(actual).hasSize(3).isEqualTo(company1Tasks);
	}
}