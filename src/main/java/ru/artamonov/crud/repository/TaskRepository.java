package ru.artamonov.crud.repository;

import ru.artamonov.crud.model.Task;

import java.util.List;

public interface TaskRepository {

    boolean save(Task task);

    boolean assignTaskToEmployee(int taskId, int employeeId);

    boolean assignTaskToCompany(int taskId, int companyId);

    // false if task does not belong to companyId
    boolean delete(int id);

    Task get(int id);

    List<Task> getAll();

    List<Task> getByEmployeeId(int employeeId);

    List<Task> getByCompanyId(int companyId);
}
