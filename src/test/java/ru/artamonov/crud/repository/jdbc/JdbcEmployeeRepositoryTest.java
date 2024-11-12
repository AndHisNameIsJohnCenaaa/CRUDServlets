package ru.artamonov.crud.repository.jdbc;

import org.junit.jupiter.api.Test;
import ru.artamonov.crud.model.Employee;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.artamonov.crud.EmployeeTestData.*;
import static ru.artamonov.crud.TaskTestData.TASK1_ID;

public class JdbcEmployeeRepositoryTest extends AbstractRepositoryTest {

	private final JdbcEmployeeRepository repository = JdbcEmployeeRepository.getInstance();

	@Test
	void save() {
		repository.save(newEmployee);
		Employee actual = repository.get(NEW_EMPLOYEE_ID);
		Employee expected = new Employee(newEmployee);
		expected.setId(NEW_EMPLOYEE_ID);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void update() {
		repository.update(updatedEmployee);
		Employee actual = repository.get(updatedEmployee.getId());
		Employee expected = new Employee(updatedEmployee);
		expected.setId(updatedEmployee.getId());
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void delete() {
		repository.delete(EMPLOYEE1_ID);
		Employee actual = repository.get(EMPLOYEE1_ID);
		assertThat(actual).isNull();
	}

	@Test
	void get() {
		Employee actual = repository.get(EMPLOYEE1_ID);
		assertThat(actual).isEqualTo(employee1);
	}

	@Test
	void getByTaskId() {
		List<Employee> actual = repository.getByTaskId(TASK1_ID);
		assertThat(actual).hasSize(2).isEqualTo(task1Employees);
	}

	@Test
	void getAll() {
		List<Employee> actual = repository.getAll();
		assertThat(actual).hasSize(3).isEqualTo(employees);
	}
}