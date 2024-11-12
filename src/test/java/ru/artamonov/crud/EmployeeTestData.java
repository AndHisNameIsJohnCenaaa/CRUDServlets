package ru.artamonov.crud;

import ru.artamonov.crud.model.Employee;
import java.util.List;

public class EmployeeTestData {
	public static final int EMPLOYEE1_ID = 100002;
	public static final int EMPLOYEE2_ID = 100003;
	public static final int EMPLOYEE3_ID = 100004;
	public static final int NEW_EMPLOYEE_ID = 100011;

	public static final Employee employee1 = new Employee(EMPLOYEE1_ID, "Бывалый", "vr_38");
	public static final Employee employee2 = new Employee(EMPLOYEE2_ID, "Полкило", "polkilo666");
	public static final Employee employee3 = new Employee(EMPLOYEE3_ID, "Ляля", "lyalya777");

	public static final Employee newEmployee = new Employee("Трус", "monkey");
	public static final Employee updatedEmployee = new Employee(EMPLOYEE1_ID, "updated", "updated");

	public static final List<Employee> employees = List.of(employee1, employee2, employee3);
	public static final List<Employee> task1Employees = List.of(employee1, employee2);

}
