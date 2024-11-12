package ru.artamonov.crud.repository;

import ru.artamonov.crud.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    boolean save(Employee employee);

    boolean update(Employee employee);
    // false if not found
    boolean delete(int id);

    // null if not found
    Employee get(int id);

    List<Employee> getByTaskId(int taskId);

    List<Employee> getAll();
}